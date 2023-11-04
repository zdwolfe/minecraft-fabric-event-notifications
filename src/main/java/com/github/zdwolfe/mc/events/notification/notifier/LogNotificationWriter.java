package com.github.zdwolfe.mc.events.notification.notifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zdwolfe.mc.events.notification.Notification;
import com.github.zdwolfe.mc.events.notification.model.NotificationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * A Notifier that emits notifications to the server log
 */
public class LogNotificationWriter implements NotificationWriter {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogNotificationWriter.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Future<NotificationResult> write(final Notification notification) {
        try {
            LOGGER.info("Notification: " + mapper.writeValueAsString(notification));
            return CompletableFuture.supplyAsync(() -> new NotificationResult("log"));
        } catch (JsonProcessingException e) {
            return CompletableFuture.supplyAsync(() -> new NotificationResult(e, "log"));
        }
    }
}
