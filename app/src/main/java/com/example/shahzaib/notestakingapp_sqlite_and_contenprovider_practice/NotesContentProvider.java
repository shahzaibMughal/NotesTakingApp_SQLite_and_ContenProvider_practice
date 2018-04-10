package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class NotesContentProvider extends ContentProvider {

    private static final int NOTES = 100;
    private static final int NOTES_WITH_ID = 101;


    DBHelper dbHelper;
    UriMatcher uriMatcher  = buildUriMatcher();




    public UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NotesTakingAppDatabaseContract.AUTHORITY,NotesTakingAppDatabaseContract.NOTES_PATH,NOTES);
        uriMatcher.addURI(NotesTakingAppDatabaseContract.AUTHORITY,NotesTakingAppDatabaseContract.NOTES_PATH+"/#",NOTES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        int result = uriMatcher.match(uri);
        switch (result)
        {
            case NOTES:
                cursor = db.query(NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);
                return cursor;

            case NOTES_WITH_ID:
                String id = uri.getPathSegments().get(1); // this will extract the last path from the uri, in our case it is id
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                cursor = db.query(NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME,projection,mSelection,mSelectionArgs,null,null,sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);
                return cursor;

                default:
                    throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = uriMatcher.match(uri);
        switch (result)
        {
            case NOTES:
                db.insert(NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME,null,contentValues);
                db.close();
                getContext().getContentResolver().notifyChange(uri,null);
                return uri;

                default:
                    throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match)
        {
            case NOTES:
                int deletedCount = db.delete(NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME,selection,selectionArgs);
                getContext().getContentResolver().notifyChange(uri,null);
                return deletedCount;

                default:
                    throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
