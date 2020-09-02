package fungsiu.kotlin.module.post.intf

interface ApiResponseIntf<T> {
    fun onSuccess(result: List<T>?)
    fun onError(error: String?)
}