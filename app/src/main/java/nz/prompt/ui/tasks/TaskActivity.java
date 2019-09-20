package nz.prompt.ui.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import nz.prompt.R;
import nz.prompt.controllers.TaskController;
import nz.prompt.ui.main.MainMenu;

/**
 *
 * @author FELIX NIOCENA
 */
public class TaskActivity extends AppCompatActivity {
    private static final String TAG = "TaskActivity";
    private EditText editTextTask;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private Button createButton;
    private Button cancelButton;
    private TextView mStartDisplayDate;
    private TextView mEndDisplayDate;
    private TextView startChooseTime;
    private TextView endChooseTime;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;

    private int startDate_Day = -1;
    private int startDate_Month = -1;
    private int startDate_Year = -1;
    private int startDate_Hour = -1;
    private int startDate_Min = -1;
    private int endDate_Day = -1;
    private int endDate_Month = -1;
    private int endDate_Year = -1;
    private int endDate_Hour = -1;
    private int endDate_Min = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        editTextTask = findViewById(R.id.task_taskTitleTextView);
        editTextLocation = findViewById(R.id.task_locationTextBox);
        editTextDescription = findViewById(R.id.task_descriptionTextBox);
        createButton = findViewById(R.id.task_createButton);
        cancelButton = findViewById(R.id.task_cancelButton);

        //FOR START TIME

        //FOR DATE START

        mStartDisplayDate = findViewById(R.id.task_startDateViewer);
        mStartDisplayDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

            DatePickerDialog dialog = new DatePickerDialog(TaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (datePicker, year12, month12, day12) -> {
                month12 += 1;     //Doing this because January Starts at 0 and December is 11
                Log.d(TAG, "onDateSet: dd/mm/yy " + day12 + "/" + month12 + "/" + year12);
                String date = day12 + "/" + month12 + "/" + year12;

                startDate_Day = day12;
                startDate_Month = month12;
                startDate_Year = year12;

                mStartDisplayDate.setText(date);
            }, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
        //END OF START DATE

        //For TIME START
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        startChooseTime = findViewById(R.id.task_startChooseTimeText);
        startChooseTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(TaskActivity.this, (timePicker, hourOfDay, minutes) -> {
                startDate_Hour = hourOfDay;
                startDate_Min = minutes;

                if (hourOfDay > 12){
                    hourOfDay -= 12;
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                startChooseTime.setText(String.format("%02d:%02d:00", hourOfDay, minutes) + amPm);
//                        startChooseTime.setText(hourOfDay + ":" + minutes);
            }, currentHour, currentMinute, false);
            dialog.show();
        });
        //End of Time Start

        //START OF END
        //FOR DATE END
        mEndDisplayDate = findViewById(R.id.task_endDateViewer);
        mEndDisplayDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

            DatePickerDialog dialog = new DatePickerDialog(TaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (datePicker, year1, month1, day1) -> {
                        month1 += 1;     //Doing this because January Starts at 0 and December is 11
                        Log.d(TAG, "onDateSet: dd/mm/yy " + day1 + "/" + month1 + "/" + year1);
                        String date = day1 + "/" + month1 + "/" + year1;

                        endDate_Day = day1;
                        endDate_Month = month1;
                        endDate_Year = year1;

                        mEndDisplayDate.setText(date);
                    }, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        //End of DATE End

        //Start of End Time

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        endChooseTime = findViewById(R.id.task_endChooseTimeText);
        endChooseTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(TaskActivity.this, (timePicker, hourOfDay, minutes) -> {
                endDate_Hour = hourOfDay;
                endDate_Min = minutes;

                if (hourOfDay > 12)
                {
                    hourOfDay -= 12;
                    amPm = "PM";
                }
                else
                {
                    amPm = "AM";
                }
                endChooseTime.setText(String.format("%02d:%02d:00", hourOfDay, minutes) + amPm);
//                        startChooseTime.setText(hourOfDay + ":" + minutes);
            }, currentHour, currentMinute, false);
            dialog.show();
        });     //End of Time Start

        //Creating a dropdown menu for a spinner
        Spinner mySpinner = findViewById(R.id.task_repeatOptions);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);        //Starting the spinner

        cancelButton.setOnClickListener(v -> finish());

        //Asking for Permission for access of files
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        createButton.setOnClickListener(v -> {
            String taskName = editTextTask.getText().toString();
            String locationName = editTextLocation.getText().toString();
            String description = editTextDescription.getText().toString();
            String startDate = String.format(Locale.getDefault(), "%d-%02d-%02d %d:%d:00", startDate_Year, startDate_Month, startDate_Day, startDate_Hour, startDate_Min);
            String endDate = String.format(Locale.getDefault(), "%d-%02d-%02d %d:%d:00", endDate_Year, endDate_Month, endDate_Day, endDate_Hour, endDate_Min);

            if (TaskController.CreateTask(taskName, locationName, description, startDate, endDate))
            {
                Toast.makeText(TaskActivity.this, "Task created!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(TaskActivity.this, "Task create failed!", Toast.LENGTH_SHORT).show();
            }
        });

        //Checking realtime whether the user has input anything
        editTextTask.addTextChangedListener(addTaskTextWatcher);
        editTextLocation.addTextChangedListener(addTaskTextWatcher);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }

    TextWatcher addTaskTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String taskInput = editTextTask.getText().toString();
            String locationInput = editTextLocation.getText().toString();

            createButton.setEnabled(!taskInput.isEmpty() && !locationInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}






