package fungsiu.kotlin.module.post.intf

import fungsiu.kotlin.network.model.PostListResponse

interface ApiRequestIntf {
    fun getPostList(response: ApiResponseIntf<PostListResponse.Post>, _start: Int?, _limit: Int?)
}