package com.kimboofactory.iconvert.net;


import com.kimboofactory.iconvert.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class KFYRequest {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    private String endpoint;
    private RequestBody requestBody;
    private String baseUrl;
    private String method;

    public KFYRequest() {
    }

    public void execute(Callback callback) {
        final Call call = HttpClient.getClient().newCall(getRequest());
        call.enqueue(callback);
    }

    private Request getRequest() {
        if (POST_METHOD.equals(method) && requestBody == null) {
            throw new RuntimeException("POST request may have a body.");
        }

        if (method == null) {
            method = GET_METHOD;
        }

        if (endpoint == null) {
            endpoint = Constant.EMPTY_STRING;
        }

        final HttpUrl url = HttpUrl.parse(baseUrl + endpoint);
        return new Request.Builder()
                .url(url.toString())
                .method(method, requestBody)
                .build();
    }


}
