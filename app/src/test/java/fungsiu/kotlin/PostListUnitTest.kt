package fungsiu.kotlin

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fungsiu.kotlin.module.post.intf.ApiRequestIntf
import fungsiu.kotlin.module.post.intf.ApiResponseIntf
import fungsiu.kotlin.module.post.intf.PostDataIntf
import fungsiu.kotlin.module.post.viewmodel.PostListViewModel
import fungsiu.kotlin.network.model.PostListResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify

class PostListUnitTest {
    @Mock
    private lateinit var context: Application

    @Mock
    private lateinit var request: ApiRequestIntf

    @Captor
    private lateinit var response: ArgumentCaptor<ApiResponseIntf<PostListResponse.Post>>

    @Mock
    private lateinit var intf: PostDataIntf

    @Captor
    private lateinit var start: ArgumentCaptor<Int?>

    @Captor
    private lateinit var limit: ArgumentCaptor<Int?>

    private lateinit var viewModel: PostListViewModel

    var normalPostList: List<PostListResponse.Post>? = arrayListOf()
    var reachedMaxPostList: List<PostListResponse.Post>? = arrayListOf()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        viewModel = PostListViewModel(request)

        mockData()
    }

    @Test
    fun onRefreshPostListSuccess() {
        viewModel.onRefresh(intf)
        verify(request, Mockito.times(1)).getPostList(
            capture(response),
            capture(start),
            capture(limit)
        )
        response.value.onSuccess(normalPostList)
        Assert.assertTrue(viewModel.postList?.size == 20)
    }

    @Test
    fun onRefreshPostListReachedMax() {
        viewModel.onRefresh(intf)
        verify(request, Mockito.times(1)).getPostList(
            capture(response),
            capture(start),
            capture(limit)
        )
        response.value.onSuccess(reachedMaxPostList)
        Assert.assertTrue(viewModel.postList?.size == 18)
    }

    fun mockData() {
        var MutableNormalPostList: MutableList<PostListResponse.Post> = mutableListOf()
        var MutableReachedMaxPostList: MutableList<PostListResponse.Post> = mutableListOf()
        for (i in 0..19) {
            val post = PostListResponse.Post(0, i + 1, "title", "body")
            MutableNormalPostList.add(post)
            if (i < 18) {
                MutableReachedMaxPostList.add(post)
            }
        }
        normalPostList = MutableNormalPostList
        reachedMaxPostList = MutableReachedMaxPostList
    }

}