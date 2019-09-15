//AUTHOR: FELIX NIOCENA
package nz.prompt;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //For add task button
        imageButton = (ImageButton) findViewById(R.id.confirmButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskPage();
            }
        });
        //For Profile
        imageButton = (ImageButton) findViewById(R.id.profileButton);
        imageButton.setOnClickListener(new View.OnClickListener() {     //When user clicks onto profile image
            @Override
            public void onClick(View view) {
                openProfilePage();  //Calls profilepage method
            }
        });
        //For Upcoming Tasks
        imageButton = (ImageButton) findViewById(R.id.upcomingTaskButton);
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
        Intent intent = new Intent(this, TaskSetter.class);
        startActivity(intent);
    }

    public void openProfilePage(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
