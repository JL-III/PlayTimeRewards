package com.playtheatria.playtimerewards.time;

import com.playtheatria.playtimerewards.PlayTimeRewards;
import com.playtheatria.playtimerewards.records.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class InitialPlayerLoad {

    private final PlayTimeRewards plugin;

    private final List<PlayerSession> playerSessions;

    public InitialPlayerLoad(PlayTimeRewards plugin, List<PlayerSession> playerSessions) {
        this.plugin = plugin;
        this.playerSessions = playerSessions;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player :Bukkit.getOnlinePlayers()) {
                    playerSessions.add(new PlayerSession(System.currentTimeMillis(), player.getUniqueId()));
                }
            }
        }.runTaskLater(plugin, 20 * 5);
    }
}
