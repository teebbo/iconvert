package com.aleengo.iconvert.domain.usecases;

import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class DeleteFavorites extends UseCase<QueryValue> {

    @Inject
    public DeleteFavorites(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        getRepository().removeFavorites();
        //getUseCaseCallback().onResult(new Result(favoriteEntities, null));
    }
}
