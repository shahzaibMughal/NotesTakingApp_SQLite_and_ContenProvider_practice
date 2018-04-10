package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ViewNote extends AppCompatActivity {

    TextView noteTitle, noteDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        noteTitle = findViewById(R.id.noteTitle);
        noteDescription = findViewById(R.id.noteDescription);

        String id = getIntent().getStringExtra("_id");


            Cursor cursor = getContentResolver().query(
                    NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI.buildUpon().appendPath(id).build(),
                    null,
                    null,
                    null,
                    null);

            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_DESCRIPTION));
            cursor.close();
            noteTitle.setText(title);
            noteDescription.setText(description);

    }
}
