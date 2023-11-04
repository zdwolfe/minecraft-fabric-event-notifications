package com.github.zdwolfe.mc.events.config.model;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public record ModConfig(
        List<NotificationConfig> notifications
) {
}
