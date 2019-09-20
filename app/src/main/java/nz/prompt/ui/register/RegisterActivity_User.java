package nz.prompt.ui.register;

import android.Manifest;
import android.accounts.Account;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import nz.prompt.controllers.AccountController;
import nz.prompt.controllers.UserController;
import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.AccountModel;
import nz.prompt.model.UserModel;
import nz.prompt.ui.main.MainMenu;
import nz.prompt.R;

/**
 @author Felix Niocena
 */
public class RegisterActivity_User extends AppCompatActivity {
    private String email;
    private String password;

    private Button buttonConfirm;
    private EditText editTextFName;
    private EditText editTextLName;
    private EditText ageTextBox;
    private EditText budgetTextBox;
    private CheckBox termsCheckBox;
    private String fNameInput;
    private String lNameInput;
    private int ageInput;
    private int budgetInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("EXTRA_EMAIL");
            password = extras.getString("EXTRA_PASSWORD");
        }

        editTextFName = findViewById(R.id.registerUser_firstNameTextBox);
        editTextLName = findViewById(R.id.registerUser_lastNameTextBox);
        ageTextBox = findViewById(R.id.registerUser_ageTextBox);
        budgetTextBox = findViewById(R.id.registerUser_budgetTextBox);

        termsCheckBox = findViewById(R.id.registerUser_termsCheckBox);

        //Asking for Permission for access of files
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        buttonConfirm = findViewById(R.id.registerUser_submitButton);
        buttonConfirm.setOnClickListener(v -> createUser());

        //For Checking whether the user has input anything.
        editTextFName.addTextChangedListener(addTextWatcher);
        editTextLName.addTextChangedListener(addTextWatcher);
        ageTextBox.addTextChangedListener(addTextWatcher);
        budgetTextBox.addTextChangedListener(addTextWatcher);
    }

    public void createUser()
    {
        if (termsCheckBox.isChecked()) {
            fNameInput = editTextFName.getText().toString();
            lNameInput = editTextLName.getText().toString();
            ageInput = Integer.parseInt(ageTextBox.getText().toString());
            budgetInput = Integer.parseInt(budgetTextBox.getText().toString());

            UserModel user = UserController.CreateUser(fNameInput, lNameInput, ageInput, budgetInput);

            AccountModel account = AccountController.CreateAccount(email, password, user);

            if (account != null) {
                DatabaseHandler.dbHelper.setSetting("UserLoggedInID", String.valueOf(account.getAccountID()));
                DatabaseHandler.dbHelper.setSetting("UserLoggedIn", "TRUE");

                AccountController.currentAccount = account;
                UserController.currentUser = user;

                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Cannot create account, please check if you have filled in all boxes!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "So you want Carlo to buy pizzas then? Well. Sweet as.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }

    TextWatcher addTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            fNameInput = editTextFName.getText().toString();
            lNameInput = editTextFName.getText().toString();
            String ageInput = ageTextBox.getText().toString();

            buttonConfirm.setEnabled((!fNameInput.isEmpty() && !lNameInput.isEmpty()) && !(ageInput).isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*public void saveToFile(String fileName, String fName, String lName, String ageTextBox) {
        fileName = fileName + ".txt";
        PrintWriter outputStream;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        try {
            outputStream = new PrintWriter(new FileOutputStream(file, true));
            outputStream.println("First Name: " + fName + "     Last Name:" + lName + "     Age: " + ageTextBox);

            outputStream.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
    }*/
}