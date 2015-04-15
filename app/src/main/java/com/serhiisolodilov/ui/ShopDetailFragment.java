package com.serhiisolodilov.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serhiisolodilov.R;
import com.serhiisolodilov.db.contentproviders.ShopsContentProvider;
import com.serhiisolodilov.io.model.Shop;
import com.serhiisolodilov.service.MyIntentService;

public class ShopDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = ShopDetailFragment.class.getSimpleName();
    public static final String ARG_ITEM_ID = "item_id";
    private long mId;

    private TextView mName;
    private TextView mAddress;
    private TextView mPhone;
    private TextView mWebsite;
    private TextView mLocation;

    public ShopDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mId = getArguments().getLong(ARG_ITEM_ID);
            MyIntentService.startActionInstruments(getActivity(), mId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_detail, container, false);
        mName = (TextView) rootView.findViewById(R.id.name);
        mAddress = (TextView) rootView.findViewById(R.id.address);
        mPhone = (TextView) rootView.findViewById(R.id.phone);
        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mPhone.getText())) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:+" + mPhone.getText().toString().trim()));
                    startActivity(callIntent);
                }
            }
        });
        mWebsite = (TextView) rootView.findViewById(R.id.website);
        mWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mWebsite.getText())) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((mWebsite.getText().toString())));
                    startActivity(browserIntent);
                }
            }
        });
        mLocation = (TextView) rootView.findViewById(R.id.location);
        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = ContentUris.withAppendedId(ShopsContentProvider.SHOPS_CONTENT_URI, mId);
        return new CursorLoader(getActivity(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        Shop shop = new Shop(cursor);
        init(shop);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void init(Shop shop) {
        if (shop == null) {
            Log.i(TAG, "init with shop == null");
            return;
        }
        mName.setText(shop.getName());
        mAddress.setText(shop.getAddress());
        mPhone.setText(shop.getPhone());
        mWebsite.setText(shop.getWebsite());
        mLocation.setText("Lat:" + shop.getLocation().getLatitude() + ", Lon:" + shop.getLocation().getLongitude());
    }
}
