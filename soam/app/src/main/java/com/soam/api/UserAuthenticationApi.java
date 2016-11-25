package com.soam.api;

import com.soam.api.response.AuthResponse;
import com.soam.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by maelfosso on 7/31/16.
 */
public interface UserAuthenticationApi {

    @POST("/api/auth/signup")
    Call<AuthResponse> signUp(@Body User user);

    @FormUrlEncoded
    @POST("/api/auth/signin")
    Call<AuthResponse> signIn(@Field("email") String email, @Field("password") String password);

    @GET("/api/auth/refresh")
    Call<String> refresh();

    @GET("/api/users/me")
    Call<User> me();

}
