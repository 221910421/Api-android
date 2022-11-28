package com.example.api_android_103.interfaces;


import com.example.api_android_103.models.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @GET("api/usuario/{id}")
    public Call<Usuario> find(@Path("id") String id);
}
