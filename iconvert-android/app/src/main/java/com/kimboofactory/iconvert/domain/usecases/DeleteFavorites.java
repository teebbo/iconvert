package com.kimboofactory.iconvert.domain.usecases;

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
public class DeleteFavorites extends UseCase<QueryValue> {

    public DeleteFavorites(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        getRepository().removeFavorites();
        //getUseCaseCallback().onResult(new Result(favoriteEntities, null));
    }
}
