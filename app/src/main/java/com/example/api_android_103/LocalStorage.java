package com.example.api_android_103;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String token;
    int id;

    public LocalStorage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("STORAGE_LOGIN_API",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getToken() {
        token = sharedPreferences.getString("TOKEN","");
        return token;
    }

    public void setToken(String token) {
        editor.putString("TOKEN",token);
        editor.commit();
        this.token = token;
    }
    public int getId() {
        id = sharedPreferences.getInt("ID",-1);
        return id;
    }

    public void setId(int id) {
        editor.putInt("ID",id);
        editor.commit();
        this.id = id;
    }
}
