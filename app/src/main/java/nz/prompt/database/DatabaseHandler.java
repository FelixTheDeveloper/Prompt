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

/**
 * @author Duc Nguyen
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static DatabaseHandler dbHelper;

    private SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PromptDatabase.dbHelper";
    private static final String TABLE_USERS_NAME = "Users";
    private static final String TABLE_TASKS_NAME = "Tasks";
    private static final String TABLE_ACCOUNT_NAME = "Accounts";
    private static final String TABLE_PROMPT_NAME = "PromptDB";

    private boolean Created = false;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void EstablishDB(Context context)
    {
        dbHelper = new DatabaseHandler(context);
        dbHelper.dbRead = dbHelper.getReadableDatabase();
        dbHelper.dbWrite = dbHelper.getWritableDatabase();

        if (dbHelper.Created)
        {
            dbHelper.setSetting("User_CurrentID", "0");
            dbHelper.setSetting("Task_CurrentID", "0");
            dbHelper.setSetting("Account_CurrentID", "0");
            dbHelper.setSetting("UserLoggedIn", "FALSE");
            dbHelper.setSetting("UserLoggedInID", "0");
            dbHelper.Created = false;
        }
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
                "Email varchar(255) NOT NULL UNIQUE," +
                "Password varchar(255) NOT NULL," +
                "UserID int," +
                "FOREIGN KEY (UserID) REFERENCES " + TABLE_USERS_NAME + "(UserID)" +
                ")";

        String CREATE_PROMPT_TABLE = "CREATE TABLE " + TABLE_PROMPT_NAME + " (" +
                "setting varchar(255) NOT NULL PRIMARY KEY," +
                "value varchar(255) NOT NULL" +
                ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_PROMPT_TABLE);

        Created = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMPT_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addUser(UserModel user)
    {
        SQLiteDatabase db = dbWrite;

        ContentValues values = new ContentValues();
        values.put("UserID", user.getID());
        values.put("FirstName", user.getFirstName());
        values.put("LastName", user.getLastName());
        values.put("Age", user.getAge());
        values.put("Budget", user.getBudget());

        db.insert(TABLE_USERS_NAME, null, values);
    }

    public UserModel getUser(int ID)
    {
        SQLiteDatabase db = dbRead;

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
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("FirstName");
            if (index != -1)
                firstName = cursor.getString(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("LastName");
            if (index != -1)
                lastName = cursor.getString(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Age");
            if (index != -1)
                age = cursor.getInt(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Budget");
            if (index != -1)
                budget = cursor.getInt(index);
            else
            {
                cursor.close();
                return null;
            }

            UserModel user = new UserModel(id, firstName, lastName, age, budget);

            cursor.close();

            return user;
        }

        return null;
    }

    public void addTask(TaskModel task)
    {
        SQLiteDatabase db = dbWrite;

        ContentValues values = new ContentValues();
        values.put("TaskID", task.getID());
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());
        values.put("StartDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getStartDate()));
        values.put("EndDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getStartDate()));
        values.put("Status", task.isStatus());

        db.insert(TABLE_TASKS_NAME, null, values);
    }

    public TaskModel getTask(int ID)
    {
        SQLiteDatabase db = dbRead;

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
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Title");
            if (index != -1)
                title = cursor.getString(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Description");
            if (index != -1)
                description = cursor.getString(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Location");
            if (index != -1)
                location = cursor.getString(index);
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("StartDate");
            if (index != -1)
            {
                try {
                    startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(index));
                }
                catch (ParseException e)
                {
                    cursor.close();
                    return null;
                }
            }
            else
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("EndDate");
            if (index != -1)
            {
                try {
                    endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(index));
                }
                catch (ParseException e)
                {
                    cursor.close();
                    return null;
                }
            }
            else {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Status");
            if (index != -1)
                status = cursor.getInt(index) > 0;
            else
            {
                cursor.close();
                return null;
            }

            TaskModel task = new TaskModel(id, title, description, location, startDate, endDate, status);

            cursor.close();
            return task;
        }

        cursor.close();
        return null;
    }

    public void addAccount(AccountModel account)
    {
        SQLiteDatabase db = dbWrite;

        ContentValues values = new ContentValues();
        values.put("AccountID", account.getAccountID());
        values.put("Email", account.getEmail());
        values.put("Password", account.getPassword());
        values.put("UserID", account.getUser().getID());

        db.insert(TABLE_ACCOUNT_NAME, null, values);
    }

    public AccountModel getAccount(int ID)
    {
        SQLiteDatabase db = dbRead;

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
            {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Email");
            if (index != -1)
                email = cursor.getString(index);
            else {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("Password");
            if (index != -1)
                password = cursor.getString(index);
            else {
                cursor.close();
                return null;
            }

            index = cursor.getColumnIndex("UserID");
            if (index != -1)
                userID = cursor.getInt(index);
            else {
                cursor.close();
                return null;
            }

            UserModel user = getUser(userID);
            AccountModel account = new AccountModel(id, email, password, user);

            cursor.close();

            return account;
        }

        cursor.close();

        return null;
    }

    public boolean checkAccount(String email)
    {
        SQLiteDatabase db = dbRead;

        Cursor cursor = db.query(TABLE_ACCOUNT_NAME, null, "Email = ?", new String[] {email}, null, null, null);

        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public void setSetting(String setting, String value)
    {
        SQLiteDatabase db = dbWrite;

        ContentValues values = new ContentValues();
        values.put(setting, value);

        db.insert(TABLE_PROMPT_NAME, null, values);
    }

    public String getSetting(String setting)
    {
        SQLiteDatabase db = dbRead;
        String[] column = {"value"};

        Cursor cursor = db.query(TABLE_PROMPT_NAME, column, "setting = ?", new String[] {setting}, null, null, null);

        if (cursor.moveToNext())
        {
            cursor.close();
            return cursor.getString(0);
        }
        else
        {
            cursor.close();
            return null;
        }
    }
}
