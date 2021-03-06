package com.soam.api;

import android.content.Intent;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.soam.SignInActivity;
import com.soam.SoamApplication;
import com.soam.api.response.ApiError;
import com.soam.model.User;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maelfosso on 7/31/16.
 */
public class ApiRetrofit {
    public final static String TAG = ApiRetrofit.class.getSimpleName();

    public final static String SOAM_BASE_API_URL = "http://192.168.65.1:3000/"; //"http://192.168.56.1:3000";
    public final static OkHttpClient.Builder HTTP_CLIENT = new OkHttpClient.Builder();

    public final static Retrofit.Builder SOAM_RETROFIT_BUILDER = new Retrofit.Builder()
            .baseUrl(SOAM_BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <T> T create(Class<T> service) {
        return create(service, null);
    }

    public static <T> T create(Class<T> service, final String authToken) {

        if(authToken != null) {
            HTTP_CLIENT.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + authToken)
                            .method(original.method(), original.body());

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });
        }

        HTTP_CLIENT.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                UserAuthenticationApi api =
                        ApiRetrofit.create(UserAuthenticationApi.class, authToken);
                Call<String> call = api.refresh();
                retrofit2.Response<String> resp = call.execute();
                String token = resp.body();

                Log.d(TAG, "OnAuthenticator : " + response.code() + " -- " + response.message());
                return response.request().newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .build();
            }
        });

        OkHttpClient client = HTTP_CLIENT.build();
        Retrofit retrofit = SOAM_RETROFIT_BUILDER.client(client).build();
        return retrofit.create(service);
    }

    public static ApiError parse(retrofit2.Response<?> response) {
        Converter<ResponseBody, ApiError> converter = SOAM_RETROFIT_BUILDER.build()
                .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();

            return new ApiError();
        }
        return error;
    }
}
