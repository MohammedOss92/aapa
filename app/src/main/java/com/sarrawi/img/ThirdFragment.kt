package com.sarrawi.img

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
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
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
import com.sarrawi.img.db.viewModel.SharedViewModel

class ThirdFragment : Fragment() {

    private lateinit var _binding: FragmentThirdBinding
    private val binding get() = _binding
    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository by lazy { ImgRepository(retrofitService) }
    private val imgsViewModel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(), mainRepository)
    }
    private val imgAdapter by lazy { ImgAdapter(requireActivity()) }
    private var ID_Type_id = -1
    private var recyclerViewState: Parcelable? = null
    private var customScrollState = CustomScrollState()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

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
        recyclerViewState = binding.rvImgCont.layoutManager?.onSaveInstanceState()
        outState.putParcelable("recycler_state", recyclerViewState)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.imgsLiveData.observe(viewLifecycleOwner) { imgs ->
            // هنا يمكنك استخدام البيانات imgs التي تم الحصول عليها من FragOne
        }

        savedInstanceState?.let { bundle ->
            // استعادة حالة التمرير
            recyclerViewState = bundle.getParcelable("recycler_state")
        }

        val layoutManager = binding.rvImgCont.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.scrollToPosition(customScrollState.scrollPosition)
        }

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
        val layoutManager = binding.rvImgCont.layoutManager
        if (layoutManager is LinearLayoutManager) {
            customScrollState.scrollPosition = layoutManager.findFirstVisibleItemPosition()
        }
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

    private fun setUpRv() = lifecycleScope.launch {
        imgsViewModel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            if (imgs.isEmpty()) {
                // إذا كانت القائمة فارغة، قم بتحميل البيانات من الخادم مرة أخرى
                imgsViewModel.getAllImgsViewModel(ID_Type_id)
            } else {
                // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
                imgAdapter.img_list = imgs
                if (binding.rvImgCont.adapter == null) {
                    binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 3)
                    binding.rvImgCont.adapter = imgAdapter
                } else {
                    imgAdapter.notifyDataSetChanged()
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
    }
}
