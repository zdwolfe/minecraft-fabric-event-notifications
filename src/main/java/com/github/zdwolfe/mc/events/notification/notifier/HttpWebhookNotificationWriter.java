package com.github.zdwolfe.mc.events.notification.notifier;

import com.github.zdwolfe.mc.events.config.model.WebhookConfig;
import com.github.zdwolfe.mc.events.notification.Notification;
import com.github.zdwolfe.mc.events.notification.model.NotificationResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Sends webhookConfig.webhookUrl a JSON message such as:
 * { "content": "<username> has joined!" }
 */
public class HttpWebhookNotificationWriter implements NotificationWriter {

  private final WebhookConfig webhook;
  private final String source;

  public HttpWebhookNotificationWriter(
          final WebhookConfig webhook
  ) {
    this.webhook = webhook;
    this.source = "webhook";
  }

  @Override
  public Future<NotificationResult> write(final Notification notification) {
    URL url;
    try {
      url = new URL(this.webhook.url());
    } catch (MalformedURLException e) {
      return CompletableFuture.supplyAsync(() -> new NotificationResult(e, this.source));
    }

    final URL finalUrl = url;
    return CompletableFuture.supplyAsync(() -> {
      try {
        HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();

        connection.setRequestProperty("Content-Type", "application/json;");
        connection.addRequestProperty("User-Agent", String.format("MinecraftPluginDiscordNotifier"));

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        try(OutputStream os = connection.getOutputStream()) {
          byte[] input = getJsonRequestString(notification.message()).getBytes("utf-8");
          os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), "utf-8"))) {
          StringBuilder response = new StringBuilder();
          String responseLine = null;
          while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
          }
          final String responseString = response.toString();
        }

        return new NotificationResult(this.source);

      } catch (Exception e) {
        return new NotificationResult(e, this.source);
      }

    });
  }

  private String getJsonRequestString(final String message) {
    return String.format("{\"content\": \"%s\"}", message);
  }

}
