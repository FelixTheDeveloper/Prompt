package nz.prompt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import nz.prompt.model.TaskModel;
import nz.prompt.model.UserModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static boolean loggedIn = false;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PromptDatabase.db";
    private static final String TABLE_USERS_NAME = "Users";
    private static final String TABLE_TASKS_NAME = "Tasks";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS_NAME + " (" +
                "UserID int PRIMARY KEY," +
                "FirstName varchar(255)," +
                "LastName varchar(255)," +
                "Age int(3)," +
                "Email varchar(255)," +
                "PhoneNumber int(12)," +
                "Budget int" +
                ")";

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS_NAME + " (" +
                "TaskID int PRIMARY KEY," +
                "Title varchar(255)," +
                "Date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "Status bool" +
                ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_NAME);

        // Create tables again
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
        values.put("Email", user.getEmail());
        values.put("PhoneNumber", user.getPhoneNumber());
        values.put("Budget", user.getBudget());

        // Inserting Row
        db.insert(TABLE_USERS_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close();
    }

    public void addTask(TaskModel task)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TaskID", task.getID());
        values.put("Title", task.getTitle());
        values.put("Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getDate()));
        values.put("Status", task.isStatus());

        // Inserting Row
        db.insert(TABLE_TASKS_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close();
    }
}
