package nz.prompt;

import android.widget.Toast;

public class Utilities {
    public static void showToast(String msg)
    {
        Toast.makeText(null, msg, Toast.LENGTH_SHORT).show();
    }
}
