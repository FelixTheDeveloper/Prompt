package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import nz.prompt.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHandler.EstablishDB(this);

        boolean loggedIn = Boolean.parseBoolean(DatabaseHandler.dbHelper.getSetting("UserLoggedIn"));

        if (!loggedIn)
        {
            Intent firstTimeIntent = new Intent(this, FirstTimeActivity.class);
            startActivity(firstTimeIntent);
        }
        else
            setContentView(R.layout.activity_main_menu);
    }
}
