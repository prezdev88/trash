package org.prezdev.notireminder.notification.model;

import android.content.Intent;

import org.prezdev.notireminder.notification.channel.model.Channel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Notification {
    private NotificationText notificationText;
    private NotificationStatus notificationStatus;
    private NotificationIcon notificationIcon;
    private Channel channel;
    private Intent intent;
}
