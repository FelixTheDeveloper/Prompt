package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nz.prompt.database.DatabaseHandler;

import static nz.prompt.Notifications.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);

        boolean loggedIn = Boolean.parseBoolean(DatabaseHandler.db.getSetting("UserLoggedIn"));

        if (!loggedIn)
        {
            Intent firstTimeIntent = new Intent(this, FirstTimeActivity.class);
            startActivity(firstTimeIntent);
        }
        else
            setContentView(R.layout.activity_main_menu);
    }

    public void sendOnChannel1 (View view) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_check_box_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }


}
