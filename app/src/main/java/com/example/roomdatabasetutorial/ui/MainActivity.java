package com.example.roomdatabasetutorial.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.roomdatabasetutorial.R;
import com.example.roomdatabasetutorial.adapters.NotesListAdapter;
import com.example.roomdatabasetutorial.databases.NotesDao;
import com.example.roomdatabasetutorial.databases.NotesDatabase;
import com.example.roomdatabasetutorial.model.Note;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NotesListAdapter.ItemClickListener {
    private static final String TAG = "MainActivity";

    public static final int EDIT_ACTIVITY_REQUEST_CODE = 1;

    EditText editTextNote;
    Button buttonAddNote;
    RecyclerView notesRecyclerView;

    NotesDatabase notesDatabase;
    NotesDao notesDao;

    private LiveData<List<Note>> allNotes;
    NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);

        notesRecyclerView = findViewById(R.id.notes_recyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesListAdapter = new NotesListAdapter((NotesListAdapter.ItemClickListener) this);
        notesRecyclerView.setAdapter(notesListAdapter);

        notesDatabase = NotesDatabase.getDatabase(this);
        notesDao = notesDatabase.notesDao();

        allNotes = notesDao.getAllNotes();
        allNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d(TAG, "onChanged: observer called");
                notesListAdapter.setNotes(notes);
            }
        });

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(UUID.randomUUID().toString(), editTextNote.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        notesDao.inset(note);
                    }
                }).start();
                editTextNote.setText("");
            }
        });

    }

    @Override
    public void onNoteDeleteClickListener(Note note) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.delete(note);
            }
        }).start();
    }

    @Override
    public void onNoteEditClickListener(Note note) {
        Intent editActivityIntent = new Intent(MainActivity.this, EditNoteActivity.class);
        editActivityIntent.putExtra("note", note);
        startActivityForResult(editActivityIntent, EDIT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE) {
            Note note = (Note) data.getSerializableExtra("note");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    notesDao.update(note);
                }
            }).start();

        }
    }
}