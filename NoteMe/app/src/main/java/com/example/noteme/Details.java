package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.noteme.databinding.ActivityDetailsBinding;

public class Details extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetailsBinding binding;
    TextView mDetails;
    NoteDatabase db;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDetails = findViewById(R.id.textview_first);
        setSupportActionBar(binding.toolbar);
        Intent i = getIntent();
        Long id = i.getLongExtra("ID",0);
//        Toast.makeText(this, "ID --> " + id, Toast.LENGTH_SHORT).show();
        db = new NoteDatabase(this);
        note= db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        mDetails.setText(note.getContent());
        Toast.makeText(this, "Title -->" + note.getTitle(), Toast.LENGTH_SHORT).show();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note.getID());
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editNote) {
            Toast.makeText(this, "Edit is clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,Edit.class);
            i.putExtra("ID",note.getID());
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}