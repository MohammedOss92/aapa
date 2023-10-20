package com.sarrawi.img.ui.fragFav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.adapter.ViewPagerAdapter
import com.sarrawi.img.databinding.FragmentFavoritePagerBinding
import com.sarrawi.img.databinding.FragmentFavoriteRecyBinding
import com.sarrawi.img.databinding.FragmentFourBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory2
import kotlinx.coroutines.launch


class FavoritePagerFrag : Fragment() {
    private lateinit var _binding: FragmentFavoritePagerBinding

    private val binding get() = _binding
    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }


    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(requireActivity())
    }

    private var ID_Type_id = -1
    private  var currentItemId:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePagerBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
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

}