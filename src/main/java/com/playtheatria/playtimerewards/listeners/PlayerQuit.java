package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.PlayTimeRewards;
import com.playtheatria.playtimerewards.config.ConfigManager;
import com.playtheatria.playtimerewards.time.SessionExpire;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final PlayTimeRewards plugin;

    private final ConfigManager configManager;

    public PlayerQuit(PlayTimeRewards plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Set a timer to check if the player is still offline after 5 minutes.
        // If the player is still offline, remove the player's session from the list.
        new SessionExpire(event.getPlayer().getUniqueId()).runTaskLater(plugin, 20 * configManager.getSessionExpire()); // 5 minutes (20 ticks per second * 60 seconds per minute * 5 minutes
    }
}
