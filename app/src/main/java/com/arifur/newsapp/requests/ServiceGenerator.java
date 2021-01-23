package com.arifur.newsapp.requests;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.arifur.newsapp.util.Constants.BASE_URL;
import static com.arifur.newsapp.util.Constants.CONNECTION_TIMEOUT;
import static com.arifur.newsapp.util.Constants.READ_TIMEOUT;
import static com.arifur.newsapp.util.Constants.WRITE_TIMEOUT;

/**
 * @author : Arif
 * @date : 29-December-2020 06:49 PM
 * @package : com.arifur.newsapp.Requests
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class ServiceGenerator {
    private static OkHttpClient client = new OkHttpClient.Builder()

            // establish connection to server
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte read from the server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte sent to server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

            .retryOnConnectionFailure(false)

            .build();

    private static Retrofit.Builder retrofitBuilder= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit sRetrofit=retrofitBuilder.build();
    private static NewsApi mNewsApi= sRetrofit.create(NewsApi.class);
    public static NewsApi getNewsApi(){
        return mNewsApi;
    }
}
