package com.lemonstack.iconvert.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.fragment.ConversionFragment;
import com.lemonstack.iconvert.fragment.DeviseFragment;


/**
 * Created by khranyt on 24/10/15.
 */
public class IConvertViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int numOfTabs;
    private String pageTitle;

    public IConvertViewPagerAdapter(final Context context, final FragmentManager fm, int numOfTabs){
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
        Class fragmentClass;

        switch (position){
            case 0:
                fragmentClass = ConversionFragment.class;
                break;
            case 1:
                fragmentClass = DeviseFragment.class;
                break;
            default:
                fragmentClass = ConversionFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position){
            case 0:
                setPageTitle(context.getString(R.string.convert_tab_title));
                break;
            case 1:
                setPageTitle(context.getString(R.string.devise_tab_title));
                break;
            default:
                setPageTitle(context.getString(R.string.convert_tab_title));
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