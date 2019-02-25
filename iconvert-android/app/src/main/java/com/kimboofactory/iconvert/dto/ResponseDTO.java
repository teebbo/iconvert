package com.kimboofactory.iconvert.dto;

import com.aleengo.peach.toolbox.commons.model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Data
@AllArgsConstructor
public class ResponseDTO {
    private String requestId;
    private Response response;
}
