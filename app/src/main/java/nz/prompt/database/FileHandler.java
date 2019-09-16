package nz.prompt.database;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import nz.prompt.Utilities;

public class FileHandler {
    public static boolean saveTextAsFile(String fileName, String taskType, String startDate, String startTime, String endDate, String endTime, String location, String description) {
        fileName = fileName + ".txt";
        PrintWriter outputStream;

        //writes to file

        try {
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Prompt/");
            if (!folder.exists())
            {
                folder.mkdirs();
            }

            //creates file
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Prompt/", fileName);

            outputStream = new PrintWriter(new FileOutputStream(file, true));

            outputStream.println("Task Name: " + taskType + "\nDate: " + startDate + "\nStart Time: " + startTime + "\nEnd Date:" + endDate + "\nEnd Time: " + endTime
                    + "\nLocation: " + location + "\nDescription: " + description + "\n\n");
            outputStream.close();
            return true;
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
            return false;
        }
    }
}
