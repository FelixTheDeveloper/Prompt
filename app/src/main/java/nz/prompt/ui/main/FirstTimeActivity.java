package nz.prompt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import nz.prompt.R;
import nz.prompt.ui.login.LoginActivity;
import nz.prompt.ui.register.RegisterActivity;

/**
@author FELIX
 */
public class FirstTimeActivity extends AppCompatActivity {
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        imageButton = findViewById(R.id.firstTime_signupButton);
        imageButton.setOnClickListener(view -> registerActivity());

        imageButton = findViewById(R.id.firstTime_loginButton);
        imageButton.setOnClickListener(view -> loginActivity());
    }

    public void loginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);      //Creates new intent which will lead into LoginActivity class
        startActivity(intent);
    }

    public void registerActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);       //Creates new intent which will lead us into RegisterActivity class
        startActivity(intent);
    }
}
