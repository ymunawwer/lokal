package com.example.yaseenmunawwer.lokal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("list")
    Call<List<Data>> getJSON();
}