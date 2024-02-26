package com.playtheatria.playtimerewards.time;


import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.events.RewardEvent;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RewardCheck extends BukkitRunnable {

    private final ConfigManager configManager;

    private final List<PlayerSession> playerSessions;

    public RewardCheck(ConfigManager configManager, List<PlayerSession> playerSessions) {
        this.configManager = configManager;
        this.playerSessions = playerSessions;
    }

    @Override
    public void run() {
        Bukkit.getConsoleSender().sendMessage("Calculating rewards for player sessions...");
        Bukkit.getConsoleSender().sendMessage(playerSessions.size() + " in sessions list.");
        playerSessions.forEach(playerSession -> {
            Bukkit.getPluginManager().callEvent(new RewardEvent(playerSession.playerUUID(), getRewardAmount(playerSession)));
        });
    }

    private int getRewardAmount(PlayerSession playerSession) {
        long time_comparison = ((System.currentTimeMillis() - playerSession.loginTime()) / 1000) / 60;

        if (time_comparison >= 40) {
            return configManager.getRewardBaseAmount() * 5;
        }
        if (time_comparison >= 30) {
            return configManager.getRewardBaseAmount() * 4;
        }
        if (time_comparison >= 20) {
            return configManager.getRewardBaseAmount() * 3;
        }
        if (time_comparison >= 10) {
            return configManager.getRewardBaseAmount() * 2;
        }
        return configManager.getRewardBaseAmount();
    }
}
