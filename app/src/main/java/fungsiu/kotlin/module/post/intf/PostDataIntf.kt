package fungsiu.kotlin.module.post.intf

import fungsiu.kotlin.network.model.PostListResponse

interface PostDataIntf {
    fun onReceive(result: List<PostListResponse.Post>?, start: Int)
}