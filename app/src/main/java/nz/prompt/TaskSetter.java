//AUTHOR: FELIX NIOCENA
package nz.prompt;

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
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystemNotFoundException;
import java.util.Calendar;

public class TaskSetter extends AppCompatActivity {

    private static final String TAG = "TaskSetter";
    private EditText editTextTask;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private Button buttonConfirm;
    private Button button;
    private TextView mStartDisplayDate;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private TextView mEndDisplayDate;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private TextView startChooseTime;
    private TimePickerDialog startTimePickerDialog;
    private TextView endChooseTime;
    private TimePickerDialog endTimePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;
    private String taskName;
    private String locationName;
    private String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        editTextTask = findViewById(R.id.taskInput);
        editTextLocation = findViewById(R.id.locationInput);
        editTextDescription = findViewById(R.id.descriptionTextBox);
        buttonConfirm = findViewById(R.id.confirmButton);

        //Turning into strings
        taskName = editTextTask.getText().toString();
        locationName = editTextLocation.getText().toString();
        description = editTextDescription.getText().toString();


        //FOR START TIME
        //For TIME START
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        startChooseTime = findViewById(R.id.startChooseTimeText);
        startChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePickerDialog = new TimePickerDialog(TaskSetter.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay > 12){
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        startChooseTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + amPm);
//                        startChooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, currentHour, currentMinute, false);
                startTimePickerDialog.show();
            }
        });     //End of Time Start

        //FOR DATE START

        mStartDisplayDate = (TextView) findViewById(R.id.startDateViewer);   //STARTDATE
        mStartDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

                DatePickerDialog dialog = new DatePickerDialog(TaskSetter.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mStartDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;     //Doing this because January Starts at 0 and December is 11
                Log.d(TAG, "onDateSet: dd/mm/yy " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                mStartDisplayDate.setText(date);
            }
        };
        //END OF START DATE

        //START OF END
        //FOR DATE END
        mEndDisplayDate = (TextView) findViewById(R.id.endDateViewer);
        mEndDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);  //Using Day of month cause there are many different days to each month

                DatePickerDialog dialog = new DatePickerDialog(TaskSetter.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //For formatting of the date
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;     //Doing this because January Starts at 0 and December is 11
                Log.d(TAG, "onDateSet: dd/mm/yy " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                mEndDisplayDate.setText(date);
            }
        };

        //End of DATE End

        //Start of End Time

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        endChooseTime = findViewById(R.id.endChooseTimeText);
        endChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePickerDialog = new TimePickerDialog(TaskSetter.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay > 12){
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        endChooseTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + amPm);
//                        startChooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, currentHour, currentMinute, false);
                endTimePickerDialog.show();
            }
        });     //End of Time Start

        //Creating a dropdown menu for a spinner
        Spinner mySpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TaskSetter.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
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
                addTask();       //Goes back to main menu
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

    private void saveTextAsFile(String fileName, String taskType, String startDate, String startTime, String endDate, String endTime, String location, String description) {
        fileName = fileName + ".txt";
        PrintWriter outputStream;

        //creates file
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Prompt/", fileName);

        //writes to file

        try {
            outputStream = new PrintWriter(new FileOutputStream(file, true));

            outputStream.println("Task Name: " + taskType + "\nDate: " + startDate + "\nStart Time: " + startTime + "\nEnd Date:" + endDate + "\nEnd Time: " + endTime
                    + "\nLocation: " + location + "\nDescription: " + description + "\n\n");
            outputStream.close();
        } catch (FileNotFoundException e) {
            showToast("File Not Found");
        }
        showToast("Saved Successfully!");
    }

    public void addTask() {

        taskName = editTextTask.getText().toString();
        locationName = editTextLocation.getText().toString();
        description = editTextDescription.getText().toString();
        String startTime = startChooseTime.getText().toString();
        String endTime = endChooseTime.getText().toString();
        String startDate = mStartDisplayDate.getText().toString();
        String endDate = mEndDisplayDate.getText().toString();


        saveTextAsFile("Tasks", taskName, startDate, startTime, endDate, endTime, locationName, description);
        Intent intent = new Intent(this, MainMenu.class);
//            showToast("Details saved to Text Files");       //Shows a popup message
        startActivity(intent);


    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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

}






