package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sarrawi.img.R
import com.sarrawi.img.databinding.ImgDesignBinding
import com.sarrawi.img.databinding.ImgDesignfavBinding
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel

class Fav_Adapter(val con: Context): RecyclerView.Adapter<Fav_Adapter.ViewHolder>() {

    private var isInternetConnected: Boolean = true
    var onItemClick: ((Int, Int) -> Unit)? = null
    var onbtnclick: ((item:FavoriteImage) -> Unit)? = null


    inner class ViewHolder(val binding: ImgDesignfavBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                //اذا كانت null سيتم استخدام 0؟
                onItemClick?.invoke(fav_img_list[layoutPosition].id ?: 0, layoutPosition ?: 0)
            }
            binding.imgFave.setOnClickListener {
                onbtnclick?.invoke(fav_img_list[adapterPosition])
            }

        }

        fun bind(position: Int, isInternetConnected: Boolean) {
            if (isInternetConnected) {
//                val current_imgModel = img_list[position]
//                val requestOptions = RequestOptions()
//                    .placeholder(R.drawable.nonet) // الصورة المؤقتة لحالة التحميل
//                    .error(R.drawable.error_a) // الصورة المعروضة في حالة حدوث خطأ أثناء التحميل
//                    .circleCrop() // تطبيق مؤثر القص للحصول على صورة دائرية
//                Glide.with(con)
//                    .load(current_imgModel.image_url)
//                    .apply(requestOptions)
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .circleCrop()
//                    .into(binding.imgadapterImgViewContent)
//                binding.lyNoInternet.visibility = View.GONE
//            } else {
//                // عند عدم وجود اتصال بالإنترنت، قم بعرض الـ lyNoInternet بدلاً من الصورة
//                binding.imgadapterImgViewContent.visibility = View.GONE
//                binding.lyNoInternet.visibility = View.VISIBLE
//            }
                val current_imgModel = fav_img_list[position]
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_baseline_autorenew_24) // الصورة المؤقتة لحالة التحميل
                    .error(R.drawable.error_a) // الصورة المعروضة في حالة حدوث خطأ أثناء التحميل
                Glide.with(con)
                    .load(current_imgModel.image_url)
                    .apply(requestOptions)
                    .circleCrop()
                    .into(binding.imgadapterImgViewContent)
                binding.lyNoInternet.visibility = View.GONE
            } else {
                // عند عدم وجود اتصال بالإنترنت، قم بعرض الـ lyNoInternet بدلاً من الصورة
                Glide.with(con)
                    .load(R.drawable.nonet) // تحميل صورة nonet.jpg
                    .into(binding.imgadapterImgViewContent)
                binding.imgadapterImgViewContent.visibility = View.GONE
                binding.lyNoInternet.visibility = View.VISIBLE
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<FavoriteImage>(){
        override fun areItemsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var fav_img_list: List<FavoriteImage>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(ImgDesignfavBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position,isInternetConnected)
    }

    override fun getItemCount(): Int {
        return fav_img_list.size
    }

    fun updateInternetStatus(isConnected: Boolean) {
        isInternetConnected = isConnected
        notifyDataSetChanged()
    }


}