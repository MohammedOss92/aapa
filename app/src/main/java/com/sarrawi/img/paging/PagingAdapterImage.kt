package com.sarrawi.img.paging



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sarrawi.img.R
import com.sarrawi.img.databinding.ImagedeaBinding
import com.sarrawi.img.model.ImgsModel

class PagingAdapterImage(val con: Context) : PagingDataAdapter<ImgsModel, PagingAdapterImage.ViewHolder>(COMPARATOR) {

    var onItemClick: ((Int, ImgsModel, Int) -> Unit)? = null
    var onbtnClick: ((ImgsModel, Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ImagedeaBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { item ->
                        onItemClick?.invoke(item.id ?: 0, item, position)

                    }
                }
            }

            binding.imgFave.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { item ->
                        onbtnClick?.invoke(item, position)
                    }
                }
            }
        }

        fun bind(item: ImgsModel?) {
            item ?: return // Handle null item
            // Bind your data to the ViewBinding elements here
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_baseline_autorenew_24)
                .error(R.drawable.error_a)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)

            Glide.with(binding.root.context)
                .load(item.image_url)
                .apply(requestOptions)
                .circleCrop()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgadapterImgViewContent)

            if (item.is_fav) {
                binding.imgFave.setImageResource(R.drawable.baseline_favorite_true)
            } else {
                binding.imgFave.setImageResource(R.drawable.baseline_favorite_border_false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImagedeaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }


    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<ImgsModel>() {
            override fun areItemsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

