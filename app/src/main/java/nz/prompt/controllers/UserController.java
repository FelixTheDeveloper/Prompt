package nz.prompt.controllers;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.UserModel;

/**
 * @author Duc Nguyen
 */
public class UserController {
    public static UserModel currentUser;

    public static UserModel CreateUser(String firstName, String lastName, int age, int budget)
    {
        int currentID;
        String currentID_string = DatabaseHandler.dbHelper.getSetting("User_CurrentID");
        if (currentID_string != null)
            currentID = Integer.parseInt(currentID_string) + 1;
        else
            currentID = 1;

        UserModel user = new UserModel(currentID, firstName, lastName, age, budget);

        DatabaseHandler.dbHelper.addUser(user);

        DatabaseHandler.dbHelper.setSetting("User_CurrentID", String.valueOf(currentID));

        return user;
    }

    public static UserModel GetUser(int ID)
    {
        return DatabaseHandler.dbHelper.getUser(ID);
    }
}
