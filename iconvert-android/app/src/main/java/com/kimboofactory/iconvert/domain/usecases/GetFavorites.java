package com.kimboofactory.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;

import java.util.List;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetFavorites extends UseCase<QueryValue> {

    public GetFavorites(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final List<FavoriteEntity> favoriteEntities = getRepository().getFavorites();
        getUseCaseCallback().onResult(new Result(favoriteEntities, null));
    }
}
