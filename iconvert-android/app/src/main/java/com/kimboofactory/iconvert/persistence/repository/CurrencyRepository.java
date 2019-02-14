package com.kimboofactory.iconvert.persistence.repository;

import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.local.LocalDataSource;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;

public class CurrencyRepository implements Repository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public CurrencyRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void get(String code, GetCallback callback) {
        /*localDataSource.getCurrencyByCode(code, new DataSource.GetCurrencyCallback(){
            @Override
            public void currencyLoaded() {

            }
        });*/

        remoteDataSource.getCurrencyByCode(code, callback::onLoaded);
    }

    @Override
    public void getAll() {

    }
}
