package com.sarrawi.img.ui.fragFav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.Person.fromBundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.adapter.FavAdapterPager
import com.sarrawi.img.adapter.ViewPagerAdapter
import com.sarrawi.img.databinding.FragmentFavoritePagerBinding
import com.sarrawi.img.databinding.FragmentFavoriteRecyBinding
import com.sarrawi.img.databinding.FragmentFourBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory2
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel
import kotlinx.coroutines.launch


class FavoritePagerFrag : Fragment() {
    private lateinit var _binding: FragmentFavoritePagerBinding

    private val binding get() = _binding
    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }

    private val adapterpager by lazy {
        FavAdapterPager(requireActivity())
    }


    private var ID = -1
    private  var currentItemId:Int=0
    var imgsmodel: ImgsModel? = null // تهيئة المتغير كاختياري مع قيمة ابتدائية

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ID = FavoritePagerFragArgs.fromBundle(requireArguments()).id
//        currentItemId = FavoritePagerFragArgs.fromBundle(requireArguments()).currentItemId
//
//        imgsmodel?.image_url = FavoritePagerFragArgs.fromBundle(requireArguments()).imageUrl
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePagerBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()

    }

    private fun setUpRv() {
        if (isAdded) {
            favoriteImagesViewModel.getAllFav().observe(viewLifecycleOwner) { imgs ->
                // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط

                if (imgs != null) {
                    adapterpager.fav_img_list_pager=imgs
                    binding.pagerFav.adapter =adapterpager
                    binding.pagerFav.setCurrentItem(currentItemId,false) // set for selected item
                    adapterpager.notifyDataSetChanged()

                }

                else {
                    // No data
                }

            }}







        }
    }


//    private fun setUpViewPager() =
//        favoriteImagesViewModel.viewModelScope.launch {
//            favoriteImagesViewModel.getAllFav().observe(requireActivity()) { imgs ->
//                // print data
//                if (imgs != null) {
//                    viewPagerAdapter.img_list=imgs
//                    binding.viewpager.adapter =viewPagerAdapter
//                    binding.viewpager.setCurrentItem(currentItemId,false) // set for selected item
//                    viewPagerAdapter.notifyDataSetChanged()
//
//                }
//
//                else {
//                    // No data
//                }
//
//            }}

