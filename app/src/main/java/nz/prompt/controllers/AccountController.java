package nz.prompt.controllers;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.AccountModel;

public class AccountController {
    public static void CreateAccount(String email, String password)
    {
        AccountModel account = new AccountModel(email, password);

        DatabaseHandler.db.addAccount(account);
    }
}
