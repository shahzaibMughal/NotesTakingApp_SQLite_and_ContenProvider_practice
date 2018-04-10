package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
    EditText noteTitle;
    EditText noteDescription ;
    boolean shouldEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteTitle = findViewById(R.id.noteTitle);
        noteDescription = findViewById(R.id.noteDescription);
        shouldEdit =  getIntent().getBooleanExtra("shouldEdit",false);
        if(shouldEdit)
        {
            String id = getIntent().getStringExtra("_id");
            Cursor cursor = getContentResolver().query(
                    NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI.buildUpon().appendPath(id).build(),
                    null,null,null,null);
            cursor.moveToFirst();
            noteTitle.setText(cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE)));
            noteDescription.setText(cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_DESCRIPTION)));
        }

    }




    public void saveNote(View view)
    {
        String title = noteTitle.getText().toString();
        String description = noteDescription.getText().toString();

        if(title.length()>0 && description.length()>0)
        {
            ContentResolver resolver = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE,title);
            values.put(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_DESCRIPTION,description);

            if(shouldEdit)
            {
                String id = getIntent().getStringExtra("_id");
                int result = resolver.update(NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI.buildUpon().appendPath(id).build(),values,null,null);
                if(result>0) Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Error! Not updated", Toast.LENGTH_SHORT).show();
            }
            else
            {
                resolver.insert(NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI,values);
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
            noteTitle.setText(""); noteDescription.setText("");
            finish();
        }


    }





    public void finishActivity(View view)
    {
        finish();
    }
}
