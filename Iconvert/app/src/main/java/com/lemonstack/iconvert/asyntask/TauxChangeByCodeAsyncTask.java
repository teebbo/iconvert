package com.lemonstack.iconvert.asyntask;

import android.content.Context;
import android.os.AsyncTask;

import com.lemonstack.iconvert.dao.tauxchange.TauxChangeDao;
import com.lemonstack.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.lemonstack.iconvert.model.TauxChange;

/**
 * Created by khranyt on 03/04/2017.
 */

public class TauxChangeByCodeAsyncTask extends AsyncTask<String, Void, Double> {

    private Context context;
    private TauxChangeDao tauxChangeDao;

    public TauxChangeByCodeAsyncTask(Context context) {
        this.context = context;
        tauxChangeDao = new TauxChangeDaoImpl(context);
    }

    @Override
    protected Double doInBackground(String... params) {
        final String code = params[0];
        final TauxChange tauxChange = tauxChangeDao.getByCode(code);

        return tauxChange.getTauxChange();
    }

    @Override
    protected void onPostExecute(Double tauxChange) {
        super.onPostExecute(tauxChange);
    }
}
