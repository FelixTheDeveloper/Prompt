package nz.prompt.controllers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.TaskModel;

/**
 * @author Duc Nguyen
 */
public class TaskController {
    public static HashSet<TaskModel> tasks = new HashSet<>();

    public static boolean CreateTask(String taskName, String locationName, String description, String startDate, String endDate) {
        try {
            int currentID;
            String currentID_string = DatabaseHandler.dbHelper.getSetting("TaskCurrentID");
            if (currentID_string != null)
                currentID = Integer.parseInt(currentID_string) + 1;
            else
                currentID = 1;

            Date m_startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
            Date m_endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
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

    public static void GetTasks(int ownerID)
    {
        tasks.clear();
        tasks.addAll(DatabaseHandler.dbHelper.getTasks(ownerID));
    }

    public static boolean RemoveTask(int ID)
    {
        return DatabaseHandler.dbHelper.deleteTask(ID);
    }
}
