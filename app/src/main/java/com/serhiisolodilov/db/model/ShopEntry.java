package com.serhiisolodilov.db.model;

import android.provider.BaseColumns;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class ShopEntry implements BaseColumns {
    public static final String TABLE_NAME = "shops";
    public static final String COLUMN_NAME_SHOP_NAME = "name";
    public static final String COLUMN_NAME_SHOP_ADDRESS = "address";
    public static final String COLUMN_NAME_SHOP_PHONE = "phone";
    public static final String COLUMN_NAME_SHOP_WEBSITE = "website";
    public static final String COLUMN_NAME_SHOP_LOCATION_LATITUDE = "latitude";
    public static final String COLUMN_NAME_SHOP_LOCATION_LONGITUDE = "longitude";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_SHOP_NAME + " TEXT," +
            COLUMN_NAME_SHOP_ADDRESS + " TEXT," +
            COLUMN_NAME_SHOP_PHONE + " TEXT," +
            COLUMN_NAME_SHOP_WEBSITE + " TEXT," +
            COLUMN_NAME_SHOP_LOCATION_LATITUDE + " TEXT," +
            COLUMN_NAME_SHOP_LOCATION_LONGITUDE + " TEXT" + " )";
}
