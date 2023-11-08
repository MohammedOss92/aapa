package com.sarrawi.img.ui.fragFav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.adapter.Fav_Adapter
import com.sarrawi.img.databinding.FragmentFavoriteRecyBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory2
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.paging.Paging_Fav_Adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavoriteFragmentRecy : Fragment() {

    private lateinit var _binding: FragmentFavoriteRecyBinding

    private val binding get() = _binding

    private val a by lazy {  FavoriteImageRepository(requireActivity().application) }

    private val imgsffav: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(a)
    }
    private val favAdapter by lazy { Fav_Adapter(requireActivity()) }
    private val pagingfavAdapter by lazy { Paging_Fav_Adapter(requireActivity()) }

    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }

    private var ID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecyBinding.inflate(inflater,container,false)
        return  _binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
        adapterOnClick()
    }

    private fun setUpRvtwo(){
        if (isAdded) {
            // تعيين المدير التخطيط (GridLayout) لـ RecyclerView أولاً
            binding.recyclerFav.layoutManager = GridLayoutManager(requireContext(), 2)

            // تعيين المحمل للـ RecyclerView بعد تعيين المدير التخطيط
            binding.recyclerFav.adapter = pagingfavAdapter

            lifecycleScope.launch(Dispatchers.IO){
                favoriteImagesViewModel.getAllFav().collect{ imgs ->
                    // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط
                    favAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW




                }

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
            }

            // بعد ذلك، قم بتعيين البيانات باستخدام ViewModel و LiveData
//
//            imgsViewModel.getImgsData(ID, startIndex).observe(viewLifecycleOwner) {
//            favoriteImagesViewModel.getAllFava().observe(viewLifecycleOwner) {
//
//                pagingfavAdapter.submitData(viewLifecycleOwner.lifecycle, it)
//            }
            // اختيار دالة التعيين وضبط السياسة لـ RecyclerView
            pagingfavAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        }
    }

    private fun setUpRv() {
        if (isAdded) {
            lifecycleScope.launch(Dispatchers.Main) { // استخدام Dispatchers.Main لتحديث UI
                binding.recyclerFav.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recyclerFav.adapter = pagingfavAdapter
                favoriteImagesViewModel.getAllFav().collect {
                    // تم استدعاء الدالة على الخيط الرئيسي
                    pagingfavAdapter.submitData(it)

                }
                pagingfavAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            }
        }
    }

    private fun adapterOnClick() {
        pagingfavAdapter.onItemClick = { _, favimage: FavoriteImage, currentItemId ->

                val directions = FavoriteFragmentRecyDirections.actionFavoriteFragmentRecyToFavFragmentLinRecy(ID, currentItemId,favimage.image_url)
                findNavController().navigate(directions)

        }
        pagingfavAdapter.onbtnclick = {
            it.is_fav = false
            imgsffav.updateImages()
            imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))

            val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }
    }

