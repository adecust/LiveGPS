package com.example.livegps



import retrofit2.http.GET




public interface ApiService {
    @GET("/api/v1/bus/getLastLocations")
    Call

}