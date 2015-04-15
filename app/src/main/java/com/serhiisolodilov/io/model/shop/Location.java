package com.serhiisolodilov.io.model.shop;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.serhiisolodilov.db.model.ShopEntry;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class Location {
    @SerializedName("latitude")
    private int mLatitude;
    @SerializedName("longitude")
    private int mLongitude;

    public Location() {
    }

    public Location(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        mLatitude = cursor.getInt(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_LOCATION_LATITUDE));
        mLongitude = cursor.getInt(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_LOCATION_LONGITUDE));
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_LOCATION_LATITUDE, mLatitude);
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_LOCATION_LONGITUDE, mLongitude);
        return contentValues;
    }

    public int getLatitude() {
        return mLatitude;
    }

    public int getLongitude() {
        return mLongitude;
    }
}
