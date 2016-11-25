package com.soam;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.soam.model.User;

/**
 * Created by maelfosso on 11/18/16.
 */
public class AuthPreferences {

    public static final String KEY_USER =
            "com.tcheps.AuthPreferences.key_user";
    public static final String KEY_TOKEN =
            "com.tcheps.AuthPreferences.key_token";

    private AuthPreferences(Context context) {
        // tsPreferences = context.getSharedPreferences("com.soam.auth", Context.MODE_PRIVATE);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.soam.auth", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.commit();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.soam.auth", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.soam.auth", Context.MODE_PRIVATE);

        return new Gson().fromJson(sharedPreferences.getString(KEY_USER, null), User.class);
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.soam.auth", Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_TOKEN, null);
    }
}