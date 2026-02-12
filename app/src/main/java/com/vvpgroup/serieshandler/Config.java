//Config.java
package com.vvpgroup.serieshandler;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
public class Config {
    private Config() {}
    public static final String marker = "\u200B";           //this simbol is invisible, this using to mark wrong series
    public static final String splitter = "[\\n•\\s]+";     //series separator (splitter) set
    public static final int max_insert = 512;               //max - 1 of a new (generated) series in a pair
    public static final String pad = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"; //20 * \n
}
