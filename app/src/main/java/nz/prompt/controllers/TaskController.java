package nz.prompt.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nz.prompt.database.DatabaseContainer;
import nz.prompt.model.TaskModel;

public class TaskController {
    private static ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

    public static boolean addTask(String taskName, String locationName, String description, String startDate, String endDate) {
        try {
            Date m_startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
            Date m_endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
            TaskModel task = new TaskModel(taskName, description, m_startDate, m_endDate, false);

            tasks.add(task);

            DatabaseContainer.db.addTask(task);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


}
