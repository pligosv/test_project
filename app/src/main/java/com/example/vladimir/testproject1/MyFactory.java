package com.example.vladimir.testproject1;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by Vladimir on 17.05.2016.
 */
public class MyFactory implements RemoteViewsService.RemoteViewsFactory {

    final Uri CONTACT_URI = Uri.parse("content://com.example.vladimir.contentprovider/mytab");

    Context context;
    int widgetID;
    ArrayList<String> data;

    MyFactory(Context ctx, Intent intent) {
        context = ctx;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        data = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rView.setTextViewText(R.id.tvItemText, data.get(position));
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

        Cursor cursor = context.getContentResolver().query(CONTACT_URI, null, null, null, null);
        data.clear();
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(cursor.getColumnIndex("firstName")));
            } while (cursor.moveToNext());
        }

    }

    @Override
    public void onDestroy() {

    }

}
