package com.example.vladimir.testproject1;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Vladimir on 17.05.2016.
 */
public class MyService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        return new MyFactory(getApplicationContext(),intent);
    }
}
