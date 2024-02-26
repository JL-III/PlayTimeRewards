package com.playtheatria.playtimerewards.time;

import com.playtheatria.playtimerewards.events.SessionRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SessionExpire extends BukkitRunnable {

    private final UUID playerUUID;

    public SessionExpire(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void run() {
        // Remove the player's session from the list
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (!offlinePlayer.isOnline()) {
            // player returns null if they are offline
            Bukkit.getConsoleSender().sendMessage("Player " + offlinePlayer.getName() + " has been removed from the session list.");
            Bukkit.getPluginManager().callEvent(new SessionRemoveEvent(playerUUID));
        }
    }
}
