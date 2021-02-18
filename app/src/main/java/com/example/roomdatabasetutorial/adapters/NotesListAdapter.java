package com.example.roomdatabasetutorial.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabasetutorial.R;
import com.example.roomdatabasetutorial.model.Note;

import java.util.List;



public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {
    private static final String TAG = "NotesListAdapter";
    List<Note> mNotes;

    ItemClickListener mItemClickListener;
    public  interface ItemClickListener{
        public void onNoteDeleteClickListener(Note note);
    }

    public NotesListAdapter(ItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes!=null){
            Note note = mNotes.get(position);
            holder.textViewNote.setText(note.getNoteText());

            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener!=null){
                        mItemClickListener.onNoteDeleteClickListener(note);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null){
            return mNotes.size();
        }
        return 0;
    }

    public void setNotes(List<Note> notes) {
        Log.d(TAG, "setNotes: called");
        mNotes = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNote;
        Button buttonDelete;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.notes_list_item_note);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
