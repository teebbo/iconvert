package com.kimboofactory.iconvert.adapter;

import android.content.Context;

import com.kimboofactory.iconvert.dto.Page;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


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
        return mPages.get(position).toString();
    }

    @Override
    public int getCount() {
        if(mPages == null) {
            return 0;
        }
        return mPages.size();
    }
}