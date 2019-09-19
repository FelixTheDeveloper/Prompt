package nz.prompt.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nz.prompt.R;
import nz.prompt.controllers.AccountController;
import nz.prompt.controllers.UserController;
import nz.prompt.model.AccountModel;
import nz.prompt.model.UserModel;
import nz.prompt.ui.main.MainMenu;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.login_emailTextBox);
        passwordInput = findViewById(R.id.login_passwordTextBox);
        submitButton = findViewById(R.id.login_submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin();
            }
        });
    }

    private void tryLogin()
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (AccountController.CheckAccount(email))
        {
            int id = AccountController.VerifyAccount(email, password);
            if (AccountController.VerifyAccount(email, password) != -1)
            {
                AccountModel account = AccountController.GetAccount(id);
                AccountController.currentAccount = account;
                UserModel user = account.getUser();
                UserController.currentUser = user;

                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Your email or password combination cannot be found! Well that's what other apps says, we say your password is wrong, your account exists. Cheers", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Account does not exist!", Toast.LENGTH_SHORT).show();
        }
    }
}
