package com.example.api_android_103.models.Barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("success")
    @Expose
    private Success success;

    public com.example.api_android_103.models.Barang.Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
