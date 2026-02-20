//MainActivity.java
package com.vvpgroup.serieshandler;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static boolean configIsInitialized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region onCreate_base
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //endregion

        if (!configIsInitialized){
            configIsInitialized = true;
            Config.Action.init();
        }

        EditText main_field;
        TextView series_cnt;
        Button button_action;
        ImageButton button_delete, button_next_error, button_copy, button_settings;

        main_field = findViewById(R.id.main_field);
        series_cnt = findViewById(R.id.series_cnt);
        button_action = findViewById(R.id.button_action);
        button_delete = findViewById(R.id.button_delete);
        button_next_error = findViewById(R.id.button_next_error);
        button_settings = findViewById(R.id.button_settings);
        button_copy = findViewById(R.id.button_copy);

        button_action.setText(Core.getActionName());

        TextWatcher repeater = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                series_cnt.setText(String.valueOf(
                        Core.series_counter(main_field.getText().toString())
                ));
            }
            //region useless_region
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //endregion
        };
        main_field.addTextChangedListener(repeater);
        button_action.setOnClickListener(v -> {
            if(Config.Action.isExtend()){
                String input = main_field.getText().toString();
                String output = Core.series_extender(input);
                if (!output.equals(input)){
                    main_field.setText(Core.highlight_err(output.concat(Constants.pad)));
                    main_field.setSelection(output.length());
                }
            } else if (Config.Action.isMerge()) {
                String input = main_field.getText().toString();
                String output = Core.series_merger(input);
                if (!output.equals(input)){
                    main_field.setText(Core.highlight_err(output.concat(Constants.pad)));
                    main_field.setSelection(output.length());
                }
            } else if (Config.Action.isScan4z()) {
                String input = main_field.getText().toString();
                String output = Core.series_4z(input);
                if (!output.equals(input)) {
                    main_field.setText(Core.highlight_err(output.concat(Constants.pad)));
                    main_field.setSelection(output.length());
                }
            } else if (Config.Action.isEan_4z()) {
                String input = main_field.getText().toString();
                String output = Core.series_ean_4z(input);
                if (!output.equals(input)) {
                    main_field.setText(Core.highlight_err(output.concat(Constants.pad)));
                    main_field.setSelection(output.length());
                }
            }


        });
        button_copy.setOnClickListener((v ->{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String text_to_copy = Core.prepare_to_copy(main_field.getText().toString());
            ClipData clip = ClipData.newPlainText("", text_to_copy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "скопировано", Toast.LENGTH_SHORT).show();
        }));
        button_delete.setOnClickListener(v -> {
            main_field.setText("");
            main_field.setSelection(0);
        });
        button_next_error.setOnClickListener(v -> {
            int pos = Core.search_marker(main_field.getText().toString(), main_field.getSelectionStart());
            main_field.setSelection(pos);

        });
        button_settings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

    }
}