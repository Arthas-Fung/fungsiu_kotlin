package fungsiu.kotlin.module.post.repo

import com.elvishew.xlog.XLog
import fungsiu.kotlin.module.post.intf.ApiRequestIntf
import fungsiu.kotlin.module.post.intf.ApiResponseIntf
import fungsiu.kotlin.network.ApiBuilder
import fungsiu.kotlin.network.model.PostListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PostRepo: ApiRequestIntf {

    override fun getPostList(
        response: ApiResponseIntf<PostListResponse.Post>,
        _start: Int?,
        _limit: Int?
    ) {
        val single = ApiBuilder.provideApi().getPostList(_start, _limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { result ->
                    response.onSuccess(result)
                },
                onError = {
                    response.onError(it.toString())
                    XLog.e(it)
                }
            )
    }

}