package nz.prompt.ui.tasks;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

import nz.prompt.R;
import nz.prompt.controllers.TaskController;
import nz.prompt.maps.MapsActivity;

/**
 *
 * @author FELIX NIOCENA
 */
public class TaskActivity extends AppCompatActivity {
    private static final String TAG = "TaskActivity";
    private EditText editTextTask;
    private EditText editTextDescription;
    private Button createButton;
    private Button cancelButton;
    private TextView mStartDisplayDate;
    private TextView mEndDisplayDate;
    private TextView startChooseTime;
    private TextView endChooseTime;
    private TextView locationTextBox;
    private Calendar calendar;
    private int currentYear, currentMonth, currentDay, currentHour, currentMinute;
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

    private double chosenLat = 0;
    private double chosenLng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        editTextTask = findViewById(R.id.task_taskTitleTextView);
        editTextDescription = findViewById(R.id.task_descriptionTextBox);
        createButton = findViewById(R.id.task_createButton);
        cancelButton = findViewById(R.id.task_cancelButton);
        locationTextBox = findViewById(R.id.task_locationTextBox);

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        startDate_Day = currentDay;
        startDate_Month = currentMonth;
        startDate_Year = currentYear;
        startDate_Hour = currentHour;
        startDate_Min = currentMinute;

        endDate_Day = currentDay;
        endDate_Month = currentMonth;
        endDate_Year = currentYear;
        endDate_Hour = currentHour + 1;
        endDate_Min = currentMinute;

        //FOR START TIME

        //FOR DATE START

        mStartDisplayDate = findViewById(R.id.task_startDateViewer);
        mStartDisplayDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", currentDay, currentMonth, currentYear));

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

        startChooseTime = findViewById(R.id.task_startChooseTimeText);
        startChooseTime.setText(String.format(Locale.getDefault(), "%02d:%02d:00", currentHour, currentMinute));

        startChooseTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(this, (timePicker, hourOfDay, minutes) -> {
                startDate_Hour = hourOfDay;
                startDate_Min = minutes;
                startChooseTime.setText(String.format(Locale.getDefault(), "%02d:%02d:00", hourOfDay, minutes));

            }, currentHour, currentMinute, false);
            dialog.show();
        });
        //End of Time Start

        //START OF END
        //FOR DATE END
        mEndDisplayDate = findViewById(R.id.task_endDateViewer);
        mEndDisplayDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", currentDay, currentMonth, currentYear));

        mEndDisplayDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

        endChooseTime = findViewById(R.id.task_endChooseTimeText);
        endChooseTime.setText(String.format(Locale.getDefault(), "%02d:%02d:00", currentHour + 1, currentMinute));
        endChooseTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(TaskActivity.this, (timePicker, hourOfDay, minutes) -> {
                endDate_Hour = hourOfDay;
                endDate_Min = minutes;
                endChooseTime.setText(String.format(Locale.getDefault(), "%02d:%02d:00", hourOfDay, minutes));

            }, currentHour, currentMinute, false);
            dialog.show();
        });     //End of Time Start

        cancelButton.setOnClickListener(v -> finish());

        //Asking for Permission for access of files
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        //Location button
        Button locationButton = findViewById(R.id.task_locationButton);
        locationButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivityForResult(intent, 0);
        });

        //Create button
        createButton.setOnClickListener(v -> {
            String taskName = editTextTask.getText().toString();
            String description = editTextDescription.getText().toString();
            String startDate = String.format(Locale.getDefault(), "%04d-%02d-%02d %02d:%02d:00", startDate_Year, startDate_Month, startDate_Day, startDate_Hour, startDate_Min);
            String endDate = String.format(Locale.getDefault(), "%04d-%02d-%02d %02d:%02d:00", endDate_Year, endDate_Month, endDate_Day, endDate_Hour, endDate_Min);

            if (TaskController.CreateTask(taskName, description, chosenLat, chosenLng, startDate, endDate))
            {
                Toast.makeText(this, "Task created!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "Task create failed!", Toast.LENGTH_SHORT).show();
            }
        });

        //Checking realtime whether the user has input anything
        editTextTask.addTextChangedListener(addTaskTextWatcher);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Retrieve data in the intent
        if (requestCode == 0)
        {
            chosenLat = intent.getDoubleExtra("MAPS_EXTRA_SAVE_LAT", 0);
            chosenLng = intent.getDoubleExtra("MAPS_EXTRA_SAVE_LNG", 0);

            if (chosenLat != 0 && chosenLng != 0)
            {
                locationTextBox.setText(String.format(Locale.getDefault(), "%.2f, %.2f", chosenLat, chosenLng));
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
            String startDateInput = mStartDisplayDate.getText().toString();
            String startTimeInput = startChooseTime.getText().toString();
            String endDateInput = mEndDisplayDate.getText().toString();
            String endTimeInput = endChooseTime.getText().toString();

            createButton.setEnabled(!taskInput.isEmpty() && !startDateInput.isEmpty() && !startTimeInput.isEmpty() && !endDateInput.isEmpty() && !endTimeInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}






