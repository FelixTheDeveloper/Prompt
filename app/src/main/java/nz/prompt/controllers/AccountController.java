package nz.prompt.controllers;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.AccountModel;
import nz.prompt.model.UserModel;

/**
 * @author Duc Nguyen
 */
public class AccountController {
    public static AccountModel CreateAccount(String email, String password, UserModel user)
    {
        int currentID;
        String currentID_string = DatabaseHandler.dbHelper.getSetting("Account_CurrentID");
        if (currentID_string != null)
            currentID = Integer.parseInt(currentID_string) + 1;
        else
            currentID = 1;

        if (email.isEmpty() || password.isEmpty() || user == null)
            return null;

        AccountModel account = new AccountModel(currentID, email, password, user);

        DatabaseHandler.dbHelper.addAccount(account);

        DatabaseHandler.dbHelper.setSetting("Account_CurrentID", String.valueOf(currentID));

        return account;
    }

    public static AccountModel GetAccount(int ID)
    {
        return DatabaseHandler.dbHelper.getAccount(ID);
    }

    public static boolean CheckAccount(String email)
    {
        return DatabaseHandler.dbHelper.checkAccount(email);
    }
}
