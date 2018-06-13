package com.multimeleon.pjapples.unittest_refactor_exerise.models.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit client = null;
    public static Retrofit getClient(String baseURL) {
        if (client == null) {
            client = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return client;
    }
}
