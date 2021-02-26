package com.arifur.newsapp.repository;

import android.content.Context;
import android.util.Log;

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
    private static final String TAG = "NewsDataRepository";
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
                    Log.d(TAG, "saveCallResult: saved " + item.getArticle());
                    for (int index = 0; index < item.getArticle().size(); index++) {
                        item.getArticle().get(index).setCategory("headlines");
                        Log.d(TAG, "saveCallResult: saved " + item.getArticle().get(index).getCategory());
                    }
                    mArticleDao.deleteArticleByCategory("headlines");
                    mArticleDao.insertArticles(item.getArticle());
                } else {
                    Log.d(TAG, "saveCallResult: " + item.getStatus());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticleDao.getArticleByCategory("headlines");
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NewsResponse>> createCall() {
                return ServiceGenerator.getNewsApi()
                        .getAllHeadlines(API_KEY, "en", "bbc-news, cnn, independent,abc-news");
            }
        }.getAsLiveData();

    }

    public LiveData<Resource<List<Article>>> getAllNewsApi() {
        return new NetworkBoundResource<List<Article>, NewsResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull NewsResponse items) {
                if (items.getArticle() != null) {
                    Log.d(TAG, "saveCallResult: saved " + items.getArticle());
                    for (int index = 0; index < items.getArticle().size(); index++) {
                        items.getArticle().get(index).setCategory("all");
                        Log.d(TAG, "saveCallResult: saved " + items.getArticle().get(index).getCategory());
                    }
                    mArticleDao.deleteArticleByCategory("all");
                    mArticleDao.insertArticles(items.getArticle());
                } else {
                    Log.d(TAG, "saveCallResult: " + items.getStatus());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticleDao.getArticleByCategory("all");
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NewsResponse>> createCall() {
                return ServiceGenerator.getNewsApi().getAllNews(API_KEY, "en", 100);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Article>>> getQueriedNewsApi(String query) {
        return new NetworkBoundResource<List<Article>, NewsResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull NewsResponse queriedNewsitem) {
                if (queriedNewsitem.getArticle() != null) {
                    mArticleDao.deleteArticleByCategory(query);
                    for (int index = 0; index < queriedNewsitem.getArticle().size(); index++) {
                        queriedNewsitem.getArticle().get(index).setCategory(query);
                        Log.d(TAG, "saveCallResult: saved " + queriedNewsitem.getArticle().get(index).getCategory());
                    }
                    mArticleDao.insertArticles(queriedNewsitem.getArticle());
                } else {
                    Log.d(TAG, "saveCallResult: " + queriedNewsitem.getStatus());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticleDao.getArticleByCategory(query);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<NewsResponse>> createCall() {
                return ServiceGenerator.getNewsApi().getQueryNews(API_KEY, "en", query, 100);
            }
        }.getAsLiveData();
    }

}
