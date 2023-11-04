package com.github.zdwolfe.mc.events.notification.notifier;

import com.github.zdwolfe.mc.events.notification.Notification;
import com.github.zdwolfe.mc.events.notification.model.NotificationResult;

import java.util.concurrent.Future;

public interface NotificationWriter {
  Future<NotificationResult> write(Notification notification);
}
