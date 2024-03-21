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
        final Button btnEdit = findViewById(R.id.btn_edit);


        if (getIntent().hasExtra("taskId")) {
            int taskId = getIntent().getIntExtra("taskId", -1);
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

                // Validate that none of the fields are empty
                if (title.isEmpty()) {
                    Toast.makeText(CrudActivity.this, "Please enter a title.", Toast.LENGTH_SHORT).show();
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

//                // Validate the due date format
//                if (!isValidDate(dueDate)) {
//                    Toast.makeText(CrudActivity.this, "Please enter a valid date in the format dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // If validation passes, insert the task into the database
                DBHelper dbHelper = new DBHelper(CrudActivity.this);
                dbHelper.insertTask(title, description, dueDate);
                Toast.makeText(CrudActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();
                finish(); // Close activity and return to MainActivity
            }

            private boolean isValidDate(String dueDate) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                sdf.setLenient(false); // Don't automatically correct dates
                try {
                    String dateString = new String();
                    sdf.parse(dateString); // Try to parse the string
                    return true; // If successful, return true
                } catch (ParseException e) {
                    return false; // If parsing fails, return false
                }
            }
        });

    }
}
