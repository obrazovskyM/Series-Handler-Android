//Config.java
package com.vvpgroup.serieshandler;

public class Config {
    public static class Action {
        private static boolean extend;
        private static boolean merge;
        private static boolean scan4z;
        private static boolean ean_4z;
        private static void reset(){
            extend = false;
            merge = false;
            scan4z = false;
            ean_4z = false;
        }
        public static class Set{
            public static void extend() { reset(); extend = true; }
            public static void merge()  { reset(); merge = true;  }
            public static void scan4z() { reset(); scan4z = true; }
            public static void ean_4z() { reset(); ean_4z = true; }
        }
        public static boolean isExtend() { return extend; }
        public static boolean isMerge() { return merge; }
        public static boolean isScan4z() { return scan4z;}
        public static boolean isEan_4z() { return ean_4z; }

        public static void init(){
            extend = true;
            merge = false;
            scan4z = false;
            ean_4z = false;
        }
    }
}
