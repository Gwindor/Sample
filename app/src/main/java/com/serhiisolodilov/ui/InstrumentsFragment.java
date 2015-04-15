package com.serhiisolodilov.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serhiisolodilov.R;
import com.serhiisolodilov.db.contentproviders.ShopsContentProvider;
import com.serhiisolodilov.db.model.InstrumentEntry;

public class InstrumentsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = InstrumentsFragment.class.getSimpleName();
    public static final String ARG_ITEM_ID = "item_id";

    private TextView mTotalInstruments;

    private long mId;
    private SimpleCursorAdapter mAdapter;

    public InstrumentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mId = getArguments().getLong(ARG_ITEM_ID);
        }
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2,
                null,
                new String[]{InstrumentEntry.COLUMN_NAME_INSTRUMENT_MODEL, InstrumentEntry.COLUMN_NAME_INSTRUMENT_PRICE},
                new int[]{android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruments, container, false);
        mTotalInstruments = (TextView) rootView.findViewById(R.id.total);
        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = ShopsContentProvider.INSTRUMENTS_CONTENT_URI;
        return new CursorLoader(getActivity(),
                uri,
                null,
                InstrumentEntry.COLUMN_NAME_INSTRUMENT_SHOP_ID + "=?",
                new String[]{String.valueOf(mId)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
