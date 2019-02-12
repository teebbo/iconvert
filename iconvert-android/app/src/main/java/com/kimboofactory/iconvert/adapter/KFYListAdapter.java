package com.kimboofactory.iconvert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class KFYListAdapter<T, VH extends KFYListAdapter.ViewHolder> extends BaseAdapter {

    @Getter
    private Context context;
    @Getter
    private List<T> dataSet;

    public KFYListAdapter(Context context, List<T> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    /**
     *
     * @return
     */
    public abstract int getLayoutResId();

    /**
     *
     * @param view
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public abstract VH onCreateViewHolder(View view);

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *         item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public abstract void onBindViewHolder(VH holder, int position);

    public void clear() {
        if (dataSet.size() > 0) {
            dataSet.clear();
            notifyDataSetChanged();
        }
    }

    public void updateDataSet(List<T> newDataSet) {
        final List<T> oldDataSet = new LinkedList<>();

        // store the old values the dataset in an
        // temporary list
        copy(this.dataSet, oldDataSet);

        clear();
        copy(newDataSet, this.dataSet);
        copy(oldDataSet, this.dataSet);
        notifyDataSetChanged();
    }

    private void copy(final List<T> src, final List<T> dst) {
        if (src.size() > 0) {
           src.forEach(e -> dst.add(dst.size(), e));
        }
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VH holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(getLayoutResId(), parent, false);

            holder = onCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public static abstract class ViewHolder {
        private View itemView;
        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }
    }
}
