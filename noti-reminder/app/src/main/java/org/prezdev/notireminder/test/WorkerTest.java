package org.prezdev.notireminder.test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.prezdev.notireminder.MainActivity;
import org.prezdev.notireminder.R;

public class WorkerTest extends Worker {

    public static int id;

    public WorkerTest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        Bitmap bitmap = BitmapFactory.decodeResource(super.getApplicationContext().getResources(), R.drawable.logo);

        String bigText =
                "Lorem Ipsum is simply dummy text of the " +
                        "printing and typesetting industry. " +
                        "Lorem Ipsum has been the industry's standard dummy " +
                        "text ever since the 1500s, when an unknown printer " +
                        "took a galley of type and scrambled it to make a type " +
                        "specimen book. It has survived not only five centuries, " +
                        "but also the leap into electronic typesetting, " +
                        "remaining essentially unchanged. It was popularised " +
                        "in the 1960s with the release of Letraset sheets " +
                        "containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker " +
                        "including versions of Lorem Ipsum";

        Intent intent = new Intent(super.getApplicationContext(), MainActivity.class);

        intent.putExtra("option", 1);

        PendingIntent pendingIntent = PendingIntent.getActivity(super.getApplicationContext(), 0, intent, 0);

        /*
        Intent snoozeIntent = new Intent(context, MainActivity.class);

        snoozeIntent.setAction("ACTION_SNOOZE");
        snoozeIntent.putExtra("infoExtra", 0);

        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);
                */

        NotificationCompat.Builder builder = new NotificationCompat.Builder(super.getApplicationContext(), "channel1")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setLargeIcon(bitmap)
            .setContentTitle("Título de la notificación")
            .setContentText("Texto de la notificación")
            .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
            //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
            .setContentInfo("4")
            //.setOngoing(true) // Notificación permanente*/
            //.setAutoCancel(true) // cuando haga click se cierra;
            .setContentIntent(pendingIntent);
            //.addAction(R.drawable.ic_menu_camera, "Cambiar wea", contIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(super.getApplicationContext());

        notificationManager.notify(++id, builder.build());

        Log.i("doWork()", "OK ["+id+"]");

        return Result.success();
    }
}
