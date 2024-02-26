package com.playtheatria.playtimerewards.time;


import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.events.RewardEvent;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class RewardDispatch extends BukkitRunnable {

    private final ConfigManager configManager;
    private final List<PlayerSession> playerSessions;
    private final List<UUID> playerOverLimitList;

    public RewardDispatch(ConfigManager configManager, List<PlayerSession> playerSessions, List<UUID> playerOverLimitList) {
        this.configManager = configManager;
        this.playerSessions = playerSessions;
        this.playerOverLimitList = playerOverLimitList;
    }

    @Override
    public void run() {
        Bukkit.getConsoleSender().sendMessage("Calculating rewards for player sessions...");
        Bukkit.getConsoleSender().sendMessage(playerSessions.size() + " in sessions list.");
        playerSessions.forEach(playerSession -> {
            if (playerOverLimitList.contains(playerSession.playerUUID())) {
                return;
            }
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
