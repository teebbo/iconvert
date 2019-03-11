package com.kimboofactory.iconvert.events;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetFavoriteEvent {
    private CurrencyIHM source;
    private List<CurrencyIHM> currencies;
}
