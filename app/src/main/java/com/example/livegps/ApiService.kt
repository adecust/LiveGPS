package com.example.livegps



import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


public interface ApiService {


    @GET("/api/v1/bus/getLastLocations")
    fun getLastLocations() :Call<List<LocationModel>>
    @POST("/api/v1/bus/location")
    fun setLastLocation(@Body dataModel: LocationModel) : Call<Void>
}