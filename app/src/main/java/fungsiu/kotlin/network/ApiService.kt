package fungsiu.kotlin.network

import fungsiu.kotlin.network.model.PostDetailResponse
import fungsiu.kotlin.network.model.PostListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/posts")
    fun getPostList(
        @Query("_start") start: Int?,
        @Query("_limit") limit: Int?
    ): Single<List<PostListResponse.Post>>

    @GET("/posts/{id}")
    fun getPostDetail(
        @Path("id") id: Int?
    ): Single<PostDetailResponse.Post>

}