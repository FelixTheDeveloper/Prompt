package nz.prompt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import nz.prompt.api.LocationRestaurants;
import nz.prompt.R;
import nz.prompt.ui.profile.ProfileActivity;
import nz.prompt.ui.tasks.TaskActivity;
import nz.prompt.ui.tasks.TaskListActivity;

/**
 * @author FELIX NIOCENA
 */
public class MainMenu extends AppCompatActivity {
    private ImageButton addTaskButton, profileButton;
    private Button foodButton;
    private CalendarView calendarView;
    private LocalDate cacheDate = LocalDate.now();
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addTaskButton = findViewById(R.id.mainMenu_addTaskButton);
        addTaskButton.setOnClickListener(v -> openTaskPage());

        foodButton = findViewById(R.id.foodSuggest);
        foodButton.setOnClickListener(view -> openZomato());


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

    public void openZomato() {
        Intent intent = new Intent(this, LocationRestaurants.class);
        startActivity(intent);
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
