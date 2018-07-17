package com.fuhl.androidhandbook.api;

public class BaseEntity<T> {

    private static String SUCCESS_CODE = "ok";
    private String code;
    private String message;
    private T data;


    public boolean isSuccess() {
        if (code != null && code.equals(SUCCESS_CODE)) {
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
