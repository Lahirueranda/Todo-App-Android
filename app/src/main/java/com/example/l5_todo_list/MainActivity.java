package com.example.l5_todo_list;

//labrary impoets

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mysqllit;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    //user input fields
    private EditText user_name,task_name,task_date,task_status,Task_Id;

    //buttons
    private Button Select_Date,Insert_task, View_task, update_task, Delete_task;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("To Do List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mysqllit = new DatabaseHelper(this);

        //input field
        user_name = findViewById(R.id.user_name);
        task_name = findViewById(R.id.task_name);
        task_date = findViewById(R.id.task_date);
        task_status= findViewById(R.id.task_status);
        Task_Id = findViewById(R.id.Task_Id);

        //buttons
        Select_Date = findViewById(R.id.Select_Date);
        Insert_task = findViewById(R.id.Insert_task);
        View_task= findViewById(R.id.View_task);
        update_task = findViewById(R.id.update_task);
        Delete_task = findViewById(R.id.Delete_task);

        calendar = Calendar.getInstance();

        //get and set date values
        Select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        task_date.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);

                //This will show calender for pickup date
                datePickerDialog.show();
            }
        });


        //value insert section

        Insert_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsert = mysqllit.insetData(

                        user_name.getText().toString(),
                        task_name.getText().toString(),
                        task_date.getText().toString(),
                        task_status.getText().toString());

                if (isInsert == true) {
                    Toast.makeText(MainActivity.this, "Task Insert Successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Tasks is not Insert. Please try aging!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //delete task section

        Delete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer isInsert = mysqllit.deletData(Task_Id .getText().toString());
                if (isInsert > 0) {
                    Toast.makeText(MainActivity.this, " Tasks  Delete Successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, " Tasks is not Deleted.. Please try aging...",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //update task section
        update_task .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsert = mysqllit.updatedata(Task_Id.getText().toString(),

                        user_name.getText().toString(),
                        task_name.getText().toString(),
                        task_date.getText().toString(),
                        task_status.getText().toString());

                if (isInsert == true) {
                    Toast.makeText(MainActivity.this, "Successfully Update tasks",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Update is not Successfully..please try again",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        //display details in popup window

        View_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = mysqllit.getall();
                if (result.getCount() == 0) {
                    showbox("No Tasks", "No Tasks Added yet");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append(
                            "ID:-" + result.getString(0) +
                            "\nUser Name:- " + result.getString(1) +
                            "\nUser Tasks:- " + result.getString(2) +
                            "\nTask Date:- " + result.getString(3)+
                            "\nTask Status:- " + result.getString(4) +
                            "\n\n\n");
                }
                showbox("User's All Tasks", buffer.toString());
            }
        });
    }

    public void showbox(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

