package nz.prompt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nz.prompt.model.AccountModel;
import nz.prompt.model.TaskModel;
import nz.prompt.model.UserModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static DatabaseHandler db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PromptDatabase.db";
    private static final String TABLE_USERS_NAME = "Users";
    private static final String TABLE_TASKS_NAME = "Tasks";
    private static final String TABLE_ACCOUNT_NAME = "Accounts";
    private static final String TABLE_PROMPT_NAME = "PromptDB";

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
                "Location varchar(255)," +
                "StartDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                "EndDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                "Status bool NOT NULL" +
                ")";

        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNT_NAME + " (" +
                "AccountID int NOT NULL PRIMARY KEY," +
                "Email varchar(255) NOT NULL," +
                "Password varchar(255) NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES " + TABLE_USERS_NAME + "(UserID)" +
                ")";

        String CREATE_PROMPT_TABLE = "CREATE TABLE " + TABLE_PROMPT_NAME + " (" +
                "setting varchar(255) NOT NULL PRIMARY KEY," +
                "value varchar(255) NOT NULL PRIMARY KEY" +
                ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_PROMPT_TABLE);

        setSetting("User_CurrentID", "0");
        setSetting("Task_CurrentID", "0");
        setSetting("Account_CurrentID", "0");
        setSetting("UserLoggedIn", "FALSE");
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
        values.put("Budget", user.getBudget());

        db.insert(TABLE_USERS_NAME, null, values);
        db.close();
    }

    public UserModel getUser(int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS_NAME, null, "UserID = ?", new String[] {String.valueOf(ID)}, null, null, null);

        if (cursor.moveToNext())
        {
            int id;
            String firstName, lastName;
            int age, budget;

            int index;
            index = cursor.getColumnIndex("UserID");
            if (index != -1)
                id = cursor.getInt(index);
            else
                return null;

            index = cursor.getColumnIndex("FirstName");
            if (index != -1)
                firstName = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("LastName");
            if (index != -1)
                lastName = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("Age");
            if (index != -1)
                age = cursor.getInt(index);
            else
                return null;

            index = cursor.getColumnIndex("Budget");
            if (index != -1)
                budget = cursor.getInt(index);
            else
                return null;

            UserModel user = new UserModel(id, firstName, lastName, age, budget);

            return user;
        }

        return null;
    }

    public void addTask(TaskModel task)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TaskID", task.getID());
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());
        values.put("StartDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getStartDate()));
        values.put("EndDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getStartDate()));
        values.put("Status", task.isStatus());

        db.insert(TABLE_TASKS_NAME, null, values);
        db.close();
    }

    public TaskModel getTask(int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS_NAME, null, "TaskID = ?", new String[] {String.valueOf(ID)}, null, null, null);

        if (cursor.moveToNext())
        {
            int id;
            String title, description, location;
            Date startDate, endDate;
            boolean status;

            int index;
            index = cursor.getColumnIndex("TaskID");
            if (index != -1)
                id = cursor.getInt(index);
            else
                return null;

            index = cursor.getColumnIndex("Title");
            if (index != -1)
                title = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("Description");
            if (index != -1)
                description = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("Location");
            if (index != -1)
                location = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("StartDate");
            if (index != -1)
            {
                try {
                    startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(index));
                }
                catch (ParseException e)
                {
                    return null;
                }
            }
            else
                return null;

            index = cursor.getColumnIndex("EndDate");
            if (index != -1)
            {
                try {
                    endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(index));
                }
                catch (ParseException e)
                {
                    return null;
                }
            }
            else
                return null;

            index = cursor.getColumnIndex("Status");
            if (index != -1)
                status = cursor.getInt(index) > 0;
            else
                return null;

            TaskModel task = new TaskModel(id, title, description, location, startDate, endDate, status);

            return task;
        }

        return null;
    }

    public void addAccount(AccountModel account)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("AccountID", account.getAccountID());
        values.put("Email", account.getEmail());
        values.put("Password", account.getPassword());
        values.put("UserID", account.getUser().getID());

        db.insert(TABLE_ACCOUNT_NAME, null, values);
        db.close();
    }

    public AccountModel getAccount(int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNT_NAME, null, "AccountID = ?", new String[] {String.valueOf(ID)}, null, null, null);

        if (cursor.moveToNext())
        {
            int id, userID;
            String email, password;

            int index;
            index = cursor.getColumnIndex("AccountID");
            if (index != -1)
                id = cursor.getInt(index);
            else
                return null;

            index = cursor.getColumnIndex("Email");
            if (index != -1)
                email = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("Password");
            if (index != -1)
                password = cursor.getString(index);
            else
                return null;

            index = cursor.getColumnIndex("UserID");
            if (index != -1)
                userID = cursor.getInt(index);
            else
                return null;

            UserModel user = getUser(userID);
            AccountModel account = new AccountModel(id, email, password, user);

            return account;
        }

        return null;
    }

    public void setSetting(String setting, String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(setting, value);

        db.insert(TABLE_PROMPT_NAME, null, values);
    }

    public String getSetting(String setting)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] column = {"value"};

        Cursor cursor = db.query(TABLE_PROMPT_NAME, column, "setting = ?", new String[] {setting}, null, null, null);

        if (cursor.moveToNext())
        {
            return cursor.getString(0);
        }
        else
        {
            return null;
        }
    }
}
