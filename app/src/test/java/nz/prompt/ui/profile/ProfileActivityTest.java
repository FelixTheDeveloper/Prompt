package nz.prompt.ui.profile;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.io.File;


import static org.junit.Assert.*;

public class ProfileActivityTest {

    @Test
    public void pickImageFromGallery() {
        File file = new File("storage/emulated/0/Download/Ken_SSBU.png");

        assertNotNull(file);
        assertEquals(0, file.length());


    }

}