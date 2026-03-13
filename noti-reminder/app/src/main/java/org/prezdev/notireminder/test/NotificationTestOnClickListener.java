package org.prezdev.notireminder.test;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import org.prezdev.notireminder.MainActivity;
import org.prezdev.notireminder.R;
import org.prezdev.notireminder.notification.channel.model.Channel;
import org.prezdev.notireminder.notification.model.Notification;
import org.prezdev.notireminder.notification.model.NotificationIcon;
import org.prezdev.notireminder.notification.model.NotificationStatus;
import org.prezdev.notireminder.notification.model.NotificationText;
import org.prezdev.notireminder.notification.service.NotificationService;
import org.prezdev.notireminder.share.service.ServiceFactory;

// https://developer.android.com/training/notify-user/build-notification#java
// https://developer.android.com/guide/topics/ui/notifiers/notifications#Templates
public class NotificationTestOnClickListener implements View.OnClickListener {

    private Context context;
    private ServiceFactory serviceFactory;
    private NotificationService notificationService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationTestOnClickListener(Context context) {
        this.context = context;
        this.serviceFactory = ServiceFactory.getInstance(context);
        this.notificationService = serviceFactory.getService(NotificationService.class);
    }

    @Override
    public void onClick(View v) {
        Notification notification = new Notification();

        NotificationText notificationText = new NotificationText();
        notificationText.setTitle("Recordatorio");
        notificationText.setText("Comprar remedios para hija");
        notificationText.setBigText("Recordatorio expandido");

        NotificationStatus notificationStatus = new NotificationStatus();
        notificationStatus.setAutoClose(true);

        NotificationIcon notificationIcon = new NotificationIcon();
        notificationIcon.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        notificationIcon.setLargeIcon(R.drawable.logo);
        notificationIcon.setBigPicture(R.drawable.logo);

        Intent intent = new Intent(context, MainActivity.class);

        notification.setNotificationStatus(notificationStatus);
        notification.setNotificationText(notificationText);
        notification.setNotificationIcon(notificationIcon);
        notification.setChannel(Channel.CHANNEL_3);

        notification.setIntent(intent);

        notificationService.create(notification);
    }


}
