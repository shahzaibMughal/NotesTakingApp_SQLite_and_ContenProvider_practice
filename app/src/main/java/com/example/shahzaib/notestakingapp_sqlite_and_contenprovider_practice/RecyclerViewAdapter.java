package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Cursor cursor;
    RecyclerViewAdapter.OnListItemClickListener listener;

    public RecyclerViewAdapter()
    {
        listener = null;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_recycler_view,parent,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.titleTV.setText(cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE)));
        holder.itemView.setTag(cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract._ID)));// we not want to start from 0



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                {
                    listener.onListItemClick(position+1); // we not want to start from 0
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }






    public void swapCursor(Cursor cursor)
    {
        this.cursor = cursor;
    }
    public void setOnListItemClickListener(RecyclerViewAdapter.OnListItemClickListener listener)
    {
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titleTV;
        ViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
        }
    }




    interface OnListItemClickListener
    {
        void onListItemClick(int position);
    }

}
