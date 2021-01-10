package com.arifur.newsapp.util;

/**
 * @author : Arif
 * @date : 29-December-2020 09:03 PM
 * @package : com.arifur.newsapp.Util
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class Constants {

    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "777e85d1704c4208b87adb6b9ccc6cf1";

    public static final int CONNECTION_TIMEOUT = 10; // 10 seconds
    public static final int READ_TIMEOUT = 2; // 2 seconds
    public static final int WRITE_TIMEOUT = 2; // 2 seconds

    public static final int NEWS_REFRESH_TIME = 60 * 60 * 24 * 30; // 30 days (in seconds)
}
