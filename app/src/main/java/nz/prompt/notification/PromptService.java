package nz.prompt.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PromptService extends Service {
    public static PromptService instance;

    public PromptService() {
        instance = this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
