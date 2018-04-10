package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>,RecyclerViewAdapter.OnListItemClickListener{

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter();
        adapter.setOnListItemClickListener(this);
        getLoaderManager().initLoader(10,null,this);


        /******** On Swipe, delete the list item*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
              // no neeed this function
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String swipedItemID = (String) viewHolder.itemView.getTag();
                deleteNote(swipedItemID);
            }
        }).attachToRecyclerView(recyclerView);

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

    public void viewNote(String _id)
    {
        Intent intent = new Intent(this, ViewNote.class);
        intent.putExtra("_id",_id);
        startActivity(intent);
    }

    private void deleteNote(String swipedItemID)
    {
        String selection = NotesTakingAppDatabaseContract.NotesContract._ID+"=?";
        String[] selectionArgs = new String[]{swipedItemID};

        int result = getContentResolver().delete(NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI,selection,selectionArgs);
        if(result>0)
        {
            Toast.makeText(this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Item Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void editNote(String _id)
    {
        Intent intent = new Intent(this,AddNote.class);
        intent.putExtra("shouldEdit",true);
        intent.putExtra("_id",_id);
        startActivity(intent);
    }







    /******** On Recycler Item Listeneres*/
    @Override
    public void onListItemClick(String _ID) {
        viewNote(_ID);
    }

    @Override
    public void onListItemLongClick(String _ID) {
        editNote(_ID);
    }





    /************* Loader Callbacks*/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,NotesTakingAppDatabaseContract.NotesContract.CONTENT_URI,
                        null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!=null && cursor.getCount()>0)
        {
            adapter.swapCursor(cursor);
            setup_recyclerView();
        }
        else
        {
            Log.i("12345","No data returned");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // when underlying database is updated, replace the old curse
        adapter.swapCursor(null);
        setup_recyclerView();
    }


}

