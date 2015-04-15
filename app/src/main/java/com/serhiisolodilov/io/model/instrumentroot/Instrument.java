package com.serhiisolodilov.io.model.instrumentroot;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;
import com.serhiisolodilov.db.model.InstrumentEntry;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class Instrument {
    @SerializedName("id")
    private long mId;
    @SerializedName("brand")
    private String mBrand;
    @SerializedName("model")
    private String mModel;
    @SerializedName("imageUrl")
    private String mImageUrl;
    @SerializedName("type")
    private String mType;
    @SerializedName("price")
    private double mPrice;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_ID, mId);
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_BRAND, mBrand);
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_MODEL, mModel);
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_IMAGE_URL, mImageUrl);
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_TYPE, mType);
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_PRICE, mPrice);
        return contentValues;
    }

    public long getId() {
        return mId;
    }

    public String getBrand() {
        return mBrand;
    }

    public String getModel() {
        return mModel;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getType() {
        return mType;
    }

    public double getPrice() {
        return mPrice;
    }
}
