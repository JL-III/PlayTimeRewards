package com.playtheatria.playtimerewards.config;

import com.playtheatria.playtimerewards.PlayTimeRewards;

public class ConfigManager {

    public final PlayTimeRewards plugin;

    private int reward_base_amount;

    private long reward_interval;

    private int reward_cap;

    private long session_expire;

    public ConfigManager(PlayTimeRewards plugin) {
        this.plugin = plugin;
        this.reward_base_amount = plugin.getConfig().getInt("reward_base_amount", 2000);
        this.reward_interval = plugin.getConfig().getLong("reward_interval", 10);
        this.reward_cap = plugin.getConfig().getInt("reward_cap", 100000);
        this.session_expire = plugin.getConfig().getLong("session_expire", 5);
    }

    public void reload() {
        plugin.reloadConfig();
        this.reward_base_amount = plugin.getConfig().getInt("reward_base_amount", 2000);
        this.reward_interval = plugin.getConfig().getLong("reward_interval", 10);
        this.reward_cap = plugin.getConfig().getInt("reward_cap", 100000);
        this.session_expire = plugin.getConfig().getLong("session_expire", 5);
    }

    public int getRewardBaseAmount() {
        return reward_base_amount;
    }

    public long getRewardInterval() {
        return reward_interval;
    }

    public int getRewardCap() {
        return reward_cap;
    }

    public long getSessionExpire() { return session_expire; }
}
