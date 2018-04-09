package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Notes.db";
    public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String QUERY_CREATE_TABLE = "CREATE TABLE "+ NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME +
                " ("+
                NotesTakingAppDatabaseContract.NotesContract._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE+" TEXT NOT NULL, " +
                NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_DESCRIPTION+" TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS "+ NotesTakingAppDatabaseContract.NotesContract.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
