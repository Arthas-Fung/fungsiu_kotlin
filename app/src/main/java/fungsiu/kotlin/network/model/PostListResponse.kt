package fungsiu.kotlin.network.model

import androidx.annotation.Keep

@Keep
class PostListResponse {

    @Keep
    data class Post(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
    )

}