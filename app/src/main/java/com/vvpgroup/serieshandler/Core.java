//Core.java
package com.vvpgroup.serieshandler;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
public class Core {

    static private int get_separator(String value){
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
    static private String get_word_part(String value, int spr){
        return value.substring(0, spr);
    }
    static private long get_num_part(String value, int spr){
        return Long.parseLong(value.substring(spr));
    }

    static public int series_counter(String input_text){
        if (input_text == null || input_text.trim().isEmpty()){
            return 0;
        }
        String[] series = Format.IN(input_text);
        int counter = 0;
        for (String sn : series){
            if (!sn.trim().isEmpty()){
                counter++;
            }
        }
        return counter;
    }
    static public SpannableString highlight_err(String text){
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
    static public int search_marker (String text, int selection_pos){
        if (text.equals(Constants.pad) || text == null || text.trim().isEmpty() || selection_pos >= text.length()){
            return selection_pos;
        }
        int index = selection_pos + 1;
        while (index < text.length()){
            if (text.charAt(index) == Constants.marker.charAt(0)){
                return index;
            }
            index++;
        }
        for (index = 0; index < selection_pos; index++) {
            if (text.charAt(index) == Constants.marker.charAt(0)){
                return index;
            }
        }
        return selection_pos;
    }
    static public String prepare_to_copy(String text){
        int index = text.length() - 1;
        for (; text.charAt(index) == '\n'; index--);
        return text.substring(0, ++index);
    }

    static public String series_extender(String input_text){
        String[] series = Format.IN(input_text);
        ArrayList<String> generated_series = new ArrayList<>();
        if (input_text == null || input_text.isEmpty()){
            return input_text;
        }
        if (series.length < 2){
            return input_text;
        }
        if (series.length % 2 == 1){
            return input_text;
        }
        for (int index = 0; index < series.length - 1; index = index + 2){
            if (series[index].charAt(0) == Constants.marker.charAt(0) && series[index + 1].charAt(0) == Constants.marker.charAt(0)){
                series[index] = series[index].substring(1);
                series[index + 1] = series[index + 1].substring(1);
            }
            if (series[index].length() != series[index + 1].length()){
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
            int separator = get_separator(series[index]);
            int separator_target = get_separator(series[index + 1]);
            if (separator != separator_target || separator == -1 || separator_target == -1){
                //series have a different format or have got wrong separator value
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
            String word_part = get_word_part(series[index], separator);
            String word_part_target = get_word_part(series[index + 1], separator);
            if (!word_part.equals(word_part_target)){
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
            long num_part = get_num_part(series[index], separator);
            long num_part_target = get_num_part(series[index + 1], separator);
            if (num_part > num_part_target){
                long temp = num_part;
                num_part = num_part_target;
                num_part_target = temp;
                String temp_str = series[index];
                series[index] = series[index + 1];
                series[index + 1] = temp_str;
            }
            int insert = (int) (num_part_target - num_part);
            if (insert > Constants.max_insert){
                //program have a limit of the new series by pair
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
            if (insert == 0) {
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
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
                //last generated series is not equal to the expected value
                for (int x = 0; x <= insert; x++){
                    generated_series.remove(generated_series.size() - 1);
                }
                generated_series.add(Constants.marker.concat(series[index]));
                generated_series.add(Constants.marker.concat(series[index + 1]));
                continue;
            }
        }
        return Format.ERP(generated_series);
    }
    static public String series_merger(String input_text){
        String[] series = Format.IN(input_text);
        ArrayList<String> merged_series = new ArrayList<>();
        if (input_text == null || input_text.isEmpty()){
            return null;
        }
        if (series.length % 2 != 0){
            return input_text;
        }
        for (int i = 0; i < series.length; i = i + 2) {
            merged_series.add(series[i]
                    .concat(Constants.between_merged)
                    .concat(series[i + 1]));
        }
        return Format.ERP(merged_series);
    }
    static public String series_4z(String input_text) {
        if (input_text == null || input_text.isEmpty()) {
            return input_text;
        }
        String[] series = Format.IN(input_text);
        for (int i = 0; i < series.length; i++){
            series[i] = series[i].substring(0, 31);
        }
        return Format.plain(series);
    }
    static public String series_ean_4z(String input_text){
        if (input_text == null || input_text.isEmpty()) {
            return input_text;
        }
        String[] series = Format.IN(input_text);
        for (int i = 0; i < series.length; i++) {
            if (series[i].length() >= Constants.kiz_len){
                series[i] = series[i].substring(0, Constants.kiz_len);
            }
        }
        return Format.to_excel(series);
    }

    static public String getActionName() {
        if (Config.Action.isExtend()) { return Constants.ActionName.extend; }
        else if (Config.Action.isMerge()) { return Constants.ActionName.merge; }
        else if (Config.Action.isScan4z()) { return Constants.ActionName.sn_4z; }
        else if (Config.Action.isEan_4z()) { return Constants.ActionName.ean_4z; }
        return null;
    }
}
