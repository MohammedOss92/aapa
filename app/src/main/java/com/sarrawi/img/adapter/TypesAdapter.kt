package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sarrawi.img.R
import com.sarrawi.img.databinding.TypesDesignBinding
import com.sarrawi.img.model.Img_Types_model

class TypesAdapter(val con: Context): RecyclerView.Adapter<TypesAdapter.ViewHolder>() {

    private val itemHeights = arrayOf("short", "long")

    inner class ViewHolder(val binding: TypesDesignBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val current_imgTypes=imgTypes_list[position]
            binding.apply {
                tvCategory.text=current_imgTypes.ImgTypes

                // تمييز العناصر بناءً على موقعها
                val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                if (position % 2 == 0) {
                    // هذه العناصر ستكون قصيرة
                    layoutParams.width = con.resources.getDimensionPixelSize(R.dimen.short_height)
                } else {
                    // هذه العناصر ستكون طويلة
                    layoutParams.height = con.resources.getDimensionPixelSize(R.dimen.long_height)
                }
                itemView.layoutParams = layoutParams
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Img_Types_model>(){
        override fun areItemsTheSame(oldItem: Img_Types_model, newItem: Img_Types_model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Img_Types_model, newItem: Img_Types_model): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var imgTypes_list: List<Img_Types_model>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(TypesDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return imgTypes_list.size
    }
}