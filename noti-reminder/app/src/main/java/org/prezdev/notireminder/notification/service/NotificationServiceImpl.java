package org.prezdev.notireminder.notification.service;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.prezdev.notireminder.notification.model.Notification;
import org.prezdev.notireminder.notification.model.NotificationIcon;
import org.prezdev.notireminder.notification.model.NotificationStatus;
import org.prezdev.notireminder.notification.model.NotificationText;
import org.prezdev.notireminder.share.service.GenericService;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl extends GenericService implements NotificationService {

    private List<Notification> notifications;

    public NotificationServiceImpl(Context context){
        super.init(context);
        notifications = new ArrayList<>();
    }

    @Override
    public void create(Notification notification) {
        addNotification(notification);

        NotificationCompat.Builder builder = createNotification(notification);

        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(super.getContext());

        notificationManager.notify(generateId(), builder.build());
    }

    private void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    private NotificationCompat.Builder createNotification(Notification notification) {
        Bitmap largeIconBitmap                  = createLargeIcon(notification);
        PendingIntent pendingIntent             = createPendingIntent(notification);
        NotificationCompat.Builder builder      = createBuilder(notification);

        NotificationStatus notificationStatus   = notification.getNotificationStatus();
        NotificationText notificationText       = notification.getNotificationText();
        NotificationIcon notificationIcon       = notification.getNotificationIcon();

        return builder
            .setSmallIcon       (notificationIcon.getSmallIcon())
            .setLargeIcon       (largeIconBitmap)
            .setContentTitle    (notificationText.getTitle())
            .setContentText     (notificationText.getText())
            .setStyle           (createBigTextStyle(notificationText))
            .setStyle           (createBigPictureStyle(notificationIcon))
            .setOngoing         (notificationStatus.isPermanent())
            .setAutoCancel      (notificationStatus.isAutoClose())
            .setContentIntent   (pendingIntent);
            //.addAction(R.drawable.ic_menu_camera, "Texto del botón", contIntent);
    }

    private NotificationCompat.Style createBigPictureStyle(NotificationIcon notificationIcon) {
        Bitmap bitmap = BitmapFactory.decodeResource(
            super.getResources(),
            notificationIcon.getBigPicture()
        );

        return new NotificationCompat.BigPictureStyle().bigPicture(bitmap)/*.bigLargeIcon(null)*/;
    }

    private int generateId() {
        return this.notifications.size();
    }

    private Bitmap createLargeIcon(Notification notification) {
        NotificationIcon notificationIcon = notification.getNotificationIcon();
        return BitmapFactory.decodeResource(
            super.getResources(),
            notificationIcon.getLargeIcon()
        );
    }

    private PendingIntent createPendingIntent(Notification notification) {
        return PendingIntent.getActivity(super.getContext(), 0, notification.getIntent(), 0);
    }

    private NotificationCompat.Builder createBuilder(Notification notification) {
        return new NotificationCompat.Builder(
                super.getContext(),
                notification.getChannel().getId()
        );
    }

    private NotificationCompat.Style createBigTextStyle(NotificationText notificationText) {
        return new NotificationCompat.BigTextStyle().bigText(notificationText.getBigText());
    }
}
