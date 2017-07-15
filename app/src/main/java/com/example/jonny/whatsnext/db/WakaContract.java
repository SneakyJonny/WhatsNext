package com.example.jonny.whatsnext.db;

import android.provider.BaseColumns;

public class WakaContract {
    public static final String DB_NAME = "com.example.jonny.whatsnext.db";
    public static final int DB_VERSION = 1;

    public class WakaEntry implements BaseColumns {
        public static final String TABLE = "wakas";

        public static final String COL_WAKA_TITLE = "title";
    }
}