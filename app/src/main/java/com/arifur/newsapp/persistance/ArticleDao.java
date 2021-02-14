package com.arifur.newsapp.persistance;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.arifur.newsapp.model.Article;

import java.sql.Timestamp;
import java.util.List;


import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;


/**
 * @author : Arif
 * @date : 11-February-2021 03:05 AM
 * @package : com.arifur.newsapp.repository
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/

@Dao
public interface ArticleDao {

    @Insert(onConflict = IGNORE)
    long[] insertArticles(Article... articles);

    @Insert(onConflict = REPLACE)
    void insertArticles(Article article);

    @Query("SELECT * FROM articles WHERE source_id= :source ORDER BY publishedAt DESC")
    LiveData<List<Article>> getHeadlines(String source);

    @Query("UPDATE articles SET author= :author, description= :description,url= :url, " +
            "urlToImage= :urlToImage, publishedAt= :publishedAt," +
            "content= :content, category= :category, save_date= :save_date " +
            " WHERE title= :title ")
    void updateNewsTable(String title, String author, String description, String url, String urlToImage, String publishedAt,
                         String content, String category, Timestamp save_date);

    @Query("SELECT * FROM articles WHERE category=:category ORDER BY publishedAt DESC")
    LiveData<List<Article>> getArticleByCategory(String category);
}
