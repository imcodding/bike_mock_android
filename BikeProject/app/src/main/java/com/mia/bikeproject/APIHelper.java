package com.mia.bikeproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    private Retrofit retrofit;
    private APIService apiService;
    public static APIHelper apiHelper = new APIHelper();

    private APIHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);
    }

    public static APIHelper getInstance() {
        return apiHelper;
    }

    public APIService getApiService() {
        return apiService;
    }
}
