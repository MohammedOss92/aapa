package com.sarrawi.img

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.sarrawi.img.adapter.ViewPagerAdapter
import com.sarrawi.img.databinding.FragmentFourBinding
import com.sarrawi.img.utils.NetworkConnection

class FourFragment : Fragment() {

private lateinit var _binding: FragmentFourBinding

private val binding get() = _binding

private val retrofitService = ApiService.provideRetrofitInstance()

private val mainRepository by lazy {  ImgRepository(retrofitService) }

private val imgsViewmodel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(),mainRepository)
        }

    private val viewPagerAdapter by lazy {
    ViewPagerAdapter(requireActivity())
        }

    private var ID_Type_id = -1
    private  var currentItemId:Int=0;

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        _binding = FragmentFourBinding.inflate(inflater, container, false)

        return binding.root

        }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            ID_Type_id = FourFragmentArgs.fromBundle(requireArguments()).id
            currentItemId = FourFragmentArgs.fromBundle(requireArguments()).currentItemId

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         //

            // Live Connected
            imgsViewmodel.isConnected.observe(requireActivity()) {
                    isConnected ->

                if (isConnected) {
                  setUpViewPager()
                 binding.lyNoInternet.visibility = View.GONE
                  }
                else {
                     binding.progressBar.visibility = View.GONE
                    binding.lyNoInternet.visibility = View.VISIBLE

                 }
            }
            imgsViewmodel.checkNetworkConnection(requireContext())


         //   if (imgsViewmodel.isConnected){
           //     setUpViewPager()

         //   }else
         //   {
         //       binding.progressBar.visibility = View.GONE
         //       binding.lyNoInternet.visibility = View.VISIBLE
           //  }


            imgsViewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE // عرض ProgressBar إذا كان التحميل قيد التقدم
                } else {
                    binding.progressBar.visibility = View.GONE // إخفاء ProgressBar إذا انتهى التحميل
                }
            }


        }

    private fun setUpViewPager() =
        imgsViewmodel.viewModelScope.launch {
        imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
             // print data
            if (imgs != null) {
                viewPagerAdapter.img_list=imgs
                binding.viewpager.adapter =viewPagerAdapter
                binding.viewpager.setCurrentItem(currentItemId,false) // set for selected item
                viewPagerAdapter.notifyDataSetChanged()

            }

            else {
                // No data
             }

        }
    }





}



