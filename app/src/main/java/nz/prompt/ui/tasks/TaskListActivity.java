package nz.prompt.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import nz.prompt.R;
import nz.prompt.controllers.TaskController;
import nz.prompt.controllers.UserController;
import nz.prompt.model.TaskModel;

/**
 * @author Duc Nguyen
 */
public class TaskListActivity extends AppCompatActivity {
    private Button backButton;

    private int year, month, day;

    private Date date = new Date();

    private HashSet<TaskModel> pastTasks = new HashSet<>();
    private HashSet<TaskModel> upcomingTasks = new HashSet<>();
    private HashSet<TaskModel> finishedTasks = new HashSet<>();

    private static int ID = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Bundle bundle = getIntent().getExtras();
        year = bundle.getInt("EXTRA_YEAR");
        month = bundle.getInt("EXTRA_MONTH");
        day = bundle.getInt("EXTRA_DAY");

        LocalTime time = LocalTime.now();

        try {
            date = TaskController.dateFormat.parse(year + "-" + String.format(Locale.getDefault(), "%02d", month) + "-" + day + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        } catch (ParseException e) {
            Log.d("TaskListActivity", e.getMessage());
            Toast.makeText(this, "ERROR: Cannot read date. Please report to devs.", Toast.LENGTH_LONG).show();
        }

        backButton = findViewById(R.id.taskList_backButton);
        backButton.setOnClickListener(v -> finish());

        TaskController.GetTasks(UserController.currentUser.getID(), date);

        if (TaskController.tasks.isEmpty()) {
            Toast.makeText(this, "You don't have any tasks :D", Toast.LENGTH_SHORT).show();
        } else {
            TaskController.tasks.forEach(this::ProcessTask);
            DrawTasks();
        }
    }

    private void DrawTasks() {
        pastTasks.forEach(task -> addRow(task, findViewById(R.id.taskList_tablePast)));
        upcomingTasks.forEach(task -> addRow(task, findViewById(R.id.taskList_tableUpcoming)));
        finishedTasks.forEach(task -> addRow(task, findViewById(R.id.taskList_tableFinished)));
    }

    private void ProcessTask(TaskModel task) {
        if (task.isStatus()) {
            finishedTasks.add(task);
            return;
        }

        if (task.getEndDate().before(new Date())) {
            pastTasks.add(task);
            return;
        }

        upcomingTasks.add(task);
    }

    private void addRow(TaskModel task, LinearLayout tableLayout) {
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
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
        taskDetailsRow.addView(status);

        LinearLayout layout = (LinearLayout)getLayoutInflater().inflate(R.layout.tmp_linear_layout, null);
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

        Button deleteButton = new Button(layout.getContext());
        deleteButton.setId(ID++ + task.getID());
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        deleteButton.setText(getString(R.string.delete_button_text));
        deleteButton.setOnClickListener(v -> {
            if (TaskController.RemoveTask(task.getID())) {
                Toast.makeText(v.getContext(), "Delete task successfully!", Toast.LENGTH_SHORT).show();
                TaskController.GetTasks(UserController.currentUser.getID(), date);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            } else {
                Toast.makeText(v.getContext(), "Cannot remove task! Please report to the devs", Toast.LENGTH_LONG).show();
            }
        });
        layout.addView(deleteButton);

        taskDetailsRow.addView(layout);
        tableLayout.addView(taskDetailsRow);
    }
}
