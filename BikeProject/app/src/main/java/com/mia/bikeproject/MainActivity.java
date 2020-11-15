package com.mia.bikeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mia.bikeproject.models.BikeInfo;
import com.mia.bikeproject.models.ForeignerInfo;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private ArrayList<BikeInfo> bikeList;
    private ArrayList<ForeignerInfo> foreignerInfoList;

    private int index = 0;

    private Button btnGet;
    private MapView mapView;
    private LinearLayout linearRightBtn;
    private TextView tvFirst, tvSecond, tvThird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup mapContainer = findViewById(R.id.container);

        tvFirst = findViewById(R.id.tv_first);
        tvSecond = findViewById(R.id.tv_second);
        tvThird = findViewById(R.id.tv_third);
        btnGet = findViewById(R.id.btn_get);
        linearRightBtn = findViewById(R.id.linear_right_btn);

        mapView = new MapView(this);
        mapContainer.addView(mapView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        getBikeInfoList();

//        getBikeAccidents();
        getForeigner();
    }

    @Override
    protected void onStop() {
        super.onStop();

        for(MapPOIItem item : mapView.getPOIItems()) {
            mapView.removePOIItem(item);
        }
    }

    /**
     * 우측 버튼
     */
    public void mainOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                linearRightBtn.setVisibility(View.INVISIBLE);
                btnGet.setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, BikeListFragment.newInstance(bikeList));
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.iv_refresh:
                if(index == 0) {getBikeInfoList();}
                if(index == 1) {getForeigner();}
                break;
            case R.id.iv_favorite:
            case R.id.iv_info:
            case R.id.tv_description:
            case R.id.btn_get:
                Toast.makeText(getBaseContext(), "지금은 대여할 수 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_first:
                index = 0;
                getBikeInfoList();
                tvFirst.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green));
                tvSecond.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvThird.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                break;
            case R.id.tv_second:
                index = 1;
                getForeigner();
                tvFirst.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvSecond.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green));
                tvThird.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                break;
            case R.id.tv_third:
                tvFirst.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvSecond.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvThird.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green));
                break;
        }
    }

    /**
     * 공공자전거 대여소 조회
     */
    private void getBikeInfoList() {

        for (MapPOIItem item : mapView.getPOIItems()) {
            mapView.removePOIItem(item);
        }

        Call<JsonObject> call = APIHelper.getInstance().getApiService().getBikeInfo(
                "514a647a55737579313333657343426a",
                "json",
                "bikeList",
                1,
                70
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공");
                    if (response.body() == null) {
                        return;
                    }
                    JsonArray jsonList = response.body().getAsJsonObject("rentBikeStatus").getAsJsonArray("row");

                    bikeList = new ArrayList<>();
                    for (JsonElement bike : jsonList) {
                        bikeList.add(new BikeInfo(bike.getAsJsonObject()));
                    }

                    for (BikeInfo bikeInfo : bikeList) {
                        createMarker(bikeInfo);
                    }

                } else {
                    Log.d(TAG, "onResponse 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 인터넷 끊김, 예외 발생 등 시스템적 이유 발생했을 때
                Log.d(TAG, "onFailure : " + t.getMessage());
            }
        });
    }

    /**
     * 자전거 사고다발지역 조회
     */
    private void getBikeAccidents() {

        Call<JsonObject> call = APIHelper2.getInstance().getApiService().getAccidents(
                "I4chePuLqVf9ud6NtZD8BaVB%2BkbbZS%2FZV8C2MlTraIj9ABdncq9TUNlYL0olN9D0eY1j7vlBkIGY79WmCY%2BrdQ%3D%3D",
                2018,
                11,
                110,
                "json",
                10,
                1
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Accidents onResponse 성공");
//                    JsonObject jsonElements = response.body();
                } else {
                    Log.d(TAG, "Accidents onResponse 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 인터넷 끊김, 예외 발생 등 시스템적 이유 발생했을 때
                Log.d(TAG, "Accidents onFailure : " + t.getMessage());
            }
        });
    }

    /**
     * 외국인 대여 정보 조회
     */
    private void getForeigner() {

        for (MapPOIItem item : mapView.getPOIItems()) {
            mapView.removePOIItem(item);
        }

        Call<JsonObject> call = APIHelper.getInstance().getApiService().getBikeInfo(
                "514a647a55737579313333657343426a",
                "json",
                "cycleForeignerRentDayInfo",
                1,
                70
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Foreigner onResponse 성공");
                    if (response.body() == null) {
                        return;
                    }
                    JsonArray jsonList = response.body().getAsJsonObject("cycleForeignerRentDayInfo").getAsJsonArray("row");

                    foreignerInfoList = new ArrayList<>();
                    for (JsonElement foreigner : jsonList) {
                        foreignerInfoList.add(new ForeignerInfo(foreigner.getAsJsonObject()));
                    }

                    for(ForeignerInfo info : foreignerInfoList) {
                        createMarkerFK(info);
                    }

                    jsonList.toString();
                } else {
                    Log.d(TAG, "onResponse 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 인터넷 끊김, 예외 발생 등 시스템적 이유 발생했을 때
                Log.d(TAG, "onFailure : " + t.getMessage());
            }
        });
    }

    /**
     * 마커 생성 (전체)
     */
    private void createMarker(BikeInfo bike) {
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(bike.getStationName());
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(bike.getLat(), bike.getLnt()));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.seoul_marker);

        mapView.addPOIItem(marker);
    }

    /**
     * 마커 생성 (외국인 사용자)
     */
    private void createMarkerFK(ForeignerInfo info) {
        for(BikeInfo bike : bikeList) {
            String temp = bike.getStationName();
            String[] str = temp.split(" ");

            String result = "";
            for(int i = 1; i < str.length; i++) {
                result += str[i];
            }
            String rentNm = (info.getRentNm()).replaceAll(" ", "");
            if(result.equals(rentNm)) {
                createMarker(bike);
                break;
            }
        }
    }

    /**
     * 카카오 맵 해시키
     */
    private void findKeyHash(Context context) {
        String keyHash = com.kakao.util.maps.helper.Utility.getKeyHash(this);
        Log.d(TAG, keyHash);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        btnGet.setVisibility(View.VISIBLE);
        linearRightBtn.setVisibility(View.VISIBLE);
    }
}