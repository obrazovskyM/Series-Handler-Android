package com.vvpgroup.serieshandler;

public class TestConfig {
    private TestConfig() {}
    public static final String SERIES_NORMAL = "•AAAA0001•\n•AAAA0010•\n•BBBB0001•\n•BBBB0010•";
    public static final String SERIES_NORMAL_WITH_EMPTY = "\n\n•AAAA0001•\n\n•AAAA0010•••\n•BBBB0001•\n•BBBB0010•";
    public static final String SERIES_NORMAL_WITH_EXTRA_POINT = "•AAAA0001••\n•AAAA0010•\n•BBBB0001••\n•BBBB0010•";
    public static final String SERIES_NORMAL_SHORT = "•AAAA0001•\n•AAAA0003•\n•BBBB0001•\n•BBBB0003•";

    public static final String SERIES_SINGLE = "•XXXX0001•";
    public static final String SERIES_SAME = "AAAA0001\nAAAA0001";
    public static final String SERIES_SAME_TWO_PAIR = "AAAA0001\nAAAA0001\nBBBB0001\nBBBB0001";

    public static final String SERIES_WRONG_FORMAT_WORD = "AAAA0001\nBBBB0010";
    public static final String SERIES_WRONG_FORMAT_NUM = "AAAA0005\nAAAA10000";
    public static final String SERIES_WRONG_FORMAT = "AAAA0001\nAAAA000B";

}
