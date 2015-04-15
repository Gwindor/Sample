package com.serhiisolodilov.db.model;

import android.provider.BaseColumns;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class InstrumentEntry implements BaseColumns {
    public static final String TABLE_NAME = "instrument";
    public static final String COLUMN_NAME_INSTRUMENT_ID = "id";
    public static final String COLUMN_NAME_INSTRUMENT_BRAND = "brand";
    public static final String COLUMN_NAME_INSTRUMENT_SHOP_ID = "shop_id";
    public static final String COLUMN_NAME_INSTRUMENT_MODEL = "model";
    public static final String COLUMN_NAME_INSTRUMENT_IMAGE_URL = "imageUrl";
    public static final String COLUMN_NAME_INSTRUMENT_TYPE = "type";
    public static final String COLUMN_NAME_INSTRUMENT_PRICE = "price";
    public static final String COLUMN_NAME_INSTRUMENT_HASH = "hash";
    public static final String COLUMN_NAME_INSTRUMENT_QUANTITY = "quantity";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_INSTRUMENT_ID + " INTEGER," +
            COLUMN_NAME_INSTRUMENT_SHOP_ID + " INTEGER," +
            COLUMN_NAME_INSTRUMENT_BRAND + " TEXT," +
            COLUMN_NAME_INSTRUMENT_MODEL + " TEXT," +
            COLUMN_NAME_INSTRUMENT_IMAGE_URL + " TEXT," +
            COLUMN_NAME_INSTRUMENT_TYPE + " TEXT," +
            COLUMN_NAME_INSTRUMENT_PRICE + " INTEGER," +
            COLUMN_NAME_INSTRUMENT_HASH + " TEXT UNIQUE," +
            COLUMN_NAME_INSTRUMENT_QUANTITY + " INTEGER" + " )";
}
