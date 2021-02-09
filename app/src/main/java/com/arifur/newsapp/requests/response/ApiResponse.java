package com.arifur.newsapp.requests.response;

import java.io.IOException;

import retrofit2.Response;

/**
 * @author : Arif
 * @date : 28-January-2021 10:23 PM
 * @package : com.arifur.newsapp.requests.response
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class ApiResponse<T> {

    public ApiResponse<T> create(Throwable error) {
        return new ApiErrrorResponse<>(!error.getMessage().equals("") ? error.getMessage() : "Unknown error\nPlease Check Internet Connection");
    }

    public ApiResponse<T> create(Response<T> response){
        if (response.isSuccessful()){
            T body= response.body();
            if (body== null|| response.code()==204){
                return new ApiEmptyResponse<>();
            }
            else return new ApiSuccessResponse<>(body);
        }
        else {
            String errorMessage= "";
            try {
                errorMessage= response.errorBody().string();
            }
            catch (IOException e){
                e.getCause();
                errorMessage= response.message();
            }
            return new ApiErrrorResponse<>(errorMessage);
        }
    }
    public class ApiSuccessResponse<T> extends ApiResponse<T> {
        private T body;

        ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public class ApiErrrorResponse<T> extends ApiResponse<T> {
        private String errormessage;

        ApiErrrorResponse(String errormessage) {
            this.errormessage = errormessage;
        }

        public String getErrormessage() {
            return errormessage;
        }
    }

    public class ApiEmptyResponse<T> extends ApiResponse<T> {
    }

}
