package com.serhiisolodilov.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.serhiisolodilov.io.IOHelper;

public class MyIntentService extends IntentService {
    private static final String ACTION_SHOPS = "com.serhiisolodilov.service.action.SHOPS";
    private static final String ACTION_INSTRUMENTS = "com.serhiisolodilov.service.action.INSTRUMENTS";

    private static final String EXTRA_SHOP_ID = "com.serhiisolodilov.service.extra.SHOP_ID";

    public static void startActionShop(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_SHOPS);
        context.startService(intent);
    }

    public static void startActionInstruments(Context context, long shopId) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INSTRUMENTS);
        intent.putExtra(EXTRA_SHOP_ID, shopId);
        context.startService(intent);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOPS.equals(action)) {
                handleActionShops();
            } else if (ACTION_INSTRUMENTS.equals(action)) {
                final long shopId = intent.getLongExtra(EXTRA_SHOP_ID, -1);
                if (shopId >= 0) {
                    handleActionInstruments(shopId);
                }
            }
        }
    }

    private void handleActionShops() {
        IOHelper.downloadShops(this);
    }

    private void handleActionInstruments(long shopId) {
        IOHelper.downloadInstruments(this, shopId);
    }
}
