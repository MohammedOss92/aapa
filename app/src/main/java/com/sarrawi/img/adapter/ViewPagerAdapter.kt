package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.R
import com.sarrawi.img.databinding.ImgPagerBinding
import com.sarrawi.img.model.ImgsModel

class ViewPagerAdapter (val con: Context):RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private var isInternetConnected: Boolean = true
    var onbtnClick: ((item: ImgsModel, position:Int) -> Unit)? = null
    var onSaveImageClickListenerp: OnSaveImageClickListenerp? = null
    val displayMetrics = con.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    val screenHeight = displayMetrics.heightPixels

    // قم بتحديد القيم المطلوبة للصورة
    val targetWidth = screenWidth / 2 // على سبيل المثال، يمكنك تحديد العرض إلى نصف عرض الشاشة
    val targetHeight = screenHeight / 2 // على سبيل المثال، يمكنك تحديد الارتفاع إلى نصف ارتفاع الشاشة


    inner class ViewHolder(val binding:ImgPagerBinding): RecyclerView.ViewHolder(binding.root){

        init {
            if(isInternetConnected) {
                binding.imgFavepager.setOnClickListener {
                    onbtnClick?.invoke(img_list_Pager[position], position)
                }

                binding.saveImgpager.setOnClickListener {
                    onSaveImageClickListenerp?.onSaveImageClickp(adapterPosition)
                }
            }
            else{

                binding.imgFavepager.setOnClickListener {
                    val snackbar = Snackbar.make(it,"لا يوجد اتصال بالإنترنت", Snackbar.LENGTH_SHORT)
                    snackbar.show()                }

                binding.saveImgpager.setOnClickListener {
                    val snackbar = Snackbar.make(it,"لا يوجد اتصال بالإنترنت", Snackbar.LENGTH_SHORT)
                    snackbar.show()                }

                binding.root.setOnClickListener{
//                        Toast.makeText(con,"ghghg",Toast.LENGTH_SHORT).show()
                    val snackbar = Snackbar.make(it,"لا يوجد اتصال بالإنترنت", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }



            }



        }

        fun bind(position: Int, isInternetConnected: Boolean) {
            if (isInternetConnected) {
                val current_imgModel = img_list_Pager[position]
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_baseline_autorenew_24)
                    .error(R.drawable.error_a)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)

                Glide.with(con)
                    .asBitmap() // تحميل الصورة كـ Bitmap
                    .load(current_imgModel.image_url)
                    .apply(requestOptions)
                    .override(targetWidth, targetHeight)
                    .circleCrop()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageViewpager)
                binding.lyNoInternet.visibility = View.GONE





                binding.lyNoInternet.visibility = View.GONE

                binding.apply {
                    if(current_imgModel.is_fav){
                        imgFavepager.setImageResource(R.drawable.baseline_favorite_true)
                    }else{
                        imgFavepager.setImageResource(R.drawable.baseline_favorite_border_false)
                    }

                }
            } else {
                // عند عدم وجود اتصال بالإنترنت، قم بعرض الـ lyNoInternet بدلاً من الصورة
                Glide.with(con)
                    .load(R.drawable.nonet) // تحميل صورة nonet.jpg
                    .into(binding.imageViewpager)
                binding.imageViewpager.visibility = View.GONE
                binding.lyNoInternet.visibility = View.VISIBLE
            }




        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<ImgsModel>(){
        override fun areItemsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var img_list_Pager: List<ImgsModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewHolder {
        return  ViewHolder(ImgPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewHolder, position: Int) {

        holder.bind(position,isInternetConnected)

    }

    override fun getItemCount(): Int {
        return img_list_Pager.size
    }

    fun updateInternetStatus(isConnected: Boolean) {
        isInternetConnected = isConnected
        notifyDataSetChanged()
    }

    interface OnSaveImageClickListenerp {
        fun onSaveImageClickp(position: Int)
    }

}