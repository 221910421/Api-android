package com.example.api_android_103.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReponseSuccess {
    @SerializedName("success")
    @Expose
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
