package com.kimboofactory.iconvert.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kimboofactory.iconvert.model.Page;

import java.util.List;


/**
 * 24/10/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Page> mPages;

    public ViewPagerAdapter(final FragmentManager fm, List<Page> pages){
        super(fm);
        mPages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return mPages.get(position).getFragment();
    }

    @Override
    public String getPageTitle(int position) {
        return mPages.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    public void swapData(List<Page> pages) {
        mPages.clear();

        mPages.addAll(pages);
        notifyDataSetChanged();
    }
}