package nz.prompt.database;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import nz.prompt.Utilities;

public class FileHandler {

    public static void saveTextAsFile(String fileName, String taskType, String startDate, String startTime, String endDate, String endTime, String location, String description) {
        fileName = fileName + ".txt";
        PrintWriter outputStream;

        //writes to file

        try {
            //creates file
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Prompt/", fileName);
            if (!file.exists())
                file.mkdirs();

            outputStream = new PrintWriter(new FileOutputStream(file, true));

            outputStream.println("Task Name: " + taskType + "\nDate: " + startDate + "\nStart Time: " + startTime + "\nEnd Date:" + endDate + "\nEnd Time: " + endTime
                    + "\nLocation: " + location + "\nDescription: " + description + "\n\n");
            outputStream.close();
            Utilities.showToast("Saved Successfully!");
        } catch (Exception e) {
            Utilities.showToast("File Not Found");
        }
    }
}
