package com.titut.placesmvp.datasource;

import com.titut.placesmvp.model.PlaceDetailResponse;
import com.titut.placesmvp.model.PlaceResponse;
import com.titut.placesmvp.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karthi.ponnusamy on 31/5/17.
 */

public interface PlacesApi {
    @GET("api/place/nearbysearch/json?location=" + Constants.LOCATION+ "&radius=" + Constants.RADIUS+ "&key=" + Constants.PLACES_API_KEY)
    Call<PlaceResponse> getPlaces();

    @GET("api/place/details/json?key=" + Constants.PLACES_API_KEY)
    Call<PlaceDetailResponse> getPlaceDetail(@Query("placeid") String placeId);
}
