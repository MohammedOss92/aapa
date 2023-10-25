package com.sarrawi.img.ui.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.adapter.ViewPagerAdapter
import com.sarrawi.img.databinding.FragmentPagerImgBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import com.sarrawi.img.db.viewModel.ViewModelFactory2
import com.sarrawi.img.model.ImgsModel
import kotlinx.coroutines.launch


class PagerFragmentImg : Fragment() {

    lateinit var _binding: FragmentPagerImgBinding
    private val binding get() = _binding

    private val retrofitService = ApiService.provideRetrofitInstance()

    private val mainRepository by lazy {
        ImgRepository(
            retrofitService,
            requireActivity().application
        )
    }

    private val imgsViewmodel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(), mainRepository)
    }

    private val a by lazy { FavoriteImageRepository(requireActivity().application) }

    private val imgsffav: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(a)
    }

    private val adapterpager by lazy {
        ViewPagerAdapter(requireActivity())
    }

    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }

    private var currentItemId = -1
    private var ID = -1
    var imgsmodel: ImgsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ID = PagerFragmentImgArgs.fromBundle(requireArguments()).id
//        currentItemId = PagerFragmentImgArgs.fromBundle(requireArguments()).currentItemId
//
//        imgsmodel?.image_url = PagerFragmentImgArgs.fromBundle(requireArguments()).imageUrl
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPagerImgBinding.inflate(inflater, container, false)



        imgsViewmodel.isConnected.observe(requireActivity()) { isConnected ->

            if (isConnected) {
//                  setUpViewPager()

//                binding.lyNoInternet.visibility = View.GONE

            } else {
//                     binding.progressBar.visibility = View.GONE
//                binding.lyNoInternet.visibility = View.VISIBLE

            }
        }
        imgsViewmodel.checkNetworkConnection(requireContext())
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setUpPager()
        setUpViewPager()
    }

    private fun setUpViewPager() =
        imgsViewmodel.viewModelScope.launch {
            imgsViewmodel.getAllImgsViewModel(ID).observe(requireActivity()) { imgs ->
                // print data
                if (imgs != null) {
                    adapterpager.img_list_Pager=imgs
                    binding.pagerimg.adapter =adapterpager
                    binding.pagerimg.setCurrentItem(currentItemId,false) // set for selected item
                    adapterpager.notifyDataSetChanged()

                }

                else {
                    // No data
                }

            }}


    private fun setUpPager() {
        if (isAdded) {
            imgsViewmodel.getAllImgsViewModel(ID).observe(viewLifecycleOwner) { imgs ->


                if (imgs.isEmpty()) {
                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
                    imgsViewmodel.getAllImgsViewModel(ID)
                } else {
                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView

                    // هنا قم بالحصول على البيانات المفضلة المحفوظة محليًا من ViewModel
                    favoriteImagesViewModel.getAllFav()
                        .observe(viewLifecycleOwner) { favoriteImages ->
                            val allImages: List<ImgsModel> = imgs

                            for (image in allImages) {
                                val isFavorite =
                                    favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
                                image.is_fav = isFavorite // قم بتحديث حالة الصورة
                            }

                            adapterpager.img_list_Pager = allImages

                            if (imgs != null) {
                                adapterpager.img_list_Pager=imgs
                                binding.pagerimg.adapter =adapterpager
                                binding.pagerimg.setCurrentItem(currentItemId,false) // set for selected item
                                binding.pagerimg.orientation=ViewPager2.ORIENTATION_VERTICAL
                                adapterpager.notifyDataSetChanged()

                            }

                            else {
                                // No data
                                adapterpager.notifyDataSetChanged()
                            }


//                            if (currentItemId != -1) {
//                                binding.pagerimg.scrollToPosition(currentItemId)
//                            }

                        }
                }


            }
        }
    }



}