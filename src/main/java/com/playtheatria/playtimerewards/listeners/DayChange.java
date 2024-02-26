package com.playtheatria.playtimerewards.listeners;

import com.playtheatria.playtimerewards.events.DayChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DayChange implements Listener {

    private final List<UUID> playerBlackList;

    private final ConcurrentHashMap<UUID, Long> playerRewardTotals;

    public DayChange(ConcurrentHashMap<UUID, Long> playerRewardTotals, List<UUID> playerBlackList) {
        this.playerRewardTotals = playerRewardTotals;
        this.playerBlackList = playerBlackList;
    }

    @EventHandler
    public void onDayChange(DayChangeEvent event) {
        this.playerRewardTotals.clear();
        this.playerBlackList.clear();
    }
}
