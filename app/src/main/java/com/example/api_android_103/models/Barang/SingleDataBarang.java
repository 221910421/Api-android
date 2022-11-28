package com.example.api_android_103.models.Barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleDataBarang {
    @SerializedName("success")
    @Expose
    private SingleDataSuccess success;

    public SingleDataSuccess getSuccess() {
        return success;
    }

    public void SingleDataSuccess(SingleDataSuccess success) {
        this.success = success;
    }
}
