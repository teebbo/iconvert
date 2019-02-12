package com.kimboofactory.iconvert.net;

import com.kimboofactory.iconvert.util.SingletonUtil;

import okhttp3.OkHttpClient;

/**
 *
 */
public class HttpClient {

    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        client = SingletonUtil.getInstance(OkHttpClient.class, client);
        return client;
    }
}
