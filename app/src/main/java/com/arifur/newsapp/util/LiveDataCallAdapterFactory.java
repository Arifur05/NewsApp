package com.arifur.newsapp.util;

import androidx.lifecycle.LiveData;

import com.arifur.newsapp.requests.response.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author : Arif
 * @date : 14-February-2021 02:27 AM
 * @package : com.arifur.newsapp.util
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    /**
     * This method performs a number of checks and then returns the Response type for the Retrofit requests.
     * (@bodyType is the ResponseType. It can be ArticleResponse)
     * <p>
     * CHECK #1) returnType returns LiveData
     * CHECK #2) Type LiveData<T> is of ApiResponse.class
     * CHECK #3) Make sure ApiResponse is parameterized. AKA: ApiResponse<T> exists.
     */

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);

        return new LiveDataCallAdapter<Type>(bodyType);
    }


}
