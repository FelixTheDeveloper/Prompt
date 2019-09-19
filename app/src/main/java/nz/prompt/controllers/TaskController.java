package nz.prompt.controllers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.TaskModel;

/**
 * @author Duc Nguyen
 */
public class TaskController {
    private static ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

    public static boolean CreateTask(String taskName, String locationName, String description, String startDate, String endDate) {
        try {
            int currentID;
            String currentID_string = DatabaseHandler.dbHelper.getSetting("Task_CurrentID");
            if (currentID_string != null)
                currentID = Integer.parseInt(currentID_string) + 1;
            else
                currentID = 1;

            Date m_startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
            Date m_endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
            TaskModel task = new TaskModel(currentID, taskName, description, locationName, m_startDate, m_endDate, false);

            tasks.add(task);

            DatabaseHandler.dbHelper.addTask(task);

            DatabaseHandler.dbHelper.setSetting("Task_CurrentID", String.valueOf(currentID));

            return true;
        }
        catch (Exception e)
        {
            Log.d("TaskController.CreateTask()", e.getMessage());
            return false;
        }
    }

    public static TaskModel GetTask(int ID)
    {
        return DatabaseHandler.dbHelper.getTask(ID);
    }
}
