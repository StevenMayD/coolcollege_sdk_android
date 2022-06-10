package com.coolcollege.aar.model;

import com.google.gson.Gson;

public class ErrorModel {
    private boolean isError;
    private String error;


    public ErrorModel(boolean isError, String error) {
        this.isError = isError;
        this.error = error;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
