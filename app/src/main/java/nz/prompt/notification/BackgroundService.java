package nz.prompt.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.widget.Toast;

import nz.prompt.controllers.TaskController;
import nz.prompt.model.TaskModel;

import static android.content.Context.ALARM_SERVICE;

public class BackgroundService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TaskModel task = TaskController.GetTask(intent.getIntExtra("id", 0));

        NotificationService.notify(context, task);
    }

    public static void setAlarm(Context context, TaskModel task)
    {
        ComponentName receiver = new ComponentName(context, BackgroundService.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(context, BackgroundService.class);
        intent.putExtra("id", task.getID());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, task.getStartDate().getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelAlarm(Context context, TaskModel task)
    {
        ComponentName receiver = new ComponentName(context, BackgroundService.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(context, BackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
