package com.serhiisolodilov.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.serhiisolodilov.R;

public class ShopListActivity extends FragmentActivity
        implements ShopListFragment.Callbacks {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        if (findViewById(R.id.shopDeteils) != null) {
            mTwoPane = true;
            ((ShopListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.shop_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putLong(ShopDetailFragment.ARG_ITEM_ID, id);
            ShopDetailFragment fragment = new ShopDetailFragment();
            fragment.setArguments(arguments);
            InstrumentsFragment instrumentsFragment = new InstrumentsFragment();
            instrumentsFragment.setArguments(arguments);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopDeteils, fragment);
            fragmentTransaction.replace(R.id.instruments, instrumentsFragment);
            fragmentTransaction.commit();

        } else {
            Intent detailIntent = new Intent(this, ShopDetailActivity.class);
            detailIntent.putExtra(ShopDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
