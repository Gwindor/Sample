package com.serhiisolodilov.db.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.serhiisolodilov.db.ShopsOpenHelper;
import com.serhiisolodilov.db.model.InstrumentEntry;
import com.serhiisolodilov.db.model.ShopEntry;

import java.util.ArrayList;
import java.util.List;

public class ShopsContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.serhiisolodilov.TestApp.provider";

    public static final Uri SHOPS_CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY + "/" + ShopEntry.TABLE_NAME);
    public static final Uri INSTRUMENTS_CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY + "/" + InstrumentEntry.TABLE_NAME);

    private static final int SHOP = 101;
    private static final int SHOP_ID = 102;

    private static final int INSTRUMENT = 201;
    private static final int INSTRUMENT_ID = 202;

    private final UriMatcher mUriMatcher;

    private ShopsOpenHelper mOpenHelper;

    public ShopsContentProvider() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, ShopEntry.TABLE_NAME, SHOP);
        mUriMatcher.addURI(AUTHORITY, ShopEntry.TABLE_NAME + "/#", SHOP_ID);
        mUriMatcher.addURI(AUTHORITY, InstrumentEntry.TABLE_NAME, INSTRUMENT);
        mUriMatcher.addURI(AUTHORITY, InstrumentEntry.TABLE_NAME + "/#", INSTRUMENT_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        long rowId;
        switch (mUriMatcher.match(uri)) {
            case SHOP:
                rowId = mOpenHelper.getWritableDatabase().insertWithOnConflict(ShopEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                returnUri = ContentUris.withAppendedId(SHOPS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(returnUri, null);
                break;
            case INSTRUMENT:
                rowId = mOpenHelper.getWritableDatabase().insertWithOnConflict(InstrumentEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                returnUri = ContentUris.withAppendedId(INSTRUMENTS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(returnUri, null);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        return returnUri;
    }

    @Override
    public synchronized int bulkInsert(Uri uri, ContentValues[] values) {
        Uri contentUri;
        String table;
        switch (mUriMatcher.match(uri)) {
            case SHOP:
                contentUri = SHOPS_CONTENT_URI;
                table = ShopEntry.TABLE_NAME;
                break;
            case INSTRUMENT:
                contentUri = INSTRUMENTS_CONTENT_URI;
                table = InstrumentEntry.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);

        }
        SQLiteDatabase sqLiteDatabase = mOpenHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            long rowId;
            List<Uri> uris = new ArrayList<>();
            for (ContentValues contentValues : values) {
                rowId = sqLiteDatabase.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                uris.add(ContentUris.withAppendedId(contentUri, rowId));
            }
            sqLiteDatabase.setTransactionSuccessful();
            for (Uri uriToNotify : uris) {
                getContext().getContentResolver().notifyChange(uriToNotify, null);
            }
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return values.length;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = ShopsOpenHelper.getHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String table;
        switch (mUriMatcher.match(uri)) {
            case SHOP:
                table = ShopEntry.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ShopEntry._ID + " ASC";
                }
                break;
            case SHOP_ID:
                table = ShopEntry.TABLE_NAME;
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = ShopEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + ShopEntry._ID + " = " + id;
                }
                break;
            case INSTRUMENT:
                table = InstrumentEntry.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = InstrumentEntry._ID + " ASC";
                }
                break;
            case INSTRUMENT_ID:
                table = InstrumentEntry.TABLE_NAME;
                String instrumentId = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = InstrumentEntry._ID + " = " + instrumentId;
                } else {
                    selection = selection + " AND " + InstrumentEntry._ID + " = " + instrumentId;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = mOpenHelper.getReadableDatabase().query(table, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public SQLiteDatabase getDbHandle() {
        return mOpenHelper.getReadableDatabase();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
