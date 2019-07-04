package com.aleengo.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;
import com.aleengo.iconvert.domain.model.FavoriteEntity;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SaveFavorite extends UseCase<QueryValue> {

    @Inject
    public SaveFavorite(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final FavoriteEntity favorite = (FavoriteEntity) getQueryValue().getValue();
        getRepository().addFavorite(favorite, this::onAdded);
    }

    private void onAdded(Response response) {
        getUseCaseCallback().onResult(new Result(response.getValue(), null));
    }

    public static class QueryVal extends QueryValue<FavoriteEntity> {
        public QueryVal(FavoriteEntity value) {
            super(value);
        }
    }
}
