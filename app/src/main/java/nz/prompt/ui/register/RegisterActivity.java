//Author Felix Niocena
package nz.prompt.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import nz.prompt.MainMenu;
import nz.prompt.R;
import nz.prompt.TaskSetterActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonConfirm;
    private EditText editTextFName;
    private EditText editTextLName;
    private EditText age;
    private TaskSetterActivity taskSetter;
    private String fNameInput;
    private String lNameInput;
    private String ageInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextFName = findViewById(R.id.firstNameTextBox);
        editTextLName = findViewById(R.id.lastNameTextBox);
        age = findViewById(R.id.ageTextBox);

        //Asking for Permission for access of files
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        buttonConfirm = findViewById(R.id.createAccButton);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdAccount();
            }
        });

        //For Checking whether the user has input anything.
        editTextFName.addTextChangedListener(addTextWatcher);
        editTextLName.addTextChangedListener(addTextWatcher);
        age.addTextChangedListener(addTextWatcher);

    }

    public void createdAccount() {

        fNameInput = editTextFName.getText().toString();
        lNameInput = editTextLName.getText().toString();
        ageInput = age.getText().toString();



        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
//        Toast.makeText(this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
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
            ageInput = (age.getText().toString());

            buttonConfirm.setEnabled((!fNameInput.isEmpty() && !lNameInput.isEmpty()) && !(ageInput).isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*public void saveToFile(String fileName, String fName, String lName, String age) {
        fileName = fileName + ".txt";
        PrintWriter outputStream;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        try {
            outputStream = new PrintWriter(new FileOutputStream(file, true));
            outputStream.println("First Name: " + fName + "     Last Name:" + lName + "     Age: " + age);

            outputStream.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
    }*/
}
