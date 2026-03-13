package org.prezdev.notireminder.notification.channel.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.prezdev.notireminder.notification.channel.model.Channel;
import org.prezdev.notireminder.share.service.GenericService;

import java.util.ArrayList;
import java.util.List;

public class NotificationChannelServiceImpl extends GenericService implements NotificationChannelService {
    private List<NotificationChannel> notificationChannels;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannelServiceImpl(Context context){
        super.init(context);
        this.notificationChannels = new ArrayList<>();

        initNotificationChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initNotificationChannels() {
        for(Channel channel : Channel.values()){
            addNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNotificationChannel(Channel channel) {
        Context context = super.getContext();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = createNotificationChannel(channel);
        notificationManager.createNotificationChannel(notificationChannel);

        this.notificationChannels.add(notificationChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel(Channel channel) {
        NotificationChannel notificationChannel =
            new NotificationChannel(
                channel.getId(),
                channel.getName(),
                NotificationManager.IMPORTANCE_DEFAULT
            );

        notificationChannel.setDescription(channel.getDescription());

        return notificationChannel;
    }

    @Override
    public List<NotificationChannel> getNotificationChannels() {
        return this.notificationChannels;
    }

}
