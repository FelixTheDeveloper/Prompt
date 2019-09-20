package nz.prompt.ui.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.resources.TextAppearance;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

import nz.prompt.R;
import nz.prompt.controllers.TaskController;
import nz.prompt.controllers.UserController;
import nz.prompt.model.TaskModel;

public class TaskListActivity extends AppCompatActivity {
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_tasks);

        table = findViewById(R.id.taskList_tableLayout);

        TaskController.GetTasks(UserController.currentUser.getID());

        TaskController.tasks.forEach(this::addRow);
    }

    private void addRow(TaskModel task)
    {
        TableRow taskDetailsRow = new TableRow(this);
        taskDetailsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        CheckBox status = new CheckBox(this);
        status.setChecked(task.isStatus());
        status.setOnClickListener(v -> {
            task.setStatus(status.isChecked());
            TaskController.AddTask(task);
        });
        taskDetailsRow.addView(status);

        LinearLayout layout = (LinearLayout)getLayoutInflater().inflate(R.layout.tmp_linear_layout, null);

        TextView title = new TextView(layout.getContext());
        title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        title.setTextAppearance(R.style.AppTheme);
        title.setText(task.getTitle());
        title.setTextSize(18);
        title.setTextColor(0xFF000000);
        layout.addView(title);

        TextView description = new TextView(layout.getContext());
        title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        title.setTextAppearance(R.style.AppTheme);
        description.setText(task.getDescription());
        description.setTextSize(18);
        title.setTextColor(0xEE000000);
        layout.addView(description);

        Button deleteButton = new Button(layout.getContext());
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        deleteButton.setText(getString(R.string.delete_button_text));
        deleteButton.setOnClickListener(v -> {
            if (TaskController.RemoveTask(task.getID()))
            {
                Toast.makeText(v.getContext(), "Delete task successfully!", Toast.LENGTH_SHORT).show();
                TaskController.GetTasks(UserController.currentUser.getID());
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            else
            {
                Toast.makeText(v.getContext(), "Cannot remove task! Please report to the devs", Toast.LENGTH_LONG).show();
            }
        });
        layout.addView(deleteButton);

        taskDetailsRow.addView(layout);
        table.addView(taskDetailsRow);
    }
}
