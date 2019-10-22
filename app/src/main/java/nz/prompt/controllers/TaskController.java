package nz.prompt.controllers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

import nz.prompt.database.DatabaseHandler;
import nz.prompt.model.TaskModel;
import nz.prompt.notification.BackgroundService;
import nz.prompt.notification.PromptService;

/**
 * @author Duc Nguyen
 */
public class TaskController {
    public static HashSet<TaskModel> tasks = new HashSet<>();
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static boolean CreateTask(String taskName, String description, double location_LAT, double location_LNG, int[] startDate, int[] endDate) {
        try {
            int currentID;
            String currentID_string = DatabaseHandler.dbHelper.getSetting("TaskCurrentID");
            if (currentID_string != null)
                currentID = Integer.parseInt(currentID_string) + 1;
            else
                currentID = 1;

            Calendar m_startDate = new Calendar.Builder().setDate(startDate[0], startDate[1], startDate[2]).setTimeOfDay(startDate[3], startDate[4], 0).build();
            Calendar m_endDate = new Calendar.Builder().setDate(endDate[0], endDate[1], endDate[2]).setTimeOfDay(endDate[3], endDate[4], 0).build();
            TaskModel task = new TaskModel(currentID, taskName, description, location_LAT, location_LNG, m_startDate, m_endDate, false);

            AddTask(task);

            DatabaseHandler.dbHelper.setSetting("TaskCurrentID", String.valueOf(currentID));

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void AddTask(TaskModel task)
    {
        DatabaseHandler.dbHelper.addTask(task);
        if (!task.isStatus())
            BackgroundService.setAlarm(PromptService.instance, task);
        else
            BackgroundService.cancelAlarm(PromptService.instance, task);
    }

    /**
     * Get all {@link TaskModel} which owned by {@param ownerID} and in a specified date
     *
     * @param ownerID
     * @param date
     * @returna {@link HashSet} containing all {@link TaskModel}
     */
    public static HashSet<TaskModel> GetTasks(int ownerID, Calendar date)
    {
        tasks.clear();
        HashSet<TaskModel> mTasks = DatabaseHandler.dbHelper.getTasks(ownerID, date);
        tasks.addAll(mTasks);

        return mTasks;
    }

    /**
     * Get all {@link TaskModel} which owned by {@param ownerID}
     *
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

    public static TaskModel GetTask(int ID) {
        return DatabaseHandler.dbHelper.getTask(ID);
    }

    public static boolean RemoveTask(int ID) {
        BackgroundService.cancelAlarm(PromptService.instance, GetTask(ID));
        return DatabaseHandler.dbHelper.deleteTask(ID);
    }
}
