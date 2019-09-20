package nz.prompt.ui.main;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import nz.prompt.R;
import nz.prompt.ui.tasks.TaskActivity;
import nz.prompt.ui.tasks.TaskListActivity;
import nz.prompt.ui.profile.ProfileActivity;

/**
 * @author FELIX NIOCENA
 */
public class MainMenu extends AppCompatActivity {
    private ImageButton addTaskButton, profileButton, upcomingTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addTaskButton = findViewById(R.id.mainMenu_addTaskButton);
        addTaskButton.setOnClickListener(view -> openTaskPage());

        profileButton = findViewById(R.id.mainMenu_profileButton);
        profileButton.setOnClickListener(view -> openProfilePage());

        upcomingTaskButton = findViewById(R.id.mainMenu_upcomingTaskButton);
        upcomingTaskButton.setOnClickListener(v -> openUpcomingTasks());
    }

    public void openUpcomingTasks() {
        Intent intent = new Intent(this, TaskListActivity.class);
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
