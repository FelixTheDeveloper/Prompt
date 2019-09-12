//AUTHOR: FELIX NIOCENA
package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TaskSetter extends AppCompatActivity {

    private static final String TAG = "TaskSetter";
    //DATE Start VARIABLES
    private TextView mStartDisplayDate;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    //DATE End Variable
    private TextView mEndDisplayDate;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;

    //TIME VARIABLES
    //Time Start Variables
    private TextView startChooseTime;
    private TimePickerDialog startTimePickerDialog;
    //Time End Variables
    private TextView endChooseTime;
    private TimePickerDialog endTimePickerDialog;

    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;
    //Time End Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

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
                        }
                        else{
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
                        }
                        else{
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















    }

}
