package org.prezdev.notireminder.test;

import android.content.Context;
import android.view.View;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class WorkerButtonOnClickListener implements View.OnClickListener {

    private Context context;

    public WorkerButtonOnClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        WorkRequest workRequest =
                new OneTimeWorkRequest.Builder(WorkerTest.class)
                        .setInitialDelay(3, TimeUnit.SECONDS)
                        .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }
}
