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

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Reward implements Listener {

    private final ConfigManager configManager;

    private final ConcurrentHashMap<UUID, Long> playerRewardTotals;

    public Reward(ConfigManager configManager, ConcurrentHashMap<UUID, Long> playerRewardTotals) {
        this.configManager = configManager;
        this.playerRewardTotals = playerRewardTotals;
    }

    @EventHandler
    public void onRewardEvent(RewardEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if (player == null) {
            return;
        }
        playerRewardTotals.put(player.getUniqueId(), event.getReward());

        Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + "Player " + player.getName() + " has been rewarded " + event.getReward() + " playtime rewards.");
        Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + "Player's total rewards today: " + playerRewardTotals.get(player.getUniqueId()));

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
