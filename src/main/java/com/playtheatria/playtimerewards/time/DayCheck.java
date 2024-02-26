package com.playtheatria.playtimerewards.time;

import com.playtheatria.playtimerewards.PlayTimeRewards;
import com.playtheatria.playtimerewards.events.DayChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;

public class DayCheck {

    private final PlayTimeRewards plugin;

    LocalDate currentDate;

    public DayCheck(PlayTimeRewards plugin) {
        this.plugin = plugin;
        this.currentDate = LocalDate.now();
        run();
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentDate.isBefore(LocalDate.now())) {
                    currentDate = LocalDate.now();
                    Bukkit.getConsoleSender().sendMessage("Day changed to " + currentDate);
                    plugin.getServer().getPluginManager().callEvent(new DayChangeEvent());
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public LocalDate getLocalDate() {
        return currentDate;
    }
}
