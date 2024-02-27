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

        long total = playerRewardTotals.getOrDefault(player.getUniqueId(), 0L) + event.getReward();
        playerRewardTotals.put(player.getUniqueId(), total);

        Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + "Player " + player.getName() + " has been rewarded " + event.getReward() + " playtime rewards.");
        Bukkit.getConsoleSender().sendMessage("[PlayTimeRewards]: " + "Player's total rewards today: " + playerRewardTotals.get(player.getUniqueId()));

        player.sendMessage(Component.text(
                "[PlayTimeRewards]: " + "You made some money!")
                .color(NamedTextColor.GREEN)
                .hoverEvent(HoverEvent.showText(
                        Component.text().content("-----Playtime Rewards report-----\n").color(NamedTextColor.YELLOW)
                                .append(Component.text().content("Current payout: ").color(NamedTextColor.YELLOW))
                                .append(Component.text().content(event.getReward() + "\n").color(NamedTextColor.GREEN))
                                .append(Component.text().content("Total payout today: ").color(NamedTextColor.YELLOW))
                                .append(Component.text().content(playerRewardTotals.get(player.getUniqueId()) + "\n").color(NamedTextColor.GREEN))
                                .append(Component.text().content("Daily cap: ").color(NamedTextColor.YELLOW))
                                .append(Component.text().content("" + configManager.getRewardCap()).color(NamedTextColor.GREEN))
                )));
    }
}
