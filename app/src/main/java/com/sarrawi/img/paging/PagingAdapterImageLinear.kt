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
import com.sarrawi.img.databinding.RowImagesBinding
import com.sarrawi.img.model.ImgsModel

class PagingAdapterImageLinear(val con: Context) : PagingDataAdapter<ImgsModel, PagingAdapterImageLinear.ViewHolder>(COMPARATOR)
{


        var onItemClick: ((Int, ImgsModel, Int) -> Unit)? = null
        var onbtnClick: ((item: ImgsModel, position: Int) -> Unit)? = null
        var onSaveImageClickListener: OnSaveImageClickListener? = null
        private var isInternetConnected: Boolean = true

        inner class ViewHolder(val binding: RowImagesBinding) : RecyclerView.ViewHolder(binding.root) {
            init {
                if (isInternetConnected) {
                    binding.root.setOnClickListener {
                        val position = bindingAdapterPosition
                        val imgModel = getItem(position)
                        imgModel?.let {
                            onItemClick?.invoke(it.id ?: 0, it, position)
                        }
                    }

                    binding.imgFave.setOnClickListener {
                        val position = bindingAdapterPosition
                        val imgModel = getItem(position)
                        imgModel?.let {
                            onbtnClick?.invoke(it, position)
                        }
                    }

                    binding.saveImg.setOnClickListener {
                        val position = bindingAdapterPosition
                        onSaveImageClickListener?.onSaveImageClick(position)
                    }
                }
                else {
                    // Snackbar code for no internet
                }
            }

            fun bind(position: Int, isInternetConnected: Boolean) {
                val current_imgModel = getItem(position)
                if (isInternetConnected) {
                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.ic_baseline_autorenew_24)
                        .error(R.drawable.error_a)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)

                    Glide.with(con)
                        .load(current_imgModel?.image_url)
                        .apply(requestOptions)
                        .circleCrop()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imageView)

                    binding.lyNoInternet.visibility = ViewGroup.GONE

                    binding.apply {
                        if (current_imgModel?.is_fav == true) {
                            imgFave.setImageResource(R.drawable.baseline_favorite_true)
                        } else {
                            imgFave.setImageResource(R.drawable.baseline_favorite_border_false)
                        }
                    }
                } else {
                    Glide.with(con)
                        .load(R.drawable.nonet)
                        .into(binding.imageView)
                    binding.imageView.visibility = ViewGroup.GONE
                    binding.lyNoInternet.visibility = ViewGroup.VISIBLE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(RowImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position, isInternetConnected)
        }

        interface OnSaveImageClickListener {
            fun onSaveImageClick(position: Int)
        }

        companion object {
            private val COMPARATOR = object : DiffUtil.ItemCallback<ImgsModel>() {
                override fun areItemsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
                    return oldItem?.id == newItem?.id
                }

                override fun areContentsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

