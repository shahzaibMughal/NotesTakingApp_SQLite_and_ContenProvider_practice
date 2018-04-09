package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.net.Uri;
import android.provider.BaseColumns;

public class NotesTakingAppDatabaseContract {


    public static final String AUTHORITY = "com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String NOTES_PATH = "Notes";

    private NotesTakingAppDatabaseContract(){

    }

    public static class NotesContract implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(NOTES_PATH).build();
        // no need to add _id because its automatically inherits from BaseColumn
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_NOTE_TITLE = "Title";
        public static final String COLUMN_NOTE_DESCRIPTION = "Description";
    }
}
