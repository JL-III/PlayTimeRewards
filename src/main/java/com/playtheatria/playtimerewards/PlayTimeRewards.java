package com.playtheatria.playtimerewards;

import com.playtheatria.playtimerewards.commands.AdminCommands;
import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.listeners.*;
import com.playtheatria.playtimerewards.records.PlayerSession;
import com.playtheatria.playtimerewards.time.DayCheck;
import com.playtheatria.playtimerewards.time.InitialPlayerSessionLoad;
import com.playtheatria.playtimerewards.time.RewardDispatch;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class PlayTimeRewards extends JavaPlugin {

    private final List<PlayerSession> playerSessions = new CopyOnWriteArrayList<>();
    private final List<UUID> playerOverLimitList = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<UUID, Long> playerRewardTotals = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        new InitialPlayerSessionLoad(this, playerSessions).run();
        ConfigManager configManager = new ConfigManager(this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogin(playerSessions), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this, configManager), this);
        Bukkit.getPluginManager().registerEvents(new Reward(configManager, playerRewardTotals), this);
        Bukkit.getPluginManager().registerEvents(new SessionRemove(playerSessions), this);
        Bukkit.getPluginManager().registerEvents(new DayChange(playerRewardTotals, playerOverLimitList), this);
        Bukkit.getPluginManager().registerEvents(new RewardCheck(configManager, playerRewardTotals, playerOverLimitList), this);
        Objects.requireNonNull(getCommand("ptr")).setExecutor(new AdminCommands(configManager, playerSessions, playerOverLimitList));

        new RewardDispatch(configManager, playerSessions, playerOverLimitList).runTaskTimer(this, 0, 20 * configManager.getRewardInterval());
        new DayCheck(this);
    }
}
