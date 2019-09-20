package nz.prompt.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import nz.prompt.ui.main.MainMenu;
import nz.prompt.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView vieImage;
    Button selectButton;
    private Button button;


    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        vieImage = findViewById(R.id.profile_avatarImageView);
        selectButton = findViewById(R.id.profile_choose_image_btn);

        selectButton.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE);

            }
            else {
                pickImageFromGallery();
            }

        });

        //button = findViewById(R.id.profile_budgetButton);
        //button.setOnClickListener(v -> goProfileAct());

        button = findViewById(R.id.profile_backButton);
        button.setOnClickListener(v -> backMain());
    }

    public void backMain() {
        finish();
    }


    public void goProfileAct() {
        Intent intent = new Intent(ProfileActivity.this, BudgetActivity.class);
        startActivity(intent);
    }


    public void pickImageFromGallery() {
        //Picking image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // The permission was granted
                pickImageFromGallery();
            }
            else {
                //The permission was denied
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //Setting the image to be viewed
            vieImage.setImageURI(data.getData());
        }

    }
}

