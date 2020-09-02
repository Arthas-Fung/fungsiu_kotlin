package fungsiu.kotlin.module.post.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import fungsiu.kotlin.R
import fungsiu.kotlin.base.BaseActivity
import fungsiu.kotlin.databinding.ActivityPostDetailBinding
import fungsiu.kotlin.module.post.viewmodel.PostDetailViewModel
import fungsiu.kotlin.network.model.PostDetailResponse

class PostDetailActivity : BaseActivity(), PostDetailViewModel.ApiResponse {
    private var viewModel: PostDetailViewModel = PostDetailViewModel()
    private lateinit var binding: ActivityPostDetailBinding

    private var id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_post_detail)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initIntent()
        viewModel.getPostDetail(this, id)
    }

    private fun initIntent() {
        id = intent.extras?.getInt("id", 0)
    }

    override fun onReceive(response: PostDetailResponse.Post?) {
        updateUi(response)
    }

    private fun updateUi(response: PostDetailResponse.Post?) {

    }

}