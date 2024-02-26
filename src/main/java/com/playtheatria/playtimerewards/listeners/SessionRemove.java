package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.events.SessionRemoveEvent;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class SessionRemove implements Listener {

    private final List<PlayerSession> playerSessions;

    public SessionRemove(List<PlayerSession> playerSessions) {
        this.playerSessions = playerSessions;
    }

    @EventHandler
    public void onSessionRemove(SessionRemoveEvent event) {
        // Remove the player's session from the list
        playerSessions.removeIf(playerSession -> playerSession.playerUUID().equals(event.getPlayerUUID()));
    }
}
