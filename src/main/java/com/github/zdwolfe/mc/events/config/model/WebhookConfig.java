package com.github.zdwolfe.mc.events.config.model;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record WebhookConfig(String url) {
}
