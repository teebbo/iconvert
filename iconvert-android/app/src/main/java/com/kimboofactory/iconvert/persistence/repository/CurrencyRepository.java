package com.kimboofactory.iconvert.persistence.repository;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.local.LocalDataSource;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;

import java.util.ArrayList;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class CurrencyRepository implements Repository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public CurrencyRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void find(final String query, SearchCallback callback) {
      if (localDataSource.isEmpty()) {
          remoteDataSource.getCurrencies(response -> {
              callback.onDataLoaded(response);
              if (response.getError() == null) {
                  localDataSource.saveAll(new ArrayList<>());
              }
          });
      } else {
          localDataSource.getCurrencies(new DataSource.GetCurrenciesCallback() {
              @Override
              public void currenciesLoaded(Response response) {
                  callback.onDataLoaded(response);
              }
          });
      }
    }
}
