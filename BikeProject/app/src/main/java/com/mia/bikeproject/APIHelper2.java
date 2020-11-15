package com.mia.bikeproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper2 {
    private Retrofit retrofit;
    private APIService apiService;
    public static APIHelper2 apiHelper2 = new APIHelper2();

    private APIHelper2() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(APIService.class);
    }

    public static APIHelper2 getInstance() {
        return apiHelper2;
    }

    public APIService getApiService() {
        return apiService;
    }
}
