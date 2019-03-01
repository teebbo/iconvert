package com.kimboofactory.iconvert.ui.main;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class ListViewListener implements
        ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    public ListViewListener() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
