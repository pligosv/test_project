

package com.example.vladimir.testproject1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    final String LOG_TAG="myLogs";

    //URI authority
    static final String AUTHORITY = "com.example.vladimir.contentprovider";

    //path
    static final String CONTACT_PATH = "mytab";

    //Общий URI
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACT_PATH);

    // Типы данных
    // набор строк
    static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + CONTACT_PATH;

    // одна строка
    static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + CONTACT_PATH;

    //UriMatcher общий Uri
    static final int URI_CONTACTS = 1;

    //Uri с указанным ID
    static final int URI_CONTACTS_ID = 2;

    //описание и создание UriMatcher
    private static final UriMatcher uriMathcer;

    static {
        uriMathcer = new UriMatcher(UriMatcher.NO_MATCH);
        uriMathcer.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS);
        uriMathcer.addURI(AUTHORITY, CONTACT_PATH + "/#", URI_CONTACTS_ID);
    }

    DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG,"query, "+uri.toString());
        switch (uriMathcer.match(uri)) {
            case URI_CONTACTS:
                Log.d(LOG_TAG, "URI_CONTACTS");
                break;
            case URI_CONTACTS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = DBHelper.COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + DBHelper.COLUMN_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        Cursor cursor =dbHelper.getWritableDatabase().query(DBHelper.DB_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
        //просим просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),CONTACT_CONTENT_URI);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Log.d(LOG_TAG, "insert, " + uri.toString());
        return null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        Log.d(LOG_TAG, "delete, " + uri.toString());
        throw new SQLiteException("Unknown uri: " + uri);

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMathcer.match(uri)){
            case URI_CONTACTS:
                return CONTACT_CONTENT_TYPE;
            case URI_CONTACTS_ID:
                return CONTACT_CONTENT_ITEM_TYPE;
        }
        return null;
    }




    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(LOG_TAG, "update, " + uri.toString());
        // TODO: Implement this to handle requests to update one or more rows.
        throw new SQLiteException("Unknown uri: " + uri);
    }
}


