package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class PlayerLogin implements Listener {

    private final List<PlayerSession> playerSessions;

    public PlayerLogin(List<PlayerSession> playerSessions) {
        this.playerSessions = playerSessions;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (playerSessions.stream().noneMatch(playerSession -> playerSession.playerUUID().equals(event.getPlayer().getUniqueId()))) {
            Bukkit.getConsoleSender().sendMessage("Player " + event.getPlayer().getName() + " has logged in, adding them to the session list.");
            playerSessions.add(new PlayerSession(System.currentTimeMillis(), event.getPlayer().getUniqueId()));
        }
    }
}
