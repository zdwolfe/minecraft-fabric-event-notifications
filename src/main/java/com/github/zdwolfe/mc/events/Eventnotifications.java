package com.github.zdwolfe.mc.events;

import com.github.zdwolfe.mc.events.config.FabricModConfigFactory;
import com.github.zdwolfe.mc.events.config.model.ModConfig;
import com.github.zdwolfe.mc.events.config.model.NotificationConfig;
import com.github.zdwolfe.mc.events.notification.Notification;
import com.github.zdwolfe.mc.events.notification.notifier.HttpWebhookNotificationWriter;
import com.github.zdwolfe.mc.events.notification.notifier.LogNotificationWriter;
import com.github.zdwolfe.mc.events.notification.notifier.NotificationWriter;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Eventnotifications implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Eventnotifications.class);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized " + this.getClass().getName());

        FabricModConfigFactory modConfigFactory = new FabricModConfigFactory();
        ModConfig config = modConfigFactory.get();

        if (config.notifications().size() <= 1) {
            LOGGER.info("No notifications configured. See config/event-notifications.json");
        }

        List<NotificationWriter> writers = new ArrayList<>();
        writers.add(new LogNotificationWriter());

        if (!config.notifications().isEmpty()) {
            // @TODO get all notification configs not just the first
            NotificationConfig notificationConfig = config.notifications().get(0);
            if (notificationConfig.target() != null && notificationConfig.target().webhook() != null) {
                writers.add(new HttpWebhookNotificationWriter(notificationConfig.target().webhook()));
            }
        }

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Notification notification = new Notification(
                    handler.getPlayer().getDisplayName().getString() + " connected!"
            );
            writers.forEach(w -> w.write(notification));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            Notification notification = new Notification(
                    handler.getPlayer().getDisplayName().getString() + " disconnected!"
            );
            writers.forEach(w -> w.write(notification));
        });
    }
}
