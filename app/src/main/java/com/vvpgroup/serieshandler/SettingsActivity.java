//SettingsActivity.java
package com.vvpgroup.serieshandler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton button_return;
        RadioButton rb_extend, rb_merge, rb_4z, rb_ean_4z;

        button_return = findViewById(R.id.button_return);
        rb_extend = findViewById(R.id.rb_extend);
        rb_merge = findViewById(R.id.rb_merge);
        rb_4z = findViewById(R.id.rb_4z);
        rb_ean_4z = findViewById(R.id.rb_ean_4z);

        button_return.setOnClickListener(v -> {
            if (rb_extend.isChecked()){
                Config.Action.Set.extend();
            } else if (rb_merge.isChecked()) {
                Config.Action.Set.merge();
            } else if (rb_4z.isChecked()) {
                Config.Action.Set.scan4z();
            } else if (rb_ean_4z.isChecked()) {
                Config.Action.Set.ean_4z();
            }
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            //finish();
        });
    }
}