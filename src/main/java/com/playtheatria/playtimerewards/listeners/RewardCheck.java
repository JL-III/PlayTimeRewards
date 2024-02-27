package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.events.RewardCheckEvent;
import com.playtheatria.playtimerewards.events.RewardEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RewardCheck implements Listener {

    private final ConfigManager configManager;

    private final ConcurrentHashMap<UUID, Long> playerRewardTotals;

    private final List<UUID> playerOverLimitList;

    public RewardCheck(ConfigManager configManager, ConcurrentHashMap<UUID, Long> playerRewardTotals, List<UUID> playerOverLimitList) {
        this.configManager = configManager;
        this.playerRewardTotals = playerRewardTotals;
        this.playerOverLimitList = playerOverLimitList;
    }

    @EventHandler
    public void onRewardCheck(RewardCheckEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if (player == null) {
            return;
        }
        if (playerRewardTotals.containsKey(player.getUniqueId())) {
            long total = playerRewardTotals.get(player.getUniqueId()) + event.getReward();
            if (total > configManager.getRewardCap()) {
                Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + player.getName() + " has reached their reward cap for the day!");
                Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + player.getName() + " has been added to the playerOverLimitList.");
                playerOverLimitList.add(player.getUniqueId());
                return;
            }
        }
        Bukkit.getPluginManager().callEvent(new RewardEvent(player.getUniqueId(), event.getReward()));
    }
}
