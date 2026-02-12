//Core.java
package com.vvpgroup.serieshandler;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;
import android.graphics.Color;
import android.text.Spannable;
/// ////////////////////////////////////////////////////////////////////////////////////////////////
public class Core {
    private static String[] split_series(String input){
        if (input == null || input.isEmpty()){
            return new String[0];
        }
        String[] temp_array = input.split(Config.splitter);
        int cnt = 0;
        for (String s : temp_array){
            if (s != null && !s.isEmpty()){
                cnt++;
            }
        }
        int i = 0;
        String[] result = new String[cnt];
        for (String s : temp_array) {
            if (s != null && !s.isEmpty()){
                result[i] = s;
                i++;
            }
        }

        return result;
    }
    private static int get_separator(String value){
        if (value.isEmpty())
            return -1;
        int result = value.length() - 1;
        while(result >= 0 && Character.isDigit(value.charAt(result))){
            result--;
        }
        result++;
        if (result == value.length()){
            return -1;
        }

        return result;
    }
    public static int series_counter(String input_text){
        if (input_text == null || input_text.trim().isEmpty()){
            return 0;
        }
        String[] series = split_series(input_text);
        int counter = 0;
        for (String sn : series){
            if (!sn.trim().isEmpty()){
                counter++;
            }
        }

        return counter;
    }
    public static String series_extender(String input_text){

        //region initialization
        String[] series = Core.split_series(input_text);
        ArrayList<String> generated_series = new ArrayList<>();
        //endregion

        //region cath error on start
        if (input_text == null || input_text.isEmpty()){
            //error
            //got null or wrong String input
            return input_text;
        }
        if (series.length < 2){
            //error
            //got less than 2 series
            return input_text;
        }
        if (series.length % 2 == 1){
            //notification
            //1 series could be skip
            return input_text;
        }
        //endregion

        /// ////////////////////////////////////////////////////////////////////////////////////////
        //pairwise series processing
        for (int index = 0; index < series.length - 1; index = index + 2){

            if (series[index].charAt(0) == Config.marker.charAt(0) && series[index + 1].charAt(0) == Config.marker.charAt(0)){
                series[index] = series[index].substring(1);
                series[index + 1] = series[index + 1].substring(1);
            }

            if (series[index].length() != series[index + 1].length()){
                //exception
                //length of the pair is not equal
                generated_series.add(Config.marker.concat(series[index + 1]));
                generated_series.add(Config.marker.concat(series[index]));
                continue;
            }

            int separator = Core.get_separator(series[index]);
            int separator_target = Core.get_separator(series[index + 1]);

            if (separator != separator_target || separator == -1 || separator_target == -1){
                //exception
                //series have a different format or have got wrong separator value
                generated_series.add(Config.marker.concat(series[index + 1]));
                generated_series.add(Config.marker.concat(series[index]));
                continue;
            }

            String word_part = Core.get_word_part(series[index], separator);
            String word_part_target = Core.get_word_part(series[index + 1], separator);

            if (!word_part.equals(word_part_target)){
                //exception
                //word parts of the pair is not equal
                generated_series.add(Config.marker.concat(series[index + 1]));
                generated_series.add(Config.marker.concat(series[index]));
                continue;
            }

            long num_part = get_num_part(series[index], separator);
            long num_part_target = get_num_part(series[index + 1], separator);

            if (num_part > num_part_target){
                //processing
                //should to switch: less value should have a first position
                long temp = num_part;
                num_part = num_part_target;
                num_part_target = temp;
                String temp_str = series[index];
                series[index] = series[index + 1];
                series[index + 1] = temp_str;
            }

            int insert = (int) (num_part_target - num_part);

            if (insert > Config.max_insert){
                //exception
                //program have a limit of the new series by pair
                generated_series.add(Config.marker.concat(series[index + 1]));
                generated_series.add(Config.marker.concat(series[index]));
                continue;
            }

            //processing of the current pair
            for(int x = 0; x <= insert; x++){
                String num_end = Long.toString(num_part + x);
                int zero_cnt = series[index].length() - word_part.length() - num_end.length();
                while (zero_cnt > 0){
                    num_end = "0".concat(num_end);
                    zero_cnt--;
                }
                generated_series.add(word_part.concat(num_end));
            }

            if (!generated_series.get(generated_series.size() - 1).equals(series[index + 1])){
                //error
                //last generated series is not equal to the expected value
                for (int x = 0; x <= insert; x++){
                    generated_series.remove(generated_series.size() - 1);
                }
                generated_series.add(Config.marker.concat(series[index + 1]));
                generated_series.add(Config.marker.concat(series[index]));
                continue;
            }
        }

        //getting ready a generated massive for the output
        String output_text = "";
        for (int i = 0; i < generated_series.size(); i++){
            generated_series.set(i, "•" + generated_series.get(i) + "•" + "\n");
            output_text = output_text.concat(generated_series.get(i));
        }
        return output_text;
    }
    private static String get_word_part(String value, int spr){
        return value.substring(0, spr);
    }
    private static long get_num_part(String value, int spr){
        return Long.parseLong(value.substring(spr));
    }
    public static SpannableString highlight_err(String text){
        SpannableString result = new SpannableString(text);
        int start = -1;

        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (c == '\u200B'){
                start = i;
            }
            else if (c == '•' && start != -1){
                ForegroundColorSpan red = new ForegroundColorSpan(Color.RED);
                result.setSpan(red, start, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = -1;
            }
        }

        return result;
    }
    public static int search_marker (String text, int selection_pos){
        if (text.equals(Config.pad) || text == null || text.trim().isEmpty() || selection_pos >= text.length()){
            return selection_pos;
        }
        int index = selection_pos + 1;
        while (index < text.length()){
            if (text.charAt(index) == Config.marker.charAt(0)){
                return index;
            }
            index++;
        }
        for (index = 0; index < selection_pos; index++) {
            if (text.charAt(index) == Config.marker.charAt(0)){
                return index;
            }
        }
        return selection_pos;
    }
    public static String prepare_to_copy(String text){
        int index = text.length() - 1;
        for (; text.charAt(index) == '\n'; index--);
        return text.substring(0, ++index);
    }
}
