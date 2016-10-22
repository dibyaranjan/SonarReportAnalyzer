package com.dibya.sonar.entity.vo;

/**
 * A Generic VO for retruning all data from the controller.
 * 
 * isSuccessful will indicate if system could process the request without any
 * exceptions
 * 
 * message will contain any message to be displayed to user
 * 
 * The object data will hold if the JSON is returning any data.
 * 
 * @author Dibya Ranjan
 */
public class GenericJsonObject {
    private boolean isSuccessful;
    private String message;
    private Object data;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
