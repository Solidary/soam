package com.soam.api.response;

/**
 * Created by maelfosso on 8/1/16.
 */
public class ApiError {

    int code;
    String message;

    public ApiError() {
    }

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
