package com.kimboofactory.iconvert.net;


import lombok.Builder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

@Builder(builderClassName = "Builder")
public class KFYRequest {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    private String endpoint;
    private RequestBody requestBody;
    private String baseUrl;
    private String method;

    public KFYRequest() {
    }

    public void make(Callback callback) {
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
        return new Request.Builder()
                .url(baseUrl + endpoint)
                .method(method, requestBody)
                .build();
    }


}
