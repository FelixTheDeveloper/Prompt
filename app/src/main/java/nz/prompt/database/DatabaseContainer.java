package nz.prompt.database;

import android.content.Context;

public class DatabaseContainer {
    public static DatabaseHandler db;

    public static void EstablishDB(Context context)
    {
        db = new DatabaseHandler(context);
    }
}
