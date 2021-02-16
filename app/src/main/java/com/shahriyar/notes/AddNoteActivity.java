package com.shahriyar.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;
    private Button buttonSaveNote;
    private Button buttonUpdateNote;
    private MainViewModel viewModel;
    private List<Note> notes;
    private Note noted;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNote=findViewById(R.id.buttonSaveNote);
        buttonUpdateNote=findViewById(R.id.buttonUpdateNote);
        buttonSaveNote.setVisibility(buttonSaveNote.VISIBLE);


        Intent intent=getIntent();
        if(intent!=null&& intent.hasExtra("id")) {
            id = intent.getIntExtra("id",-1);
            noted=viewModel.getNoteById(id);
            editTextTitle.setText(noted.getTitle());
            editTextDescription.setText(noted.getDescription());
            spinnerDaysOfWeek.setSelection(noted.getDayOfWeek()-1);
            radioGroupPriority.check(noted.getPriority());
            buttonSaveNote.setVisibility(buttonSaveNote.GONE);
            buttonUpdateNote.setVisibility(buttonUpdateNote.VISIBLE);
        }

    }

    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition()+1;
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isFilled(title, description)) {
            Note note = new Note(title, description, dayOfWeek, priority);
            viewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickUpdateNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition()-1;
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isFilled(title, description)) {
            Note note = new Note(id, title, description, dayOfWeek, priority);
            viewModel.updateNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, R.string.updates_note, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }



}
