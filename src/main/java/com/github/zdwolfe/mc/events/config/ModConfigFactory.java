package com.github.zdwolfe.mc.events.config;

import com.github.zdwolfe.mc.events.config.model.ModConfig;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class ModConfigFactory {
    static final ModConfig DEFAULT = new ModConfig(List.of());

    public abstract ModConfig get();

    public final ModConfig get(Path path) throws Exception {
        JacksonConfigurationLoader loader = JacksonConfigurationLoader.builder().path(path).build();
        BasicConfigurationNode root = getOrCreateRoot(path, loader);
        ObjectMapper<ModConfig> mapper = ObjectMapper.factory().get(ModConfig.class);

        ModConfig config = getOrCreateConfig(path, mapper, root);
        mapper.save(config, root);
        loader.save(root);

        return config;
    }

    private static ModConfig getOrCreateConfig(
            Path path,
            ObjectMapper<ModConfig> mapper,
            BasicConfigurationNode root) throws SerializationException {
        if (Files.exists(path)) {
            return mapper.load(root);
        } else {
            return DEFAULT;
        }
    }

    private BasicConfigurationNode getOrCreateRoot(
            Path path,
            JacksonConfigurationLoader loader
    ) throws ConfigurateException {
        if (Files.exists(path)) {
            return loader.load();
        } else {
            return loader.createNode();
        }
    }
}
