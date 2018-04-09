package com.example.shahzaib.notestakingapp_sqlite_and_contenprovider_practice;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Cursor cursor;

    public RecyclerViewAdapter(Cursor cursor)
    {
        this.cursor = cursor;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_recycler_view,parent,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.titleTV.setText(cursor.getString(cursor.getColumnIndex(NotesTakingAppDatabaseContract.NotesContract.COLUMN_NOTE_TITLE)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titleTV;
        ViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
        }
    }
}
