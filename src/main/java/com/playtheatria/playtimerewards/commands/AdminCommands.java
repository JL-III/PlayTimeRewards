package com.playtheatria.playtimerewards.commands;

import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AdminCommands implements CommandExecutor {

    private final ConfigManager configManager;
    private final List<PlayerSession> playerSessions;
    private final List<UUID> playerBlackList;

    public AdminCommands(ConfigManager configManager, List<PlayerSession> playerSessions, List<UUID> playerBlackList) {
        this.configManager = configManager;
        this.playerSessions = playerSessions;
        this.playerBlackList = playerBlackList;
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
                commandSender.sendMessage("Blacklist size: " + playerBlackList.size());
                for (UUID uuid : playerBlackList) {
                    commandSender.sendMessage(" - Blacklisted: " + Bukkit.getOfflinePlayer(uuid).getName());
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
