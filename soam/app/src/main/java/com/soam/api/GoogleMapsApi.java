package com.soam.api;

import com.soam.api.response.maps.Routes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maelfosso on 10/31/16.
 */
public interface GoogleMapsApi {

    @GET("api/directions/json?key=AIzaSyC22GfkHu9FdgT9SwdCWMwKX1a4aohGifM")
    Call<Routes> getDistanceDuration(@Query("units") String units,
                                     @Query("origin") String origin, @Query("destination") String destination,
                                     @Query("mode") String mode);
}
