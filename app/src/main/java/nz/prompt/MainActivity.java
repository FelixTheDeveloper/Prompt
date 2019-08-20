package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import nz.prompt.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!DatabaseHandler.loggedIn)
            setContentView(R.layout.login_activity);
        else
            setContentView(R.layout.activity_main);
    }
}
