//Author: FELIX
package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import nz.prompt.ui.register.RegisterActivity;


public class FirstTimeActivity extends AppCompatActivity {
    private Button button;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        imageButton = (ImageButton) findViewById(R.id.signupButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity2();
            }
        });

        imageButton = (ImageButton) findViewById(R.id.login_button2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginActivity();
            }
        });


    }

    public void loginActivity(){
        Intent intent = new Intent(this, MainMenu.class);      //Creates new intent which will lead into LoginActivity class
        startActivity(intent);
    }

    public void registerActivity2(){
        Intent intent = new Intent(this, RegisterActivity.class);       //Creates new intent which will lead us into RegisterActivity class
        startActivity(intent);
    }
}
