package nz.prompt.ui.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nz.prompt.R;

public class UpcomingTasks extends AppCompatActivity {

    private TextView dateTimeDisplay;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private String date;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_tasks);

        dateTimeDisplay = (TextView) findViewById(R.id.currentDate);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("EE d MMM ");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        imageButton = (ImageButton) findViewById(R.id.addTaskButton2);


    }
}
