package com.svo.svo.other.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseBody<T> {
    private boolean success;
    private String message;
    private T data;
    private int total;
    private String[] invalidParams;
    private Exception exception;

    public ResponseBody() {
        this.success = true;
    }

    public ResponseBody(String message) {
        this.success = true;
        this.message = message;
    }

    public ResponseBody(T data) {
        this.success = true;
        this.data = data;
    }

    public ResponseBody(T data, int total) {
        this.success = true;
        this.data = data;
        this.total = total;
    }

    public ResponseBody(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public ResponseBody(String message, T data, int total) {
        this.success = true;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public ResponseBody(boolean success) {
        this.success = success;
    }

    public ResponseBody(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseBody(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ResponseBody(boolean success, String message, Exception exception) {
        this.success = success;
        this.message = message;
        this.exception = exception;
    }

    public ResponseBody(boolean success, String message, String[] invalidParams) {
        this.success = success;
        this.message = message;
        this.invalidParams = invalidParams;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            return var3.getMessage();
        }
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String[] getInvalidParams() {
        return this.invalidParams;
    }

    public void setInvalidParams(String[] invalidParams) {
        this.invalidParams = invalidParams;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}