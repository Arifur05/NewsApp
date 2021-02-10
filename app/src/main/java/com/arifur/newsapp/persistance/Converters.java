package com.arifur.newsapp.persistance;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author : Arif
 * @date : 11-February-2021 03:07 AM
 * @package : com.arifur.newsapp.persistance
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class Converters {

    @TypeConverter
    public static String[] fromString(String value){
        Type listType= new TypeToken<String[]>(){}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String fromArrayList(String[] list){
        Gson gson= new Gson();
        String json= gson.toJson(list);
        return json;
    }

}
