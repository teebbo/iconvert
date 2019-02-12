package com.kimboofactory.iconvert.asyntask;

import android.content.Context;
import android.os.AsyncTask;

import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDao;
import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.kimboofactory.iconvert.model.RateData;


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
        final RateData tauxChange = tauxChangeDao.getByCode(code);

        return Double.valueOf(tauxChange.getValue());
    }

    @Override
    protected void onPostExecute(Double tauxChange) {
        super.onPostExecute(tauxChange);
    }
}
