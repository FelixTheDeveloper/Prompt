package nz.prompt.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nz.prompt.R;
import nz.prompt.controllers.AccountController;

/**
 * @author Duc Nguyen
 */
public class RegisterActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText emailTextBox;
    private EditText passwordTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailTextBox = findViewById(R.id.register_emailTextBox);
        passwordTextBox = findViewById(R.id.register_passwordTextBox);

        submitButton = findViewById(R.id.register_submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAccount();
            }
        });
    }

    private void checkAccount()
    {
        if (emailTextBox.getText().toString().isEmpty() || passwordTextBox.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in your email and password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (passwordTextBox.getText().length() < 8)
            {
                Toast.makeText(this, "Password cannot be less than 8 characters!", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!AccountController.CheckAccount(emailTextBox.getText().toString())) {
                    Intent intent = new Intent(this, RegisterActivity_User.class);
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_EMAIL", emailTextBox.getText().toString());
                    extras.putString("EXTRA_PASSWORD", passwordTextBox.getText().toString());
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "An account already exists with this email!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
