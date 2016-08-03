package com.soam.api.response;

import com.google.gson.annotations.SerializedName;
import com.soam.model.User;

/**
 * Created by maelfosso on 7/31/16.
 */
public class AuthResponse {

    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;


    public AuthResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
