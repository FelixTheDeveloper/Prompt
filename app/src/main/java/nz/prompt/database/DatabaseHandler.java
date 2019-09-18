package nz.prompt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;

import nz.prompt.model.AccountModel;
import nz.prompt.model.TaskModel;
import nz.prompt.model.UserModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static boolean loggedIn = false;

    public static DatabaseHandler db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PromptDatabase.db";
    private static final String TABLE_USERS_NAME = "Users";
    private static final String TABLE_TASKS_NAME = "Tasks";
    private static final String TABLE_ACCOUNT_NAME = "Accounts";
    private static final String TABLE_PROMPT_NAME = "PromptDH";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void EstablishDB(Context context)
    {
        db = new DatabaseHandler(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS_NAME + " (" +
                "UserID int NOT NULL PRIMARY KEY," +
                "FirstName varchar(255) NOT NULL," +
                "LastName varchar(255) NOT NULL," +
                "Age int(3) NOT NULL," +
                "Budget int NOT NULL" +
                ")";

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS_NAME + " (" +
                "TaskID int NOT NULL PRIMARY KEY," +
                "Title varchar(255) NOT NULL," +
                "Description varchar(255)," +
                "Date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "Status bool" +
                ")";

        String CREATE_TASKS_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNT_NAME + " (" +
                "AccountID int PRIMARY KEY," +
                "Email varchar(255)," +
                "Password varchar(255)," +
                "FOREIGN KEY (UserID) REFERENCES " + TABLE_USERS_NAME + "(UserID)" +
                ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASKS_ACCOUNTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addUser(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("UserID", user.getID());
        values.put("FirstName", user.getFirstName());
        values.put("LastName", user.getLastName());
        values.put("Age", user.getAge());
        values.put("PhoneNumber", user.getPhoneNumber());
        values.put("Budget", user.getBudget());

        db.insert(TABLE_USERS_NAME, null, values);
        db.close();
    }

    public void addTask(TaskModel task)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TaskID", task.getID());
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());
        values.put("Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getStartDate()));
        values.put("Status", task.isStatus());

        db.insert(TABLE_TASKS_NAME, null, values);
        db.close();
    }

    public void addAccount(AccountModel account)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("AccountID", account.getAccountID());
        values.put("Email", account.getEmail());
        values.put("Password", account.getPassword());

        db.insert(TABLE_ACCOUNT_NAME, null, values);
        db.close();
    }
}
