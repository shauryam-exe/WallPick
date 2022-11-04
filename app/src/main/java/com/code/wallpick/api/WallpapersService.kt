package com.code.wallpick.api

import com.code.wallpick.data.model.Photo
import com.code.wallpick.data.model.PhotoList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface WallpapersService {

    @Headers("Authorization: 563492ad6f91700001000001c97f1bf249ea491ebb47261cff9699f6")
    @GET("/v1/search")
    suspend fun getWallpaperList(
        @Query("query") wallpaperType: String,
        @Query("orientation") orientation: String
    ): Response<PhotoList>

    @Headers("Authorization: 563492ad6f91700001000001c97f1bf249ea491ebb47261cff9699f6")
    @GET("/v1/photos")
    suspend fun getWallpaper(@Query("query") wallpaperId: Int): Response<Photo>

    @Headers("Authorization: 563492ad6f91700001000001c97f1bf249ea491ebb47261cff9699f6")
    @GET("/v1/curated")
    suspend fun getCurated(): Response<PhotoList>
}