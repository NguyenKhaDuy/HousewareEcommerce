package com.example.housewareecommerce.Model.DTO;

import org.springframework.http.HttpStatus;

public class MessageDTO<T>{
    private String message;
    private HttpStatus httpStatus;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
