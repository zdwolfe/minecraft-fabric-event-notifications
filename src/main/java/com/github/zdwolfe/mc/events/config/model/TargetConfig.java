package com.github.zdwolfe.mc.events.config.model;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record TargetConfig(@Nullable WebhookConfig webhook) {
}
