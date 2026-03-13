package org.prezdev.notireminder.share.service;

import android.content.Context;
import android.content.res.Resources;

public class GenericService implements Service{
    private Context context;

    @Override
    public void init(Context context){
        this.context = context;
    }

    public Context getContext(){
        return this.context;
    }

    public Resources getResources(){
        return this.context.getResources();
    }
}
