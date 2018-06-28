package com.kimboo.iconvert.dao;

import android.database.Cursor;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by khranyt on 31/03/2017.
 */

public interface BaseDao<T> {

    /**
     * Retourne la liste des éléments
     * @return
     */
    public Cursor list();

    /**
     * Sauvegarde la liste des éléments
     * @param list
     * @return
     */
    public long save(List<T> list);

    public void save(JSONObject jsonObject);

    public T getByCode(String code);

    public T createEntity(Cursor c);
}
