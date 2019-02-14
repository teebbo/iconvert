package com.kimboofactory.iconvert.persistence.repository;

import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.dto.Result;
import com.kimboofactory.iconvert.persistence.local.LocalDataSource;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.Optional;

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
      if (!localDataSource.isEmpty()) {
          localDataSource.getCurrencies(null);
      } else {
          remoteDataSource.getCurrencies((error, currencies) -> {
              final Result<String> result =
                      new Result<>(Optional.ofNullable(error), Optional.ofNullable(currencies));

              callback.onDataLoaded(result);

              if (error == null) {
                  localDataSource.saveAll(new ArrayList<>());
              }
          });
      }
    }
}
