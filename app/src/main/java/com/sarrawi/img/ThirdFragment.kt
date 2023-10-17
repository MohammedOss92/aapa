package com.sarrawi.img

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.adapter.ImgAdapter
import com.sarrawi.img.db.repository.ImgRepository
import kotlinx.coroutines.launch
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.databinding.FragmentThirdBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.*
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel

class ThirdFragment : Fragment() {

    private lateinit var _binding: FragmentThirdBinding
    private val binding get() = _binding
    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository by lazy { ImgRepository(retrofitService,requireActivity().application) }
    private val imgsViewModel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(), mainRepository)
    }
    private val imgAdapter by lazy { ImgAdapter(requireActivity()) }
    private var ID_Type_id = -1
    private var recyclerViewState: Parcelable? = null
//    private var customScrollState = CustomScrollState()

    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }

    private val a by lazy {  FavoriteImageRepository(requireActivity().application) }
    private val imgsffav: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(a)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ID_Type_id = ThirdFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // حفظ حالة التمرير
//        recyclerViewState = binding.rvImgCont.layoutManager?.onSaveInstanceState()
//        outState.putParcelable("recycler_state", recyclerViewState)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        savedInstanceState?.let { bundle ->
//            // استعادة حالة التمرير
//            recyclerViewState = bundle.getParcelable("recycler_state")
//        }
//
//        val layoutManager = binding.rvImgCont.layoutManager
//        if (layoutManager is GridLayoutManager) {
//            layoutManager.scrollToPosition(customScrollState.scrollPosition)
//        }

        imgsViewModel.isConnected.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                setUpRv()
                adapterOnClick()
                imgAdapter.updateInternetStatus(isConnected)
                binding.lyNoInternet.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.lyNoInternet.visibility = View.VISIBLE
                imgAdapter.updateInternetStatus(isConnected)
            }
        }

        imgsViewModel.checkNetworkConnection(requireContext())

        imgsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
//        val layoutManager = binding.rvImgCont.layoutManager
//        if (layoutManager is LinearLayoutManager) {
//            customScrollState.scrollPosition = layoutManager.findFirstVisibleItemPosition()
//        }
    }

//    private fun setUpRv() = lifecycleScope.launch {
//        imgsViewModel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
//            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//            if (imgs.isEmpty()) {
//                // إذا كانت القائمة فارغة، قم بتحميل البيانات من الخادم مرة أخرى
//                imgsViewModel.getAllImgsViewModel(ID_Type_id)
//            }
//            else{
//
//            imgAdapter.img_list = imgs
//
//            if (binding.rvImgCont.adapter == null) {
//                binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 3)
//                binding.rvImgCont.adapter = imgAdapter
//            } else {
//                imgAdapter.notifyDataSetChanged()
//            }
//
//            imgAdapter.onItemClick = { _, currentItemId ->
//                if (imgsViewModel.isConnected.value == true) {
//                    val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
//                    findNavController().navigate(directions)
//                } else {
//                    val snackbar = Snackbar.make(
//                        requireView(),
//                        "لا يوجد اتصال بالإنترنت",
//                        Snackbar.LENGTH_SHORT
//                    )
//                    snackbar.show()
//                }
//            }
//        }
//        }
//    }

//    private fun setUpRv() = lifecycleScope.launch {
//        imgsViewModel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
//            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//            if (imgs.isEmpty()) {
//                // إذا كانت القائمة فارغة، قم بتحميل البيانات من الخادم مرة أخرى
//                imgsViewModel.getAllImgsViewModel(ID_Type_id)
//            } else {
//                // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
//                imgAdapter.img_list = imgs
//                if (binding.rvImgCont.adapter == null) {
//                    binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 3)
//                    binding.rvImgCont.adapter = imgAdapter
//                } else {
//                    imgAdapter.notifyDataSetChanged()
//                }
//            }
//
//            imgAdapter.onItemClick = { _, currentItemId ->
//                if (imgsViewModel.isConnected.value == true) {
//                    val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
//                    findNavController().navigate(directions)
//                } else {
//                    val snackbar = Snackbar.make(
//                        requireView(),
//                        "لا يوجد اتصال بالإنترنت",
//                        Snackbar.LENGTH_SHORT
//                    )
//                    snackbar.show()
//                }
//            }
//        }
//    }
//    private fun setUpRv() {
//        if (isAdded) {
//            imgsViewModel.getAllImgsViewModel(ID_Type_id).observe(viewLifecycleOwner) { imgs ->
//                // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط
//                imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//                if (imgs.isEmpty()) {
//                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
//                    imgsViewModel.getAllImgsViewModel(ID_Type_id)
//                } else {
//                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
//                    imgAdapter.img_list = imgs
//                    if (binding.rvImgCont.adapter == null) {
//                        binding.rvImgCont.layoutManager = LinearLayoutManager(requireContext())
//                        binding.rvImgCont.adapter = imgAdapter
//                    } else {
//                        imgAdapter.notifyDataSetChanged()
//                    }
//                }
//
//                imgAdapter.onItemClick = { _, currentItemId ->
//                    if (imgsViewModel.isConnected.value == true) {
//                        val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
//                        findNavController().navigate(directions)
//                    } else {
//                        val snackbar = Snackbar.make(
//                            requireView(),
//                            "لا يوجد اتصال بالإنترنت",
//                            Snackbar.LENGTH_SHORT
//                        )
//                        snackbar.show()
//                    }
//                }
//            }
//        }
//    }

    private fun setUpRv() {
        if (isAdded) {
            imgsViewModel.getAllImgsViewModel(ID_Type_id).observe(viewLifecycleOwner) { imgs ->
                imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

                if (imgs.isEmpty()) {
                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
                    imgsViewModel.getAllImgsViewModel(ID_Type_id)
                } else {
                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView

                    // هنا قم بالحصول على البيانات المفضلة المحفوظة محليًا من ViewModel
                    favoriteImagesViewModel.getAllFav().observe(viewLifecycleOwner) { favoriteImages ->
                        val allImages: List<ImgsModel> = imgs

                        for (image in allImages) {
                            val isFavorite = favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
                            image.is_fav = isFavorite // قم بتحديث حالة الصورة
                        }

                        imgAdapter.img_list = allImages

                        if (binding.rvImgCont.adapter == null) {
                            binding.rvImgCont.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvImgCont.adapter = imgAdapter
                        } else {
                            imgAdapter.notifyDataSetChanged()
                        }
                    }
                }

                imgAdapter.onItemClick = { _, currentItemId ->
                    if (imgsViewModel.isConnected.value == true) {
                        val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
                        findNavController().navigate(directions)
                    } else {
                        val snackbar = Snackbar.make(
                            requireView(),
                            "لا يوجد اتصال بالإنترنت",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                    }
                }
            }
        }
    }



    private fun adapterOnClick() {

        imgAdapter.onItemClick = { _, currentItemId ->
            if (imgsViewModel.isConnected.value == true) {
                val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
                findNavController().navigate(directions)
            } else {
                val snackbar = Snackbar.make(
                    requireView(),
                    "لا يوجد اتصال بالإنترنت",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        }

//        imgAdapter.onbtnClick = { it: ImgsModel, i:Int ->
//            val fav= FavoriteImage(it.id!!,it.ID_Type_id,it.new_img,it.image_url)
//
//            println("it.is_fav: ${it.is_fav}")
//            if (it.is_fav) {
//                it.is_fav = false
//                imgsffav.removeFavoriteImage(fav)
//
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
//                snackbar.show()
////                setUpViewPager()
//
//                imgAdapter.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//            }
//
//            else{
//                it.is_fav = true
//                imgsffav.addFavoriteImage(fav)
//
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الاضافة", Snackbar.LENGTH_SHORT)
//                snackbar.show()
////                setUpViewPager()
//
//                imgAdapter.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//            }
//            // تحقق من قيمة it.is_fav
//            println("it.is_fav: ${it.is_fav}")
////            setUpViewPager()
//
//            imgAdapter.notifyDataSetChanged()
//            println("it.is_fav: ${it.is_fav}")
//        }

//        imgAdapter.onbtnClick = { it: ImgsModel, i:Int ->
//            val fav = FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url)
//
//            // قم بالحصول على مشغل SharedPreferences
//            val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//
//            println("it.is_fav: ${it.is_fav}")
//            if (it.is_fav) {
//                sharedPreferences.edit().putBoolean("is_fav_${it.id}", false).apply()
//
//                it.is_fav = false
//                imgsffav.removeFavoriteImage(fav)
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//                imgAdapter.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//
//
//            } else {
//                sharedPreferences.edit().putBoolean("is_fav_${it.id}", true).apply()
//                it.is_fav = true
//                imgsffav.addFavoriteImage(fav)
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الاضافة", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//                imgAdapter.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//            }
//
//            // حفظ قيمة is_fav في SharedPreferences
////            sharedPreferences.edit().putBoolean("is_fav_${it.id}", it.is_fav).apply()
//
//            // تحقق من قيمة it.is_fav
//            println("it.is_fav: ${it.is_fav}")
//        }

//        imgAdapter.onbtnClick = { it: ImgsModel, i:Int ->
//            // قم بالحصول على مشغل SharedPreferences
//            val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//
//            // استرجاع القيمة is_fav من SharedPreferences
//            val isFav = sharedPreferences.getBoolean("is_fav_${it.id}", false)
//
//            // تحديث قيمة is_fav في كائن ImgsModel
//            it.is_fav = isFav
//
//            if (it.is_fav) {
//                // إذا كانت الصورة مفضلة، قم بإلغاء الإعجاب بها
//                sharedPreferences.edit().putBoolean("is_fav_${it.id}", false).apply()
//                it.is_fav = false
//                imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//            } else {
//                // إذا لم تكن الصورة مفضلة، قم بإضافتها للمفضلة
//                sharedPreferences.edit().putBoolean("is_fav_${it.id}", true).apply()
//                it.is_fav = true
//                imgsffav.addFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الإضافة", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//            }
//
//            // تحقق من قيمة it.is_fav
//            println("it.is_fav: ${it.is_fav}")
//
//            // تحديث RecyclerView Adapter
//            imgAdapter.notifyDataSetChanged()
//        }
//ss
        imgAdapter.onbtnClick = { it: ImgsModel, i:Int ->

            if (it.is_fav) {
                // إذا كانت الصورة مفضلة، قم بإلغاء الإعجاب بها
                it.is_fav = false
                imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))

                imgsffav.updateImages()
                imgsffav.getFavByIDModels(it.id!!)
                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
                snackbar.show()
            } else {
                // إذا لم تكن الصورة مفضلة، قم بإضافتها للمفضلة
                it.is_fav = true
                imgsffav.addFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))

                imgsffav.updateImages()
                imgsffav.getFavByIDModels(it.id!!)
                val snackbar = Snackbar.make(view!!, "تم الإضافة", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            // تحقق من قيمة it.is_fav
            println("it.is_fav: ${it.is_fav}")
            // تحديث RecyclerView Adapter
            imgAdapter.notifyDataSetChanged()
        }


    }

}
