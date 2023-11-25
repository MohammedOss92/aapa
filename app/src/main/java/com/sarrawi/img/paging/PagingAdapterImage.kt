package com.sarrawi.img.paging



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.R
import com.sarrawi.img.databinding.ImagedeaBinding
import com.sarrawi.img.model.ImgsModel

class PagingAdapterImage(val con: Context, private val onClickListener: OnClickListener) : PagingDataAdapter<ImgsModel, PagingAdapterImage.ViewHolder>(COMPARATOR) {

    var onItemClick: ((Int, ImgsModel, Int) -> Unit)? = null
    var onbtnClick: ((ImgsModel, Int) -> Unit)? = null
    private var isInternetConnected: Boolean = true


    inner class ViewHolder(private val binding: ImagedeaBinding) : RecyclerView.ViewHolder(binding.root) {
        init {


            if(isInternetConnected) {


//                binding.root.setOnClickListener {
//                    val position = bindingAdapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        getItem(position)?.let { item ->
//                            onItemClick?.invoke(item.id ?: 0, item, position)
//
//                        }
//                    }
//                }

                binding.imgFave.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        getItem(position)?.let { item ->
                            onbtnClick?.invoke(item, position)
                        }
                    }
                }
            }
            else{
//                binding.root.setOnClickListener{
////                        Toast.makeText(con,"ghghg",Toast.LENGTH_SHORT).show()
//                    val snackbar = Snackbar.make(it,"لا يوجد اتصال بالإنترنت", Snackbar.LENGTH_SHORT)
//                    snackbar.show()
//                }

                binding.imgFave.setOnClickListener {
                    val snackbar = Snackbar.make(it,"لا يوجد اتصال بالإنترنت", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }

            }
        }

        fun bind(item: ImgsModel?,isInternetConnected: Boolean,onClickListener: OnClickListener) {
            item ?: return // Handle null item
            // Bind your data to the ViewBinding elements here
            if (isInternetConnected) {
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_baseline_autorenew_24)
                    .error(R.drawable.error_a)
                    .format(DecodeFormat.PREFER_RGB_565)
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
            else {
                // عند عدم وجود اتصال بالإنترنت، قم بعرض الـ lyNoInternet بدلاً من الصورة
                Glide.with(con)
                    .load(R.drawable.nonet) // تحميل صورة nonet.jpg
                    .into(binding.imgadapterImgViewContent)
                binding.imgadapterImgViewContent.visibility = View.GONE
                binding.lyNoInternet.visibility = View.VISIBLE
            }
            binding.root.setOnClickListener{
                onClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImagedeaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position),isInternetConnected,onClickListener)
        val listener = onClickListener
        if (listener != null) {
            holder.bind(getItem(position), isInternetConnected, listener)
        }

    }

    fun updateInternetStatus(isConnected: Boolean) {
        isInternetConnected = isConnected
        notifyDataSetChanged()
    }
    class OnClickListener(val clickListener: (imgsModel: ImgsModel) -> Unit) {
        fun onClick(imgsModel: ImgsModel) = clickListener(imgsModel)
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

