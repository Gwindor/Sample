package com.serhiisolodilov.io.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.serhiisolodilov.db.model.ShopEntry;
import com.serhiisolodilov.io.model.shop.Location;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class Shop {
    @SerializedName("id")
    private long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("website")
    private String mWebsite;

    public Location getLocation() {
        return mLocation;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getWebsite() {
        return mWebsite;
    }

    @SerializedName("location")
    private Location mLocation;

    public Shop() {

    }

    public Shop(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        mId = cursor.getLong(cursor.getColumnIndex(ShopEntry._ID));
        mName = cursor.getString(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_NAME));
        mAddress = cursor.getString(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_ADDRESS));
        mPhone = cursor.getString(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_PHONE));
        mWebsite = cursor.getString(cursor.getColumnIndex(ShopEntry.COLUMN_NAME_SHOP_WEBSITE));
        mLocation = new Location(cursor);
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopEntry._ID, mId);
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_NAME, mName);
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_ADDRESS, mAddress);
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_PHONE, mPhone);
        contentValues.put(ShopEntry.COLUMN_NAME_SHOP_WEBSITE, mWebsite);
        contentValues.putAll(mLocation.toContentValues());
        return contentValues;
    }
}
