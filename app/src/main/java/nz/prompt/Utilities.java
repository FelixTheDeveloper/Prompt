package nz.prompt;

import android.content.Context;
import android.widget.Toast;

public class Utilities {
    private static Context context;

    public static void showToast(String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void setContext(Context context)
    {
        Utilities.context = context;
    }
}
