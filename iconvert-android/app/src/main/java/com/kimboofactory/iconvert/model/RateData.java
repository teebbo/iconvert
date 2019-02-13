package com.kimboofactory.iconvert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by CK_ALEENGO on 29/10/2015..
 * Copyright (c) 2015. All rights reserved.
 */
@ToString(of = {"code", "value"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RateData implements Model {

    private Long id;
    private String code;
    private String value;
}
