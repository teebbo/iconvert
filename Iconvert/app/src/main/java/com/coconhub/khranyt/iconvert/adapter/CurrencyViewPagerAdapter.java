package com.coconhub.khranyt.iconvert.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.coconhub.khranyt.iconvert.R;
import com.coconhub.khranyt.iconvert.fragment.ConvertTabFragment;
import com.coconhub.khranyt.iconvert.fragment.CurrenciesTabFragment;
import com.coconhub.khranyt.iconvert.fragment.HomeFragment;


/**
 * Created by khranyt on 24/10/15.
 */
public class CurrencyViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int numOfTabs; // store the number of tabs
    private String pageTitle;

    public CurrencyViewPagerAdapter(final Context context, final FragmentManager fm, int numOfTabs){
        super(fm);
        this.context = context;
        this.numOfTabs = numOfTabs;
    }

    /*
        Return the fragment, within the position "position"
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ConvertTabFragment();
                break;
            case 2:
                fragment = new CurrenciesTabFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        return fragment;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position){
            case 0:
                setPageTitle(context.getString(R.string.home_tab_title));
                break;
            case 1:
                setPageTitle(context.getString(R.string.convert_tab_title));
                break;
            case 2:
                setPageTitle(context.getString(R.string.currencies_tab_title));
                break;
            default:
                setPageTitle(context.getString(R.string.home_tab_title));
                break;
        }
        return this.pageTitle;
    }

    public void setPageTitle(final String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}