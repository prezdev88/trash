package org.prezdev.notireminder.share.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.prezdev.notireminder.notification.channel.service.NotificationChannelServiceImpl;
import org.prezdev.notireminder.notification.service.NotificationServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ServiceFactory {
    private List<GenericService> services;
    private Context context;

    private static ServiceFactory serviceFactory;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ServiceFactory getInstance(Context context){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory(context);
        }

        return serviceFactory;
    }

    public <T> T getService(Class<T> _class){
        for (GenericService genericService : services) {
            if(genericService.getClass().getName().contains(_class.getName())){
                return (T) genericService;
            }
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ServiceFactory(Context context){
        this.context = context;
        createServices();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createServices() {
        services = new ArrayList<>();

        services.add(new NotificationChannelServiceImpl(this.context));
        services.add(new NotificationServiceImpl(this.context));
    }
}
