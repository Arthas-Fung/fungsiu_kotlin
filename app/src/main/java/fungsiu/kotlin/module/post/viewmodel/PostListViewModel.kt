package fungsiu.kotlin.module.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fungsiu.kotlin.base.BaseApplication
import fungsiu.kotlin.database.post.PostDatabase
import fungsiu.kotlin.database.post.PostModel
import fungsiu.kotlin.module.post.intf.ApiRequestIntf
import fungsiu.kotlin.module.post.intf.ApiResponseIntf
import fungsiu.kotlin.module.post.intf.PostDataIntf
import fungsiu.kotlin.network.model.PostListResponse
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostListViewModel(private val request: ApiRequestIntf) : ViewModel() {
    private var _start = 0
    private val _limit = 20
    private var isLoading = false
    var postList: MutableList<PostListResponse.Post>? = arrayListOf()

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    private val _list = MutableLiveData<List<PostListResponse.Post>?>()
    private val list: LiveData<List<PostListResponse.Post>?>
        get() = _list

    private val _listItem = MutableLiveData<PostListResponse.Post?>()
    val listItem: LiveData<PostListResponse.Post?>
        get() = _listItem

    fun isLoading(): Boolean {
        return isLoading
    }

    fun onRefresh(intf: PostDataIntf) {
        _start = 0
        getPostList(intf)
    }

    fun onLoadMore(intf: PostDataIntf) {
        val posts = list.value
        _start = posts?.get(posts.size - 1)?.id ?: 0
        getPostList(intf)
    }

    private fun getPostList(intf: PostDataIntf) {
        isLoading = true
        request.getPostList(object :
            ApiResponseIntf<PostListResponse.Post> {
            override fun onSuccess(result: List<PostListResponse.Post>?) {
                isLoading = false
                if (_start == 0) {
                    postList = result?.toMutableList()
                    _list.value = postList
                    val posts = list.value
                    intf.onReceive(_list.value, 0)
                } else {
                    result?.let {
                        postList?.addAll(it)
                    }
                    _list.value = postList
                    val posts = list.value
                    posts?.size?.let {
                        intf.onReceive(_list.value, it)
                    }
                }

                //region insert to database
//                var postModel = PostModel()
//                postModel.userId = result!![0].userId
//                postModel.title = result!![0].title
//                postModel.body = result!![0].body
//
//                Completable.fromAction {
//                    val postsDao =
//                        PostDatabase.getDatabase(
//                            BaseApplication.instance()
//                        )
//                            .postDao()
//                    postsDao.insert(postModel)
//                }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(object : CompletableObserver {
//                        override fun onSubscribe(d: Disposable) {
//
//                        }
//
//                        override fun onComplete() {
//
//                        }
//
//                        override fun onError(e: Throwable) {
//
//                        }
//                    })
                //endregion
            }

            override fun onError(error: String?) {
                isLoading = false
            }
        }, _start, _limit)
    }

    fun setWord(w: String) {
        _word.value = w
    }

    fun setPost(post: PostListResponse.Post?) {
        _listItem.value = post
    }

    fun modifiedId(id: Int): String {
        return "id: $id"
    }

    fun modifiedTitle(title: String): String {
        return "title: $title"
    }

}