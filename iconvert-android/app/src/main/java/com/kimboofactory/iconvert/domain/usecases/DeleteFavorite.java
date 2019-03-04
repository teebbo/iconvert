package com.kimboofactory.iconvert.domain.usecases;

import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class DeleteFavorite extends UseCase<QueryValue> {

    @Inject
    public DeleteFavorite(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final FavoriteEntity favorite = (FavoriteEntity) getQueryValue().getValue();
        getRepository().removeFavorite(favorite);
        //getUseCaseCallback().onResult(new Result(favoriteEntities, null));
    }

    public static class QueryVal extends QueryValue<FavoriteEntity> {
        public QueryVal(FavoriteEntity value) {
            super(value);
        }
    }
}
