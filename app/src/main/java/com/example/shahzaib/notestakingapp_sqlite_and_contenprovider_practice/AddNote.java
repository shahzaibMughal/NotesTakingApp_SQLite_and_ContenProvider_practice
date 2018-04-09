package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

    }

    public void saveNote(View view)
    {
        EditText noteTitle = findViewById(R.id.noteTitle);
        EditText noteDescription = findViewById(R.id.noteDescription);

        String title = noteTitle.getText().toString();
        String description = noteDescription.getText().toString();

        if(title.length()>0 && description.length()>0)
        {
            ContentResolver resolver = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE,title);
            values.put(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_DESCRIPTION,description);
            resolver.insert(NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI,values);

            noteTitle.setText(""); noteDescription.setText("");
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    public void finishActivity(View view)
    {
        finish();
    }
}
