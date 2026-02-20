//Constants.java
package com.vvpgroup.serieshandler;
public class Constants {
    private Constants() {}
    public static final String marker = "\u200B";           //this simbol is invisible, this using to mark wrong series
    public static final String splitter = "[\\n•\\s]+";     //series separator (splitter) set
    public static final int max_insert = 512;               //max - 1 of a new (generated) series in a pair
    public static final String pad = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"; //20 * \n
    public static final String spc_dot = "•";
    public static final String between_merged = " ";
    public static final int kiz_len = 31;
    public static final int ean_len = 13; //or 8

    public static class ActionName{
        public static final String extend = "протянуть серии";
        public static final String merge = "попарно объединить";
        public static final String sn_4z = "удалить криптохвост";
        public static final String ean_4z = "удалить криптохвост";
    }
}
