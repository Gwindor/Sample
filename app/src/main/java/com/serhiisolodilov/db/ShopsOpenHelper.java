package com.serhiisolodilov.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serhiisolodilov.db.model.InstrumentEntry;
import com.serhiisolodilov.db.model.ShopEntry;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class ShopsOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 39;
    private static final String SHOPS_DB_NAME = "shops";

    private static ShopsOpenHelper instance;

    public static synchronized ShopsOpenHelper getHelper(Context context) {
        if (instance == null) {
            instance = new ShopsOpenHelper(context);
        }
        return instance;
    }

    private ShopsOpenHelper(Context context) {
        super(context, SHOPS_DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InstrumentEntry.SQL_CREATE_TABLE);
        db.execSQL(ShopEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InstrumentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ShopEntry.TABLE_NAME);
        onCreate(db);
    }

}
