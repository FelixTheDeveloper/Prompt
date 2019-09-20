package nz.prompt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import nz.prompt.controllers.AccountController;
import nz.prompt.controllers.UserController;
import nz.prompt.database.DatabaseHandler;
import nz.prompt.notification.PromptService;
import nz.prompt.ui.main.FirstTimeActivity;
import nz.prompt.ui.main.MainMenu;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHandler.EstablishDB(this);

        boolean loggedIn = Boolean.parseBoolean(DatabaseHandler.dbHelper.getSetting("UserLoggedIn"));

        Intent service = new Intent(this, PromptService.class);
        startService(service);

        if (!loggedIn)
        {
            Intent firstTimeIntent = new Intent(this, FirstTimeActivity.class);
            startActivity(firstTimeIntent);
            finish();
        }
        else
        {
            int ID = Integer.parseInt(DatabaseHandler.dbHelper.getSetting("UserLoggedInID"));
            AccountController.currentAccount = DatabaseHandler.dbHelper.getAccount(ID);
            UserController.currentUser = AccountController.currentAccount.getUser();

            Intent mainMenuIntent = new Intent(this, MainMenu.class);
            startActivity(mainMenuIntent);
            finish();
        }
    }
}
