package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nz.prompt.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean loggedIn = Boolean.parseBoolean(DatabaseHandler.db.getSetting("UserLoggedIn"));

        if (!loggedIn)
        {
            Intent firstTimeIntent = new Intent(this, FirstTimeActivity.class);
            startActivity(firstTimeIntent);
        }
        else
            setContentView(R.layout.activity_main_menu);
    }
}
