//AUTHOR: FELIX NIOCENA
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

import nz.prompt.R;
import nz.prompt.controllers.TaskController;
import nz.prompt.ui.main.MainMenu;

public class TaskSetterActivity extends AppCompatActivity {


    private static final String TAG = "TaskSetterActivity";
    private EditText editTextTask;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private Button buttonConfirm;
    private Button button;
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

        editTextTask = findViewById(R.id.taskInput);
        editTextLocation = findViewById(R.id.locationInput);
        editTextDescription = findViewById(R.id.descriptionTextBox);
        buttonConfirm = findViewById(R.id.confirmButton);

        //FOR START TIME

        //FOR DATE START

        mStartDisplayDate = findViewById(R.id.startDateViewer);
        mStartDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

                DatePickerDialog dialog = new DatePickerDialog(TaskSetterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;     //Doing this because January Starts at 0 and December is 11
                        Log.d(TAG, "onDateSet: dd/mm/yy " + day + "/" + month + "/" + year);
                        String date = day + "/" + month + "/" + year;

                        startDate_Day = day;
                        startDate_Month = month;
                        startDate_Year = year;

                        mStartDisplayDate.setText(date);
                    }
                }, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        //END OF START DATE

        //For TIME START
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        startChooseTime = findViewById(R.id.startChooseTimeText);
        startChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSetterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
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
                    }
                }, currentHour, currentMinute, false);
                dialog.show();
            }
        });
        //End of Time Start

        //START OF END
        //FOR DATE END
        mEndDisplayDate = findViewById(R.id.endDateViewer);
        mEndDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

                DatePickerDialog dialog = new DatePickerDialog(TaskSetterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month += 1;     //Doing this because January Starts at 0 and December is 11
                                Log.d(TAG, "onDateSet: dd/mm/yy " + day + "/" + month + "/" + year);
                                String date = day + "/" + month + "/" + year;

                                endDate_Day = day;
                                endDate_Month = month;
                                endDate_Year = year;

                                mEndDisplayDate.setText(date);
                            }
                        }, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //End of DATE End

        //Start of End Time

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        endChooseTime = findViewById(R.id.endChooseTimeText);
        endChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSetterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
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
                    }
                }, currentHour, currentMinute, false);
                dialog.show();
            }
        });     //End of Time Start

        //Creating a dropdown menu for a spinner
        Spinner mySpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TaskSetterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);        //Starting the spinner

        button = findViewById(R.id.cancelButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTask();
            }
        });

        //Asking for Permission for access of files
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        button = findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTask.getText().toString();
                String locationName = editTextLocation.getText().toString();
                String description = editTextDescription.getText().toString();
                String startDate = String.format("%d-%d-%d %d:%d:00", startDate_Year, startDate_Month, startDate_Day, startDate_Hour, startDate_Min);
                String endDate = String.format("%d-%d-%d %d:%d:00", endDate_Year, endDate_Month, endDate_Day, endDate_Hour, endDate_Min);

                if (TaskController.CreateTask(taskName, locationName, description, startDate, endDate))
                {
                    Toast.makeText(TaskSetterActivity.this, "Task created!", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
                else
                {
                    Toast.makeText(TaskSetterActivity.this, "Task create failed!", Toast.LENGTH_SHORT).show();
                }
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

    public void cancelTask(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    TextWatcher addTaskTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String taskInput = editTextTask.getText().toString();
            String locationInput = editTextLocation.getText().toString();

            buttonConfirm.setEnabled(!taskInput.isEmpty() && !locationInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void updateUI()
    {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}






