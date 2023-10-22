package com.sarrawi.img.ui.fragFav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.adapter.FavAdapterLinRecy
import com.sarrawi.img.databinding.FragmentFavLinRecyBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory2
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel


class FavFragmentLinRecy : Fragment() {
    private lateinit var _binding: FragmentFavLinRecyBinding

    private val binding get() = _binding

    private val a by lazy {  FavoriteImageRepository(requireActivity().application) }

    private val imgsffav: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(a)
    }
    private val favAdapterLinRecy by lazy { FavAdapterLinRecy(requireActivity()) }

    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }

    private var ID = -1
    var idd = -1
    private var ID_Type_id = -1
    private var currentItemId = -1
    private  var newimage:Int= -1
    private lateinit var imageUrl : String
    var imgsmodel: ImgsModel? = null // تهيئة المتغير كاختياري مع قيمة ابتدائية

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ID = FavFragmentLinRecyArgs.fromBundle(requireArguments()).id
        currentItemId = FavFragmentLinRecyArgs.fromBundle(requireArguments()).currentItemId

        imgsmodel?.image_url = FavFragmentLinRecyArgs.fromBundle(requireArguments()).imageUrl
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavLinRecyBinding.inflate(inflater,container,false)
        return  _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
        adapterOnClick()
    }

    private fun setUpRv() {
        if (isAdded) {
            favoriteImagesViewModel.getAllFav().observe(viewLifecycleOwner) { imgs ->
                // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط
                favAdapterLinRecy.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

                favAdapterLinRecy.fav_img_list = imgs
                if(binding.recyclerFav.adapter == null){

                    binding.recyclerFav.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerFav.adapter = favAdapterLinRecy
                    favAdapterLinRecy.notifyDataSetChanged()
                }else{
                    favAdapterLinRecy.notifyDataSetChanged()
                }
                if (currentItemId != -1) {
                    binding.recyclerFav.scrollToPosition(currentItemId)
                }


            }

            favAdapterLinRecy.onbtnclick = {
                it.is_fav = false
                imgsffav.updateImages()
                imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))

                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

    private fun adapterOnClick() {

        favAdapterLinRecy.onbtnclick = {
            it.is_fav = false
            imgsffav.updateImages()
            imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))

            val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

}