package com.sparkfusion.quiz.brainvoyage.api.online.command;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public Response() {}

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


























