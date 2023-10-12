package com.sarrawi.img

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.adapter.Fav_Adapter
import com.sarrawi.img.adapter.ImgAdapter
import com.sarrawi.img.databinding.FragmentFavoriteRecyBinding
import com.sarrawi.img.databinding.FragmentFourBinding
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.FavoriteImagesViewModel
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import com.sarrawi.img.db.viewModel.ViewModelFactory2


class FavoriteFragmentRecy : Fragment() {

    private lateinit var _binding: FragmentFavoriteRecyBinding

    private val binding get() = _binding


    private val favAdapter by lazy { Fav_Adapter(requireActivity()) }

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

    private fun setUpRv() {
        if (isAdded) {
            favoriteImagesViewModel.getAllFav().observe(viewLifecycleOwner) { imgs ->
                // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط
                favAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

                favAdapter.fav_img_list = imgs
                if(binding.recyclerFav.adapter == null){

                    binding.recyclerFav.layoutManager = GridLayoutManager(requireContext(), 3)
                    binding.recyclerFav.adapter = favAdapter
                    favAdapter.notifyDataSetChanged()
                }else{
                    favAdapter.notifyDataSetChanged()
                }


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
        }

    private fun adapterOnClick() {
        favAdapter.onItemClick = { _, currentItemId ->

//                val directions = FavoriteFragmentRecyDirections.actionFavoriteFragmentRecyToFourFragment(ID, currentItemId)
//                findNavController().navigate(directions)

        }
    }
    }

