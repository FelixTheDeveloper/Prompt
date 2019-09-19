//AUTHOR: FELIX NIOCENA
package nz.prompt.ui.main;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import nz.prompt.R;
import nz.prompt.ui.tasks.TaskSetterActivity;
import nz.prompt.ui.tasks.UpcomingTasks;
import nz.prompt.ui.profile.ProfileActivity;

public class MainMenu extends AppCompatActivity {
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //For add task buttongir
        imageButton = findViewById(R.id.mainMenu_confirmButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskPage();
            }
        });
        //For ProfileActivity
        imageButton = findViewById(R.id.mainMenu_profileButton);
        imageButton.setOnClickListener(new View.OnClickListener() {     //When user clicks onto profile image
            @Override
            public void onClick(View view) {
                openProfilePage();  //Calls profilepage method
            }
        });
        //For Upcoming Tasks
        imageButton = findViewById(R.id.mainMenu_upcomingTaskButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpcomingTasks();
            }
        });
    }

    public void openUpcomingTasks() {
        Intent intent = new Intent(this, UpcomingTasks.class);
        startActivity(intent);
    }

    public void openTaskPage(){
        Intent intent = new Intent(this, TaskSetterActivity.class);
        startActivity(intent);
    }

    public void openProfilePage(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
