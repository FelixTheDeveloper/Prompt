package nz.prompt.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import nz.prompt.controllers.TaskController;
import nz.prompt.model.TaskModel;

public class BackgroundService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Prompt:wakeLock");
        wl.acquire(10 * 60 * 1000L /*10 minutes*/);

        int id = intent.getIntExtra("id", 0);
        TaskModel task = TaskController.GetTask(id);

        // Put here YOUR code.
        NotificationService.notify(PromptService.instance, task);

        wl.release();
    }

    public static void setAlarm(Context context, TaskModel task) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BackgroundService.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, task.getID(), intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, task.getStartDate().getTime(), pi);
    }

    public static void cancelAlarm(Context context, TaskModel task) {
        Intent intent = new Intent(context, BackgroundService.class);
        intent.putExtra("ID", task.getID());
        PendingIntent sender = PendingIntent.getBroadcast(context, task.getID(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
