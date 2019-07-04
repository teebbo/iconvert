package com.aleengo.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;
import com.aleengo.iconvert.domain.model.FavoriteEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SaveFavorites extends UseCase<QueryValue> {

    @Inject
    public SaveFavorites(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final List<FavoriteEntity> favorites = (List<FavoriteEntity>) getQueryValue().getValue();
        getRepository().addAllFavorites(favorites, this::onAdded);
    }

    private void onAdded(Response response) {
        getUseCaseCallback().onResult(new Result(response.getValue(), null));
    }

    public static class QueryVal extends QueryValue<List<FavoriteEntity>> {

        public QueryVal(List<FavoriteEntity> value) {
            super(value);
        }
    }
}
