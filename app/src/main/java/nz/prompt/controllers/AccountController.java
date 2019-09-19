package nz.prompt.controllers;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.AccountModel;
import nz.prompt.model.UserModel;

public class AccountController {
    public static void CreateAccount(String email, String password, UserModel user)
    {
        int currentID;
        String currentID_string = DatabaseHandler.db.getSetting("Account_CurrentID");
        if (currentID_string != null)
            currentID = Integer.parseInt(currentID_string) + 1;
        else
            currentID = 1;

        AccountModel account = new AccountModel(currentID, email, password, user);

        DatabaseHandler.db.addAccount(account);

        DatabaseHandler.db.setSetting("Account_CurrentID", String.valueOf(currentID));
    }

    public static AccountModel GetAccount(int ID)
    {
        return DatabaseHandler.db.getAccount(ID);
    }
}
