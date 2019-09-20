package nz.prompt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nz.prompt.R;
import nz.prompt.ui.profile.ProfileActivity;
import nz.prompt.ui.tasks.TaskActivity;
import nz.prompt.ui.tasks.TaskListActivity;

/**
 * @author FELIX NIOCENA
 */
public class MainMenu extends AppCompatActivity {
    private ImageButton addTaskButton, profileButton;
    private CalendarView calendarView;
    private LocalDate cacheDate = LocalDate.now();
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addTaskButton = findViewById(R.id.mainMenu_addTaskButton);
        addTaskButton.setOnClickListener(v -> openTaskPage());

        profileButton = findViewById(R.id.mainMenu_profileButton);
        profileButton.setOnClickListener(v -> openProfilePage());

        calendarView = findViewById(R.id.mainMenu_calendarView);
        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            if (year == cacheDate.getYear() && month + 1 == cacheDate.getMonthValue() && day == cacheDate.getDayOfMonth())
            {
                Bundle bundle = new Bundle();
                bundle.putInt("EXTRA_YEAR", year);
                bundle.putInt("EXTRA_MONTH", month + 1);
                bundle.putInt("EXTRA_DAY", day);

                Intent intent = new Intent(this, TaskListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else
            {
                cacheDate = LocalDate.parse(year + "-" + (month + 1) + "-" + day, formatter);
            }
        });
    }

    public void openTaskPage(){
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    public void openProfilePage(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
