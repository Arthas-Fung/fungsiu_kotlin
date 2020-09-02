package fungsiu.kotlin.module.post.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fungsiu.kotlin.R
import fungsiu.kotlin.base.BaseActivity
import fungsiu.kotlin.databinding.ActivityPostListBinding
import fungsiu.kotlin.module.post.intf.ApiRequestIntf
import fungsiu.kotlin.module.post.intf.PostDataIntf
import fungsiu.kotlin.module.post.repo.PostRepo
import fungsiu.kotlin.module.post.viewmodel.PostListViewModel
import fungsiu.kotlin.network.model.PostListResponse
import kotlinx.android.synthetic.main.activity_post_list.*

class PostListActivity : BaseActivity(), View.OnClickListener,
    PostListAdapter.ClickListener,
    PostDataIntf {
    private val request: ApiRequestIntf = PostRepo()
    private lateinit var viewModel: PostListViewModel
    private lateinit var binding: ActivityPostListBinding
    private var mAdapter = PostListAdapter()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_post_list)
        viewModel = PostListViewModel(request)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initList()
        viewModel.onRefresh(this)
    }

    private fun initList() {
        mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL
        mAdapter.setClickListener(this)
        with(binding.rvPostList) {
            adapter = mAdapter
            layoutManager = mLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    // if scroll to bottom, get next page
                    if (mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.itemCount - 1 &&
                        !viewModel.isLoading()
                    ) {
                        viewModel.onLoadMore(this@PostListActivity)
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            if (!viewModel.isLoading()) {
                viewModel.onRefresh(this)
            } else {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnA) {
            viewModel.setWord("A")
        } else if (v.id == R.id.btnB) {
            viewModel.setWord("B")
        }
    }

    override fun onReceive(response: List<PostListResponse.Post>?, start: Int) {
        updateUi(response, start)
    }

    private fun updateUi(response: List<PostListResponse.Post>?, start: Int) {
        swipeRefresh.isRefreshing = false
        mAdapter.setData(response)
        if (start == 0) {
            binding.rvPostList.adapter?.notifyDataSetChanged()
        } else {
            binding.rvPostList.adapter?.notifyItemChanged(start)
        }
    }

    override fun onItemClick(post: PostListResponse.Post?) {
        val intent = Intent(context, PostDetailActivity::class.java)
        intent.putExtra("id", post?.id)
        startActivity(intent)
    }

}