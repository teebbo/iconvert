package com.kimboofactory.iconvert.adapter;


import com.kimboofactory.iconvert.dto.Page;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


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


    public void clear() {
        mPages.clear();
        notifyDataSetChanged();
    }


    public void updateDataSet(List<Page> newDataSet) {
        mPages.addAll(newDataSet);
        notifyDataSetChanged();
    }
}