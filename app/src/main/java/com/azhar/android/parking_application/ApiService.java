package com.azhar.android.parking_application;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 212614814 on 10/01/2018.
 */


public interface ApiService {

    @FormUrlEncoded
    @POST("/user/index.php")
    Call<ParkingUser> insertData(@Field("name") String name, @Field("email") String email , @Field("password") String password);
}