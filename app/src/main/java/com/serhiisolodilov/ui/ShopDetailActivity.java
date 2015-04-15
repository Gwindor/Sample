package com.serhiisolodilov.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.serhiisolodilov.R;

public class ShopDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            final long id = getIntent().getLongExtra(ShopDetailFragment.ARG_ITEM_ID, -1);
            arguments.putLong(ShopDetailFragment.ARG_ITEM_ID, id);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            ShopDetailFragment shopDetailFragment = new ShopDetailFragment();
            shopDetailFragment.setArguments(arguments);
            fragmentTransaction.add(R.id.shopDeteils, shopDetailFragment);

            InstrumentsFragment instrumentsFragment = new InstrumentsFragment();
            instrumentsFragment.setArguments(arguments);
            fragmentTransaction.add(R.id.instruments, instrumentsFragment);

            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ShopListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
