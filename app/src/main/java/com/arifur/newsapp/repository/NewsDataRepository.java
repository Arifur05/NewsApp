package com.arifur.newsapp.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.persistance.ArticleDao;
import com.arifur.newsapp.persistance.NewsDatabase;
import com.arifur.newsapp.requests.NewsApiClient;
import com.arifur.newsapp.requests.ServiceGenerator;
import com.arifur.newsapp.requests.response.ApiResponse;
import com.arifur.newsapp.requests.response.NewsResponse;
import com.arifur.newsapp.util.AppExecutors;
import com.arifur.newsapp.util.NetworkBoundResource;
import com.arifur.newsapp.util.Resource;

import java.util.List;

import static com.arifur.newsapp.util.Constants.API_KEY;

/**
 * @author : Arif
 * @date : 04-January-2021 06:59 PM
 * @package : com.arifur.newsapp.repository
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class NewsDataRepository {
    private static NewsDataRepository instance;
    private ArticleDao mArticleDao;

    //private static NewsApiClient mApiClient;
    public static NewsDataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NewsDataRepository(context);
        }
        return instance;
    }

    public NewsDataRepository(Context context) {
        mArticleDao = NewsDatabase.getInstance(context).getArticleDao();
    }

    public LiveData<Resource<List<Article>>> getHeadlinesApi() {
        return new NetworkBoundResource<List<Article>, NewsResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull NewsResponse item) {
                if (item.getArticle() != null) {
                    mArticleDao.insertArticles(item.getArticle());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticleDao.getHeadlines("bbc-news, cnn, independent,abc-news");
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NewsResponse>> createCall() {
                return ServiceGenerator.getNewsApi()
                        .getAllHeadlines(API_KEY, "en", "bbc-news, cnn, independent,abc-news");
            }
        }.getAsLiveData();
    }


}
