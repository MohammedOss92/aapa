package com.sarrawi.img.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sarrawi.img.R
import com.sarrawi.img.adapter.FavAdapterLinRecy
import com.sarrawi.img.databinding.ImgDesignfavBinding
import com.sarrawi.img.databinding.RowimagefavBinding
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel

class FavPagingAdapterImageLinear(val con: Context) : PagingDataAdapter<FavoriteImage, FavPagingAdapterImageLinear.ViewHolder>(Diff_Callback
) {

    var onItemClick: ((Int, FavoriteImage,Int) -> Unit)? = null
    var onbtnclick: ((item: FavoriteImage) -> Unit)? = null
    var onSaveImageClickListenerfav: OnSaveImageClickListenerfav? = null

    inner class ViewHolder(val binding: RowimagefavBinding):RecyclerView.ViewHolder(binding.root) {

        init{
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
                        onbtnclick?.invoke(item)
                    }
                }

            }

            binding.saveImg.setOnClickListener {
                val position = bindingAdapterPosition
                onSaveImageClickListenerfav?.onSaveImageClick(position)
            }
        }

        fun bind(item:FavoriteImage?) {
            item ?: return // Handle null item
            // Bind your data to the ViewBinding elements here
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_baseline_autorenew_24) // الصورة المؤقتة لحالة التحميل
                .error(R.drawable.error_a) // الصورة المعروضة في حالة حدوث خطأ أثناء التحميل
            Glide.with(con)
                .load(item.image_url)
                .apply(requestOptions)
                .circleCrop()
                .centerCrop()
                .into(binding.imageView)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowimagefavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnSaveImageClickListenerfav {
        fun onSaveImageClick(position: Int)
    }

    companion object {
        private val Diff_Callback = object : DiffUtil.ItemCallback<FavoriteImage>() {
            override fun areItemsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}