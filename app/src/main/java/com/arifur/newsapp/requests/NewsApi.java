package com.arifur.newsapp.requests;

import androidx.lifecycle.LiveData;

import com.arifur.newsapp.model.NewsDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author : Arif
 * @date : 29-December-2020 07:07 PM
 * @package : com.arifur.newsapp.Requests
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public interface NewsApi {

    @GET("")
    LiveData<NewsDataModel> getWorldNews(
            @Query("apikey") String api,
            @Query("q") String query,
            @Query("language") String language
    );
}
