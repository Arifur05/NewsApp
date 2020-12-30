package com.arifur.newsapp.requests;

import androidx.lifecycle.LiveData;

import com.arifur.newsapp.requests.response.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author : Arif
 * @date : 29-December-2020 07:07 PM
 * @package : com.arifur.newsapp.Requests
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public interface NewsApi {

    @GET("top-headlines")
    LiveData<NewsResponse> getWorldNews(
            @Query("apikey") String api,
            @Query("q") String query,
            @Query("language") String language
    );
}
