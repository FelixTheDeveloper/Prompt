package nz.prompt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

import nz.prompt.R;
import nz.prompt.api.Location;
import nz.prompt.controllers.TaskController;
import nz.prompt.controllers.UserController;
import nz.prompt.model.TaskModel;
import nz.prompt.ui.profile.ProfileActivity;
import nz.prompt.ui.tasks.TaskActivity;
import nz.prompt.ui.tasks.TaskListActivity;

/**
 * @author FELIX NIOCENA
 */
public class MainMenu extends AppCompatActivity {
    private ImageButton addTaskButton, profileButton;
    private CalendarView calendarView;
    private Calendar cacheDate = Calendar.getInstance();

    private HashSet<TaskModel> tasks = new HashSet<>();
    private static int ID = 20000;

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
            if (year == cacheDate.get(Calendar.YEAR) && month == cacheDate.get(Calendar.MONTH) && day == cacheDate.get(Calendar.DAY_OF_MONTH))
            {
                Bundle bundle = new Bundle();
                bundle.putInt("EXTRA_YEAR", year);
                bundle.putInt("EXTRA_MONTH", month);
                bundle.putInt("EXTRA_DAY", day);

                Intent intent = new Intent(this, TaskListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else
            {
                cacheDate = new Calendar.Builder().setDate(year, month, day).build();
            }
        });
    }

    @Override
    protected void onResume() {
        ((LinearLayout) findViewById(R.id.mainMenu_upcomingTable)).removeAllViews();

        tasks.clear();
        TaskController.GetTasks(UserController.currentUser.getID()).forEach(task -> {
            Calendar now = Calendar.getInstance();

            if (task.getEndDate().after(now) && task.getStartDate().getTimeInMillis() - now.getTimeInMillis() < 1000 * 60 * 60 * 24 * 7) {
                tasks.add(task);
            }
        });
        tasks.forEach(this::addRow);

        super.onResume();
    }

    public void openTaskPage(){
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    public void openProfilePage(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void addRow(TaskModel task) {
        LinearLayout taskDetailsRow = (LinearLayout) getLayoutInflater().inflate(R.layout.tmp_linear_layout, null);
        taskDetailsRow.setId(ID++ + task.getID());
        taskDetailsRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        taskDetailsRow.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox status = new CheckBox(taskDetailsRow.getContext());
        status.setId(ID++ + task.getID());
        status.setTextAppearance(R.style.AppTheme);
        status.setChecked(task.isStatus());
        status.setOnClickListener(v -> {
            task.setStatus(status.isChecked());
            TaskController.AddTask(task);
        });
        taskDetailsRow.addView(status);

        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.tmp_linear_layout, null);
        layout.setId(ID++ + task.getID());

        TextView title = new TextView(layout.getContext());
        title.setId(ID++ + task.getID());
        title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        title.setTextAppearance(R.style.AppTheme);
        title.setText(task.getTitle());
        title.setTextSize(18);
        title.setTextColor(0xFF000000);
        layout.addView(title);

        TextView description = new TextView(layout.getContext());
        description.setId(ID++ + task.getID());
        description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        description.setTextAppearance(R.style.AppTheme);
        description.setText(task.getDescription());
        description.setTextSize(16);
        description.setTextColor(0xCC000000);
        layout.addView(description);

        TextView location = new TextView(layout.getContext());
        location.setId(ID++ + task.getID());
        location.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        location.setTextAppearance(R.style.AppTheme);
        location.setText("Location: " + String.format(Locale.getDefault(), "%.2f, %.2f", task.getLocation_LAT(), task.getLocation_LNG()));
        location.setTextSize(14);
        location.setTextColor(0xBB000000);
        layout.addView(location);

        taskDetailsRow.addView(layout);
        ((LinearLayout) findViewById(R.id.mainMenu_upcomingTable)).addView(taskDetailsRow);
    }
}
