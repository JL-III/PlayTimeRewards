package com.playtheatria.playtimerewards.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RewardEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID playerUUID;

    private final long reward;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() { return HANDLERS; }

    public RewardEvent(UUID playerUUID, int reward) {
        this.playerUUID = playerUUID;
        this.reward = reward;
    }

    public UUID getPlayerUUID() { return playerUUID; }

    public long getReward() { return reward; }
}
