package com.serhiisolodilov.io.model;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;
import com.serhiisolodilov.db.model.InstrumentEntry;
import com.serhiisolodilov.io.model.instrumentroot.Instrument;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class InstrumentRoot {
    @SerializedName("instrument")
    private Instrument mInstrument;
    @SerializedName("quantity")
    private int mQuantity;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.putAll(mInstrument.toContentValues());
        contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_QUANTITY, mQuantity);
        return contentValues;
    }

    public Instrument getInstrument() {
        return mInstrument;
    }

    public int getQuantity() {
        return mQuantity;
    }
}
