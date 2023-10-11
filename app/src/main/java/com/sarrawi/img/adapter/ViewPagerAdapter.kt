package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarrawi.img.R
import com.sarrawi.img.databinding.RowImagesBinding

import com.sarrawi.img.model.ImgsModel

class ViewPagerAdapter(val con: Context):
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var onbtnClick: ((item:ImgsModel,position:Int) -> Unit)? = null
    private var isToolbarVisible = true


    inner class ViewHolder(val binding:RowImagesBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(img_list[layoutPosition].id?:0)
            }




        }


        fun bind(position: Int) {

            val current_imgModel = img_list[position]
            Glide.with(con)
                .load(current_imgModel.image_url)
                .into(binding.imageView)
            binding.apply {
             if(current_imgModel.is_fav){
                imgFave.setImageResource(R.drawable.baseline_favorite_true)
             }else{
                 imgFave.setImageResource(R.drawable.baseline_favorite_border_false)
             }

            }
            binding.imgFave.setOnClickListener {
                onbtnClick?.invoke(img_list[position],position)
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
    var img_list: List<ImgsModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(RowImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.binding.imageView.setOnClickListener {
//            // عند النقر على الصورة، قم بتبديل حالة الشريط العلوي
//            isToolbarVisible = !isToolbarVisible
//
//            // يمكنك استخدام واجهة المستخدم الخاصة بك لإظهار أو إخفاء الشريط العلوي
//            // على سبيل المثال، إذا كنت تستخدم AppCompatActivity:
////            if (isToolbarVisible) {
////                (con as AppCompatActivity).supportActionBar?.show()
////
////            } else {
////                (con as AppCompatActivity).supportActionBar?.hide()
////            }
//
//            val uiFlags = if (isToolbarVisible) {
//                // إذا كان شريط الأدوات مرئيًا
//                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//            } else {
//                // إذا تم إخفاء شريط الأدوات
//                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//            }
//
//            (con as AppCompatActivity).window.decorView.systemUiVisibility = uiFlags
//            (con as AppCompatActivity).supportActionBar?.apply {
//                if (isToolbarVisible) {
//                    show()
//                } else {
//                    hide()
//                }
//
//            }
//        }
        var isSystemUIVisible = true // تعيينها إلى true في البداية

//        holder.binding.imageView.setOnClickListener {
//            isSystemUIVisible = !isSystemUIVisible // قم بتبديل حالة الظهور للنظام
//
//            if (isSystemUIVisible) {
//                // إذا تم إظهار النظام، قم بإظهار شريط العنوان وشريط الملاحة
//                (con as AppCompatActivity).window.decorView.systemUiVisibility = (
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_VISIBLE // إظهار النظام
//                        )
//                (con as AppCompatActivity).supportActionBar?.show()
//            } else {
//                // إذا تم إخفاء النظام، قم بإخفاء شريط العنوان وشريط الملاحة
//                (con as AppCompatActivity).window.decorView.systemUiVisibility = (
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        )
//                (con as AppCompatActivity).supportActionBar?.hide()
//            }
//        }

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return img_list.size
    }


}