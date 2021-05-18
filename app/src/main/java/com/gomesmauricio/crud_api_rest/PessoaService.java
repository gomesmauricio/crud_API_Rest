package com.gomesmauricio.crud_api_rest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PessoaService {

    @GET("pessoa")
    Call<List<Pessoa>> selectPessoa();

    @GET("pessoa/{id}")
    Call<Pessoa> selectPessoa(@Path("id") int id);

    @POST("pessoa")
    Call<Pessoa> insertPessoa(@Body Pessoa pessoa);

    @PUT("pessoa/{id}")
    Call<Pessoa> updatePessoa(@Path("id") int id, @Body Pessoa pessoa);

    @DELETE("pessoa/{id}")
    Call<ResponseBody> deletePessoa(@Path("id") int id);
}
