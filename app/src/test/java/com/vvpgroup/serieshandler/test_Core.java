package com.vvpgroup.serieshandler;

import org.junit.Test;

public class test_Core {
    @Test
    public void test_formatin(){
        String input = TestConfig.SERIES_NORMAL_SHORT;
        String[] check = Core.format_in(input);
        String[] check_new = Core.format_in(input);
    }
    @Test
    public void test_series_extend() {
        String input = TestConfig.SERIES_WRONG_FORMAT;
        String result = Core.series_extender(input);
        result = Core.series_extender(result);
    }
}
