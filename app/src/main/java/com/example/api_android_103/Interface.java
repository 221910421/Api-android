package com.example.api_android_103;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import com.example.api_android_103.models.Barang.Barang;
import com.example.api_android_103.models.Barang.SingleDataBarang;
import com.example.api_android_103.models.GetReponseSuccess;

public interface Interface {

    @GET("users")
    Call<Barang> postBarang(@Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("users")
    Call<GetReponseSuccess> postBarangStore(@Header("Authorization") String Authorization,
                                            @Field("name") String name,
                                            @Field("primer_apellido") String pr_apellido,
                                            @Field("segundo_apellido") String sg_apellido,
                                            @Field("id_rol") String rol,
                                            @Field("email") String email,
                                            @Field("usuario") String usuario,
                                            @Field("password") String pass,
                                            @Field("estado") String estado,
                                            @Field("condicion") String condicion);

    @GET("users/{id}")
    Call<SingleDataBarang> postBarangEdit(@Header("Authorization") String Authorization,
                                          @Path(value = "id", encoded = true) int id);

    @FormUrlEncoded
    @POST("users/{id}")
    Call<GetReponseSuccess> postBarangUpdate(@Header("Authorization") String Authorization,
                                             @Field("name") String name,
                                             @Field("primer_apellido") String pr_apellido,
                                             @Field("segundo_apellido") String sg_apellido,
                                             @Field("id_rol") String rol,
                                             @Field("email") String email,
                                             @Field("usuario") String usuario,
                                             @Field("password") String pass,
                                             @Field("estado") String estado,
                                             @Field("condicion") String condicion,
                                             @Path(value = "id", encoded = true) int id);

    @DELETE("users/{id}")
    Call<GetReponseSuccess> postBarangDelete(@Header("Authorization") String Authorization,
                                             @Path(value = "id", encoded = true) int id);
}
