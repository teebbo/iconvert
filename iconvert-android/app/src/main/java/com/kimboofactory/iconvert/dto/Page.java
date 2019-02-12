package com.kimboofactory.iconvert.dto;


import androidx.fragment.app.Fragment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private String title;
    private Fragment fragment;
}
