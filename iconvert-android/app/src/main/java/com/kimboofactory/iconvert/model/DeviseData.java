package com.kimboofactory.iconvert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by CK_ALEENGO on 31/03/2017.
 * Copyright (c) 2017. All rights reserved.
 */

@ToString(of = {"code", "libelle"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviseData implements Model {
    private Long id;
    private String code;
    private String libelle;
}
