package com.arifur.newsapp.requests;

import androidx.lifecycle.LiveData;

import com.arifur.newsapp.requests.response.ApiResponse;
import com.arifur.newsapp.requests.response.NewsResponse;

import retrofit2.Call;
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
    LiveData<ApiResponse<NewsResponse>> getAllHeadlines(
            @Query("apikey") String api,
            @Query("language") String language,
            @Query("sources") String sources
    );

    @GET("top-headlines")
    LiveData<ApiResponse<NewsResponse>> getAllNews(
            @Query("apikey") String api,
            @Query("language") String language,
            @Query("pageSize") int pageSize
    );

    @GET("everything")
    LiveData<ApiResponse<NewsResponse>> getQueryNews(
            @Query("apikey") String api,
            @Query("language") String language,
            @Query("q") String query,
            @Query("pageSize") int pageSize
    );
}
