package nz.prompt.controllers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.TaskModel;

/**
 * @author Duc Nguyen
 */
public class TaskController {
    public static HashSet<TaskModel> tasks = new HashSet<>();
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static boolean CreateTask(String taskName, String locationName, String description, String startDate, String endDate) {
        try {
            int currentID;
            String currentID_string = DatabaseHandler.dbHelper.getSetting("TaskCurrentID");
            if (currentID_string != null)
                currentID = Integer.parseInt(currentID_string) + 1;
            else
                currentID = 1;

            Date m_startDate = dateFormat.parse(startDate);
            Date m_endDate = dateFormat.parse(endDate);
            TaskModel task = new TaskModel(currentID, taskName, description, locationName, m_startDate, m_endDate, false);

            AddTask(task);

            DatabaseHandler.dbHelper.setSetting("TaskCurrentID", String.valueOf(currentID));

            return true;
        }
        catch (Exception e)
        {
            Log.d("TaskController.CreateTask()", e.getMessage());
            return false;
        }
    }

    public static void AddTask(TaskModel task)
    {
        DatabaseHandler.dbHelper.addTask(task);
    }

    /**
     * Get all {@link TaskModel} which owned by {@param ownerID} and in a specified date
     * @param ownerID
     * @param date
     * @returna {@link HashSet} containing all {@link TaskModel}
     */
    public static HashSet<TaskModel> GetTasks(int ownerID, Date date)
    {
        tasks.clear();
        HashSet<TaskModel> mTasks = DatabaseHandler.dbHelper.getTasks(ownerID, date);
        tasks.addAll(mTasks);

        return mTasks;
    }

    /**
     * Get all {@link TaskModel} which owned by {@param ownerID}
     * @param ownerID the ID of the user (owner)
     * @return a {@link HashSet} containing all {@link TaskModel}
     */
    public static HashSet<TaskModel> GetTasks(int ownerID)
    {
        tasks.clear();
        HashSet<TaskModel> mTasks = DatabaseHandler.dbHelper.getTasks(ownerID);
        tasks.addAll(mTasks);
        return mTasks;
    }

    public static boolean RemoveTask(int ID)
    {
        return DatabaseHandler.dbHelper.deleteTask(ID);
    }
}
