package com.serhiisolodilov.io;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.serhiisolodilov.db.contentproviders.ShopsContentProvider;
import com.serhiisolodilov.db.model.InstrumentEntry;
import com.serhiisolodilov.io.model.InstrumentRoot;
import com.serhiisolodilov.io.model.Shop;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serhiisolodilov on 7/9/14.
 */
public class IOHelper {
    private static final String TAG = IOHelper.class.getSimpleName();

    private static final String SHOPS_URL = "http://aschoolapi.appspot.com/stores/";
    private static final String SHOP_URL = "http://aschoolapi.appspot.com/stores/%d/instruments";

    private static final int NUMBER_FOR_STORE_SHOPS = 5;
    private static final int NUMBER_FOR_STORE_INSTRUMENTS = 5;

    private static final AndroidHttpClient sAndroidHttpClient = AndroidHttpClient.newInstance(null);
    private static final Gson sGSON = new Gson();


    public static void downloadShops(Context context) {
        HttpGet httpGet = new HttpGet(SHOPS_URL);
        try {
            HttpResponse httpResponse = sAndroidHttpClient.execute(httpGet);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    parseAndSaveShops(context, inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage() == null ? "null" : e.getMessage());
        }
    }

    private static void parseAndSaveShops(Context context, InputStream inputStream) throws IOException {
        final JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        final List<Shop> shops = new ArrayList<>();
        try {
            reader.beginArray();
            Shop shop;
            ContentResolver contentResolver = context.getContentResolver();
            while (reader.hasNext()) {
                shop = sGSON.fromJson(reader, Shop.class);
                shops.add(shop);
                if (shops.size() >= NUMBER_FOR_STORE_SHOPS) {
                    saveShops(contentResolver, shops);
                }
            }
            if (shops.size() > 0) {
                saveShops(contentResolver, shops);
            }
        } finally {
            reader.close();
        }
    }

    private static void saveShops(ContentResolver contentResolver, List<Shop> shops) {
        ContentValues[] contentValueses = new ContentValues[shops.size()];
        int i = 0;
        for (Shop shop : shops) {
            contentValueses[i++] = shop.toContentValues();
        }
        contentResolver.bulkInsert(ShopsContentProvider.SHOPS_CONTENT_URI, contentValueses);
    }

    public static void downloadInstruments(Context context, long shopId) {
        HttpGet httpGet = new HttpGet(String.format(SHOP_URL, shopId));
        HttpResponse httpResponse;
        try {
            httpResponse = sAndroidHttpClient.execute(httpGet);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    parseAndSaveInstruments(context, shopId, inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage() == null ? "null" : e.getMessage());
        } finally {
            httpGet.abort();
        }
    }

    private static void parseAndSaveInstruments(Context context, long shopId, InputStream inputStream) throws IOException {
        final JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            final List<InstrumentRoot> instruments = new ArrayList<InstrumentRoot>();
            InstrumentRoot instrument;
            ContentResolver contentResolver = context.getContentResolver();
            while (reader.hasNext()) {
                instrument = sGSON.fromJson(reader, InstrumentRoot.class);
                instruments.add(instrument);
                if (instruments.size() >= NUMBER_FOR_STORE_INSTRUMENTS) {
                    saveInstruments(contentResolver, instruments, shopId);
                    instruments.clear();
                }
            }
            if (instruments.size() > 0) {
                saveInstruments(contentResolver, instruments, shopId);
                instruments.clear();
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    private static void saveInstruments(ContentResolver contentResolver, List<InstrumentRoot> instruments, long shopId) {
        ContentValues[] contentValueses = new ContentValues[instruments.size()];
        int i = 0;
        for (InstrumentRoot instrumentRoot : instruments) {
            ContentValues contentValues = instrumentRoot.toContentValues();
            contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_SHOP_ID, shopId);
            contentValues.put(InstrumentEntry.COLUMN_NAME_INSTRUMENT_HASH, "" + shopId + instrumentRoot.getInstrument().getId());
            contentValueses[i++] = contentValues;
        }
        contentResolver.bulkInsert(ShopsContentProvider.INSTRUMENTS_CONTENT_URI, contentValueses);
    }

}
