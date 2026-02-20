//Format.java
package com.vvpgroup.serieshandler;

import java.util.ArrayList;
import java.util.Arrays;

public class Format {
    public static String[] IN(String text) {
        if (text == null || text.isEmpty()){
            return null;
        }
        String[] series = text.split(Constants.splitter);
        int counter = 0;
        for (String s : series){
            if (s != null && !s.isEmpty()){
                counter++;
            }
        }
        String[] formated = new String[counter];
        int i = 0;
        for (String s : series){
            if (s != null && !s.isEmpty()){
                formated[i] = s;
                if (formated[i].charAt(0) == Constants.marker.charAt(0)){
                    formated[i] = formated[i].substring(1);
                }
                i++;
            }
        }
        return formated;
    }
    public static String ERP(ArrayList<String> result){
        String output = "";
        for (int i = 0; i < result.size(); i++) {
            result.set(i, Constants.spc_dot
                    .concat(result.get(i))
                    .concat(Constants.spc_dot)
                    .concat("\n")   );
            output = output.concat(result.get(i));
        }
        return output;
    }
    public static String ERP(String[] result) {
        String output = "";
        for (int i = 0; i < result.length; i++){
            output = output
                    .concat(Constants.spc_dot)
                    .concat(result[i])
                    .concat(Constants.spc_dot)
                    .concat("\n");
        }
        return output;
            
    }
    public static String plain(ArrayList<String> result) {
        String output = "";
        for (int i = 0; i < result.size(); i++) {
            output = output.concat(result.get(i))
                    .concat("\n");
        }
        return output;
    }
    public static String plain(String[] result){
        String output = "";
        for (int i = 0; i < result.length; i++) {
            output = output.concat(result[i])
                    .concat("\n");
        }
        return output;
    }
    public static String to_excel(String[] result) {
        if (result == null || result.length == 0) {
            return null;
        }
        if (result[0].length() > Constants.ean_len) {
            return null;
        }
        String output = "";
        for (int i = 0; i < result.length; i++){
            if (result[i].length() <= Constants.ean_len){
                output = output
                        .concat(result[i]);
            }
            else {
                output = output
                        .concat("\t")
                        .concat(result[i])
                        .concat("\n");
            }
        }
        return output;
    }
}
