package nz.prompt.controllers;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.UserModel;

public class UserController {
    public static void CreateUser(String firstName, String lastName, int age, int budget)
    {
        int currentID;
        String currentID_string = DatabaseHandler.db.getSetting("User_CurrentID");
        if (currentID_string != null)
            currentID = Integer.parseInt(currentID_string) + 1;
        else
            currentID = 1;

        UserModel user = new UserModel(currentID, firstName, lastName, age, budget);

        DatabaseHandler.db.addUser(user);

        DatabaseHandler.db.setSetting("User_CurrentID", String.valueOf(currentID));
    }

    public static UserModel GetUser(int ID)
    {
        return DatabaseHandler.db.getUser(ID);
    }
}
