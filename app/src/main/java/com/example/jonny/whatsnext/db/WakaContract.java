package com.example.jonny.whatsnext.db;

import android.provider.BaseColumns;

public class WakaContract {
    public static final String DB_NAME = "com.example.jonny.whatsnext.db";
    public static final int DB_VERSION = 4;

    public class WakaEntry implements BaseColumns {
        public static final String TABLE = "wakas";

        public static final String COL_WAKA_TITLE = "title";
        public static final String COL_WAKA_DESCRIPTION = "description";
        public static final String COL_WAKA_VALUE = "value";
        public static final String COL_WAKA_COST = "cost";
        public static final String COL_WAKA_TIME = "time";
        public static final String COL_WAKA_COMPLETED_ON = "completed_on";
    }
}