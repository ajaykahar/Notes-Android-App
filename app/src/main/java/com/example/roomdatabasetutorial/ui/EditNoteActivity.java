package com.example.roomdatabasetutorial.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabasetutorial.R;
import com.example.roomdatabasetutorial.model.Note;

public class EditNoteActivity extends AppCompatActivity {

    EditText editTextEditNote;
    Button buttonEditedNoteAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        editTextEditNote = findViewById(R.id.editTextEditNote);
        buttonEditedNoteAdd = findViewById(R.id.buttonEditedAdd);

        Note note = (Note) getIntent().getSerializableExtra("note");
        editTextEditNote.setText(note.getNoteText());
        buttonEditedNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note.getNoteText().equals(editTextEditNote.getText().toString())){
                    Toast.makeText(EditNoteActivity.this, "please edit text.",Toast.LENGTH_SHORT).show();
                }else{
                    note.setNoteText(editTextEditNote.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("note",note);
                    setResult(1,intent);
                    finish();
                }

            }
        });
    }
}