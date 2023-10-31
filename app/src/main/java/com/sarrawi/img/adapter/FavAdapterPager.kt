package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sarrawi.img.R
import com.sarrawi.img.databinding.FavAdapterPagerBinding
import com.sarrawi.img.databinding.ImgDesignfavBinding
import com.sarrawi.img.model.FavoriteImage

class FavAdapterPager(val con: Context): RecyclerView.Adapter<FavAdapterPager.ViewHolder>() {

    var onbtnclick: ((item:FavoriteImage) -> Unit)? = null
    var onSaveImageClickListenerfp: OnSaveImageClickListenerfavp? = null

    inner class ViewHolder(val binding:FavAdapterPagerBinding):RecyclerView.ViewHolder(binding.root) {


        init {
            binding.imgFavepager.setOnClickListener {
//                onbtnclick?.invoke(fav_img_list[adapterPosition])
                onbtnclick?.invoke(fav_img_list_pager[bindingAdapterPosition])

            }
        }

        fun bind(position: Int) {

            val current_imgModel = fav_img_list_pager[position]
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_baseline_autorenew_24) // الصورة المؤقتة لحالة التحميل
                .error(R.drawable.error_a) // الصورة المعروضة في حالة حدوث خطأ أثناء التحميل
            Glide.with(con)
                .load(current_imgModel.image_url)
                .apply(requestOptions)
                .centerCrop()
                .into(binding.imageViewpager)

            binding.saveImgpager.setOnClickListener {
                onSaveImageClickListenerfp?.onSaveImageClickfp(adapterPosition)
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
    var fav_img_list_pager: List<FavoriteImage>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapterPager.ViewHolder {
        return  ViewHolder(FavAdapterPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return fav_img_list_pager.size
    }

    interface OnSaveImageClickListenerfavp {
        fun onSaveImageClickfp(position: Int)
    }
}