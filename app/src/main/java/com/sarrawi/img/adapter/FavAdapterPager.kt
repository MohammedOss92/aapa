package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.databinding.FavAdapterPagerBinding
import com.sarrawi.img.databinding.ImgDesignfavBinding
import com.sarrawi.img.model.FavoriteImage

class FavAdapterPager(val con: Context): RecyclerView.Adapter<FavAdapterPager.ViewHolder>() {


    inner class ViewHolder(val binding:FavAdapterPagerBinding):RecyclerView.ViewHolder(binding.root) {

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

    }

    override fun getItemCount(): Int {
        return fav_img_list_pager.size
    }
}