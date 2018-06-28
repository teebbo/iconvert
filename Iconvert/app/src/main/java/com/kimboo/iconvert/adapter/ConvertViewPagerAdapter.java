package com.kimboo.iconvert.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kimboo.iconvert.model.Page;

import java.util.List;


/**
 * Created by khranyt on 24/10/15.
 */
public class ConvertViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Page> mPages;

    public ConvertViewPagerAdapter(final Context context, final FragmentManager fm, List<Page> pages){
        super(fm);
        mContext = context;
        mPages = pages;
    }

    @Override
    public Fragment getItem(int position) {
         if(mPages == null) {
             return null;
         }
         return mPages.get(position).getFragment();
    }

    @Override
    public String getPageTitle(int position) {
        if(mPages == null) {
            return null;
        }
        return mPages.get(position).getTitle();
    }

    @Override
    public int getCount() {
        if(mPages == null) {
            return 0;
        }
        return mPages.size();
    }
}