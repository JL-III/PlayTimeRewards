package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.events.RewardEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerReward implements Listener {

    private final ConfigManager configManager;

    private final ConcurrentHashMap<UUID, Long> playerRewardTotals;

    private final List<UUID> playerBlackList;

    public PlayerReward(ConfigManager configManager, ConcurrentHashMap<UUID, Long> playerRewardTotals, List<UUID> playerBlackList) {
        this.configManager = configManager;
        this.playerRewardTotals = playerRewardTotals;
        this.playerBlackList = playerBlackList;
    }

    @EventHandler
    public void onPlayerReward(RewardEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if (player == null) {
            return;
        }
        if (playerBlackList.contains(player.getUniqueId())) {
            Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " is on the blacklist and will not receive rewards.");
            return;
        }
        if (playerRewardTotals.containsKey(player.getUniqueId())) {
            long total = playerRewardTotals.get(player.getUniqueId()) + event.getReward();
            if (total > configManager.getRewardCap()) {
                Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " has reached their reward cap for the day!");
                Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " has been added to the blacklist.");
                playerBlackList.add(player.getUniqueId());
                return;
            }
            playerRewardTotals.put(player.getUniqueId(), total);
        } else {
            playerRewardTotals.put(player.getUniqueId(), event.getReward());
        }
        Bukkit.getConsoleSender().sendMessage("Player " + player.getName() + " has been rewarded " + event.getReward() + " playtime rewards.");

        Bukkit.getConsoleSender().sendMessage("Player's total rewards today: " + playerRewardTotals.get(player.getUniqueId()));
        player.sendMessage(Component.text(
                "[PlayTimeRewards]: " + "You have been rewarded " + event.getReward() + " denarii.")
                .color(NamedTextColor.GREEN)
                .hoverEvent(HoverEvent.showText(Component.text(
                        "Hi, " + player.getName() + "!\n" +
                        "Earnings report: \n" +
                        + playerRewardTotals.get(player.getUniqueId()) + "/" + configManager.getRewardCap()).color(NamedTextColor.YELLOW)))
        );
    }
}
