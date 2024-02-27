package com.playtheatria.playtimerewards.commands;

import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AdminCommands implements CommandExecutor {

    private final ConfigManager configManager;
    private final List<PlayerSession> playerSessions;
    private final List<UUID> playerOverLimitList;

    public AdminCommands(ConfigManager configManager, List<PlayerSession> playerSessions, List<UUID> playerOverLimitList) {
        this.configManager = configManager;
        this.playerSessions = playerSessions;
        this.playerOverLimitList = playerOverLimitList;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        if (!commandSender.hasPermission("ptr.admin")) {
            return false;
        }
        if (args.length == 0) {
            commandSender.sendMessage("No arguments provided.");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                configManager.reload();
                commandSender.sendMessage("Config reloaded.");
                return true;
            }
            if (args[0].equalsIgnoreCase("debug")) {
                commandSender.sendMessage("Player sessions list size: " + playerSessions.size());
                for (PlayerSession playerSession : playerSessions) {
                    commandSender.sendMessage(" - Player: " + Bukkit.getOfflinePlayer(playerSession.playerUUID()).getName() + ", Time: " + new Date(playerSession.loginTime()));
                }
                commandSender.sendMessage("OverLimitList size: " + playerOverLimitList.size());
                for (UUID uuid : playerOverLimitList) {
                    commandSender.sendMessage(" - OverLimit: " + Bukkit.getOfflinePlayer(uuid).getName());
                }
                // get the number of tasks running that belong to this plugin
                commandSender.sendMessage("Tasks running: " + Bukkit.getScheduler().getPendingTasks().stream().filter(task -> task.getOwner().getName().equalsIgnoreCase("PlayTimeRewards")).count());

                for (BukkitTask task : Bukkit.getScheduler().getPendingTasks()) {
                    if (task.getOwner().getName().equalsIgnoreCase("PlayTimeRewards")) {
                        commandSender.sendMessage(" - Task: " + task.getTaskId());
                    }
                }
            } else {
                commandSender.sendMessage("Invalid argument.");
            }
        } else {
            commandSender.sendMessage("Too many arguments provided.");
        }
        return true;
    }
}
