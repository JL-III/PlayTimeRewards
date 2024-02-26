package com.playtheatria.playtimerewards.records;

import java.util.UUID;

public record PlayerSession(long loginTime, UUID playerUUID) {}