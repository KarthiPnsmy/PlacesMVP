package com.titut.placesmvp.places;

import com.titut.placesmvp.model.Place;
import com.titut.placesmvp.model.PlaceResponse;
import com.titut.placesmvp.datasource.PlacesApi;
import com.titut.placesmvp.utils.Constants;

import java.util.List;

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

public class PlacesPresenter implements PlacesContract.Presenter {
    private final PlacesContract.View mView;
    private final PlacesApi placesApi;

    public PlacesPresenter(PlacesContract.View view) {
        this.mView = view;


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Converter.Factory converter = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PLACES_SEARCH_URL)
                .client(client)
                .addConverterFactory(converter)
                .build();

        placesApi = retrofit.create(PlacesApi.class);
    }

    @Override
    public void loadPlaces() {
        mView.showLoading();
        placesApi.getPlaces().enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                if (response.code() != 200) {
                    mView.showErrorMessage();
                } else {
                    List<Place> places = response.body().results;
                    mView.showPlaces(places);

                }
                mView.hideLoading();
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                mView.showErrorMessage();
                mView.hideLoading();
            }
        });
    }
}
