package com.example.a41p;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class CrudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_crud_activity);
        final EditText editTextTitle = findViewById(R.id.editTextTaskTitle);
        final EditText editTextDescription = findViewById(R.id.editTextTaskDescription);
        final EditText editTextDueDate = findViewById(R.id.editTextTaskDueDate);
        final Button buttonSave = findViewById(R.id.buttonSaveTask);

        // Initialize isEditMode and taskId at the beginning of onCreate
        boolean isEditMode = getIntent().getBooleanExtra("editMode", false);
        int taskId = getIntent().getIntExtra("taskId", -1);  // Declare taskId here

        DBHelper dbHelper = new DBHelper(this);


        if (getIntent().hasExtra("taskId")) {
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String dueDate = getIntent().getStringExtra("dueDate");

            editTextTitle.setText(title);
            editTextDescription.setText(description);
            editTextDueDate.setText(dueDate);

        }


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String dueDate = editTextDueDate.getText().toString().trim();

                // Basic validation checks
                if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                    Toast.makeText(CrudActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.isEmpty()) {
                    Toast.makeText(CrudActivity.this, "Please enter a description.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dueDate.isEmpty()) {
                    Toast.makeText(CrudActivity.this, "Please enter a due date.", Toast.LENGTH_SHORT).show();
                    return;
                }


                DBHelper dbHelper = new DBHelper(CrudActivity.this);

                // Check if we are in edit mode
                if (isEditMode && taskId != -1) {
                    // Update the existing task
                    dbHelper.updateTask(new Task(taskId, title, description, dueDate));
                    Toast.makeText(CrudActivity.this, "Task updated successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert a new task
                    dbHelper.insertTask(title, description, dueDate);
                    Toast.makeText(CrudActivity.this, "Task added successfully.", Toast.LENGTH_SHORT).show();
                }
                finish(); // Return to MainActivity
            }
        });







    }
}
