package nz.prompt.controllers;

import android.content.Intent;

import java.util.ArrayList;

import nz.prompt.MainMenu;
import nz.prompt.database.FileHandler;
import nz.prompt.model.TaskModel;

public class TaskController {
    private ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

    public static boolean addTask(String taskName, String locationName, String description, String startTime, String endTime, String startDate, String endDate) {
        return FileHandler.saveTextAsFile("Tasks", taskName, startDate, startTime, endDate, endTime, locationName, description);
    }
}
