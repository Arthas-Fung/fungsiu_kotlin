package fungsiu.kotlin.module.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elvishew.xlog.XLog
import fungsiu.kotlin.network.ApiBuilder
import fungsiu.kotlin.network.model.PostDetailResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PostDetailViewModel : ViewModel() {
    private var isLoading = false

    private val _post = MutableLiveData<PostDetailResponse.Post?>()
    val post: LiveData<PostDetailResponse.Post?>
        get() = _post

    fun isLoading(): Boolean {
        return isLoading
    }

    fun getPostDetail(response: ApiResponse, id: Int?) {
        isLoading = true
        val single = ApiBuilder.provideApi().getPostDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { result ->
                    isLoading = false
                    response.onReceive(result)
                },
                onError = {
                    isLoading = false
                    XLog.e(it)
                }
            )
    }

    interface ApiResponse {
        fun onReceive(detail: PostDetailResponse.Post?)
    }

    fun modifiedId(id: Int): String {
        return "id: $id"
    }

    fun modifiedTitle(title: String): String {
        return "title: $title"
    }

}