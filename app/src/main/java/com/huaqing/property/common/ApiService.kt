package com.huaqing.property.common

import com.huaqing.property.model.Login
import com.huaqing.property.model.MyInfo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("api-company/sys/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Flowable<Login>

    @Headers("H_NAME" + ":" + "TOKEN")
    @GET("api-company/sys/myInfo")
    fun myInfo(): Flowable<MyInfo>

}