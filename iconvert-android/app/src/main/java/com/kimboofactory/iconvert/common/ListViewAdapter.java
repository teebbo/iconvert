package com.kimboofactory.iconvert.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kimboofactory.iconvert.model.Model;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class ListViewAdapter<MODEL extends Model, VH extends ListViewAdapter.ViewHolder> extends BaseAdapter {

    @Getter
    private Context context;
    @Getter
    private List<MODEL> dataSet;
    @Getter
    private final List<MODEL> originalList;

    private boolean isOriginalListEmpty = true;

    public ListViewAdapter(Context context, List<MODEL> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
        originalList = new LinkedList<>();
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
    public abstract VH onCreateViewHolder(final View view);

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *         item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public abstract void onBindViewHolder(final VH holder, final int position);

    public void clear() {
        if (dataSet.size() > 0) {
            dataSet.clear();
            notifyDataSetChanged();
        }
    }

    public void updateDataSet(final List<MODEL> newDataSet) {

        if (newDataSet.size() > 0) {

            if (isOriginalListEmpty) {
                originalList.addAll(newDataSet);
                isOriginalListEmpty = false;
            }

            dataSet.addAll(newDataSet);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return dataSet != null ? dataSet.size() : -1;
    }

    @Override
    public Object getItem(int position) {
        return this.dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.dataSet.get(position).hashCode();
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
