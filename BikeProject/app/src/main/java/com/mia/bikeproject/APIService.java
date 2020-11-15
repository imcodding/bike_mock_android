package com.mia.bikeproject;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String BASE_URL = "http://openapi.seoul.go.kr:8088/";
    String BASE_URL2 = "http://apis.data.go.kr/";


    @GET("{key}/{type}/{service}/{startIndex}/{endIndex}/")
    Call<JsonObject> getBikeInfo(
            @Path("key") String key,
            @Path("type") String type,
            @Path("service") String service,
            @Path("startIndex") int startIndex,
            @Path("endIndex") int endIndex
            );


    @GET("B552061/frequentzoneBicycle/getRestFrequentzoneBicycle")
    Call<JsonObject> getAccidents(
        @Query("ServiceKey") String ServiceKey,
        @Query("searchYearCd") int searchYearCd,
        @Query("siDo") int siDo,
        @Query("guGun") int guGun,
        @Query("type") String type,
        @Query("numOfRows") int numOfRows,
        @Query("pageNo") int pageNo
    );
}
