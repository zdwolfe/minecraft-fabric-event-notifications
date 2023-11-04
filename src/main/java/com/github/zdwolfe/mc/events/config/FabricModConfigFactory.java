package com.github.zdwolfe.mc.events.config;

import com.github.zdwolfe.mc.events.config.model.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricModConfigFactory extends ModConfigFactory {

    @Override
    public ModConfig get() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("event-notifications.json");
        try {
            return this.get(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
