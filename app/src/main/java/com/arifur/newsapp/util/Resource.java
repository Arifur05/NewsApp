package com.arifur.newsapp.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : Arif
 * @date : 10-February-2021 01:37 AM
 * @package : com.arifur.newsapp.util
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@NonNull String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}
