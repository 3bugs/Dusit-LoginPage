package com.example.loginpage.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {

    @GET("login")
    Call<LoginResponse> login(
            @Query("username") String username,
            @Query("password") String password
    );

}
