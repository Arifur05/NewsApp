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
                    Log.d(TAG, "saveCallResult: saved " + item.getArticle().size());
                    Article[] articles = new Article[item.getArticle().size()];
                    int index = 0;
                    for (index = 0; index < item.getArticle().size(); index++) {
                        item.getArticle().get(index).setCategory("headlines");
                    }
                    for (long rowid : mArticleDao.insertArticles((Article[]) (item.getArticle().toArray(articles)))) {
                        if (rowid == 1) {

                            Log.d(TAG, "saveCallResult: CONFLICT... This article is already in the cache");
                            mArticleDao.updateNewsTable(
                                    articles[index].getTitle(),
                                    articles[index].getAuthor(),
                                    articles[index].getDescription(),
                                    articles[index].getUrl(),
                                    articles[index].getUrlToImage(),
                                    articles[index].getPublishedAt(),
                                    articles[index].getContent(),
                                    articles[index].getCategory(),
                                    articles[index].getSaveDate()
                            );
                        }
                        index++;
                    }

                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                Log.d(TAG, "loadFromDb: fetched");
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
