package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getContentResolver().query(NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI,
                null,null,null,null));
        setup_recyclerView();
    }


    private void setup_recyclerView()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addNote(View view)
    {
        startActivity(new Intent(this,AddNote.class));
    }

}

