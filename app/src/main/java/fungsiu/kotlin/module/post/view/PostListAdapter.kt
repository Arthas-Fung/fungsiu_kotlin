package fungsiu.kotlin.module.post.view

import android.view.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.*
import fungsiu.kotlin.databinding.ItemLoadingBinding
import fungsiu.kotlin.databinding.ItemPostBinding
import fungsiu.kotlin.module.post.intf.ApiRequestIntf
import fungsiu.kotlin.module.post.repo.PostRepo
import fungsiu.kotlin.module.post.viewmodel.PostListViewModel
import fungsiu.kotlin.network.model.PostListResponse

class PostListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _list = MutableLiveData<List<PostListResponse.Post>?>()
    private val list: LiveData<List<PostListResponse.Post>?>
        get() = _list

    companion object {
        const val TYPE_LOADING = 0
        const val TYPE_POST = 1
    }

    //region click listener
    private lateinit var clickListener: ClickListener

    interface ClickListener {
        fun onItemClick(post: PostListResponse.Post?)
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }
    //endregion

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_LOADING
        } else {
            TYPE_POST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_POST -> {
                val binding = ItemPostBinding.inflate(inflater)
                PostViewHolder(binding)
            }
            else -> {
                val binding = ItemLoadingBinding.inflate(inflater)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list.value.isNullOrEmpty()) {
            0
        } else {
            list.value?.size?.plus(1) ?: 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != itemCount - 1) {
            val post = list.value?.get(position)
            when (holder) {
                is PostViewHolder -> holder.bind(post)
                is LoadingViewHolder -> holder.bind()
                else -> throw IllegalArgumentException()
            }
        }
    }

    fun setData(list: List<PostListResponse.Post>?) {
        _list.value = list
    }

    //region viewholder
    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        private val context = binding.rootView.context
        fun bind(post: PostListResponse.Post?) {
            val request: ApiRequestIntf = PostRepo()
            val viewModel = PostListViewModel(request)
            binding.vm = viewModel
            viewModel.setPost(post)

            binding.rootView.setOnClickListener{
                clickListener.onItemClick(post)
            }
        }
    }

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind() {

        }
    }
    //endregion

}