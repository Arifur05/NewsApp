package com.arifur.newsapp.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.arifur.newsapp.model.Article;

/**
 * @author : Arif
 * @date : 11-February-2021 02:56 AM
 * @package : com.arifur.newsapp.repository
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/

@Database(entities = {Article.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NewsDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "articles_db1";

    private static NewsDatabase instance;

    public static NewsDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NewsDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ArticleDao getArticleDao();
}
