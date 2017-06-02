package com.titut.placesmvp.placedetail;

import com.titut.placesmvp.model.Place;
import com.titut.placesmvp.model.PlaceDetailResponse;
import com.titut.placesmvp.datasource.PlacesApi;
import com.titut.placesmvp.utils.Constants;
import com.titut.placesmvp.utils.PlacesCache;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karthi.ponnusamy on 31/5/17.
 */

public class PlaceDetailPresenter implements PlaceDetailContract.Presenter {
    private final PlaceDetailContract.View mView;
    private final PlacesApi mPlacesApi;
    PlacesCache mPlacesCache;

    public PlaceDetailPresenter(PlaceDetailContract.View view) {
        this.mView = view;

        mPlacesCache = PlacesCache.getInstance();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Converter.Factory converter = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PLACES_SEARCH_URL)
                .client(client)
                .addConverterFactory(converter)
                .build();

        mPlacesApi = retrofit.create(PlacesApi.class);
    }

    @Override
    public void loadPlace(String placeId) {
        HashMap<String, Place> placesCache = mPlacesCache.getPlaceDetailCache();

        if (placesCache != null) {
            if (placesCache.get(placeId) == null) {
                fetchPlace(placeId);
            } else {
                mView.showPlace(placesCache.get(placeId));
            }
        }
    }

    private void fetchPlace(final String placeId){
        mView.showLoading();
        mPlacesApi.getPlaceDetail(placeId).enqueue(new Callback<PlaceDetailResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {

                if (response.code() != 200) {
                    mView.showErrorMessage();
                } else {
                    Place place = response.body().result;
                    mView.showPlace(place);
                    mPlacesCache.addPlaceToCache(placeId, place);
                }
                mView.hideLoading();
            }

            @Override
            public void onFailure(Call<PlaceDetailResponse> call, Throwable t) {
                mView.showErrorMessage();
                mView.hideLoading();
            }
        });
    }
}
