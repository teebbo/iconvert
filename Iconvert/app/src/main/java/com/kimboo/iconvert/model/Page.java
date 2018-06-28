package com.kimboo.iconvert.model;

import android.support.v4.app.Fragment;

/**
 * Created by gnemoka on 28/06/2018.
 */
public class Page {

    private String title;
    private Fragment fragment;

    public Page() {}

    public Page(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                '}';
    }
}
