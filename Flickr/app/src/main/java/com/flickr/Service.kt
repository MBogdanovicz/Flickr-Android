package com.flickr

import com.flickr.com.flickr.model.BaseInfo
import com.flickr.com.flickr.model.BasePhoto
import com.flickr.com.flickr.model.BaseSize
import com.flickr.com.flickr.model.BaseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("?method=flickr.people.findByUsername&format=json&nojsoncallback=1&api_key=84e5361175d9e8c21ac2a7688f02a905")
    fun findByUsername(@Query("username") username: String): Call<BaseUser>

    @GET("?method=flickr.people.getPublicPhotos&format=json&nojsoncallback=1&api_key=84e5361175d9e8c21ac2a7688f02a905")
    fun getPublicPhotos(@Query("user_id") userId: String, @Query("page") page: Int = 1): Call<BasePhoto>

    @GET("?method=flickr.photos.getInfo&format=json&nojsoncallback=1&api_key=84e5361175d9e8c21ac2a7688f02a905")
    fun getInfo(@Query("photo_id") photoId: String): Call<BaseInfo>

    @GET("?method=flickr.photos.getSizes&format=json&nojsoncallback=1&api_key=84e5361175d9e8c21ac2a7688f02a905")
    fun getSizes(@Query("photo_id") photoId: String): Call<BaseSize>
}