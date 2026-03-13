package org.prezdev.notireminder.notification.channel.service;

import android.app.NotificationChannel;

import java.util.List;

public interface NotificationChannelService{
    List<NotificationChannel> getNotificationChannels();
}
