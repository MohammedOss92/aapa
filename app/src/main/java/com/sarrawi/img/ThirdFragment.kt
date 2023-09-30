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
import com.sarrawi.img.databinding.FragmentThirdBinding
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class ThirdFragment : Fragment() {



    private lateinit var _binding: FragmentThirdBinding

    private val binding get() = _binding

    private val retrofitService = ApiService.provideRetrofitInstance()

    private val mainRepository by lazy {  ImgRepository(retrofitService) }

    private val imgsViewmodel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(),mainRepository)
    }

    private var isInternetConnected: Boolean = true

    private val imgAdapter by lazy {
        ImgAdapter(requireActivity())
    }

    private var ID_Type_id = -1


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Live Connected Check
//        imgsViewmodel.isConnected.observe(requireActivity()) { isConnected ->
//            if (isConnected){
//                setUpRv()
//                adapterOnClick()
//                 binding.lyNoInternet.visibility = View.GONE
//
//            }
//            else {
//                binding.progressBar.visibility = View.GONE
//                binding.lyNoInternet.visibility = View.VISIBLE
//
//             }
//
//
//        }

        // في الـ ThirdFragment
        imgsViewmodel.isConnected.observe(requireActivity()) { isConnected ->
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

        imgsViewmodel.checkNetworkConnection(requireContext())

        imgsViewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // عرض ProgressBar إذا كان التحميل قيد التقدم
            } else {
                binding.progressBar.visibility = View.GONE // إخفاء ProgressBar إذا انتهى التحميل
            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()

    }





    private fun setUpRv() = imgsViewmodel.viewModelScope.launch {
        imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(requireActivity())
        { imgs ->
            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            if (imgs != null) {
                // print data
                imgAdapter.img_list = imgs
                if (binding.rvImgCont.adapter == null) {

                    binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 3)
                    binding.rvImgCont.adapter = imgAdapter
                    imgAdapter.notifyDataSetChanged()

                } else {
                    imgAdapter.notifyDataSetChanged()
                }
            }

            else {
                // imgs هي قائمة فارغة أو null، يمكنك اتخاذ الإجراء المناسب هنا
            }

            val handler = Handler(Looper.getMainLooper()) // تعريف handler هنا

            handler.postDelayed({
                hideprogressdialog()
            }, 5000)
        }
    }


    fun hideprogressdialog() {
        binding.progressBar.visibility = View.GONE
        binding.lyNoInternet.visibility = View.GONE
    }

//    fun adapterOnClick(){
//
//        imgAdapter.onItemClick = {id, currentItemId ->
//             // currentItemId for current item selected
//            //id not use
//            val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id,currentItemId)
//            findNavController().navigate(directions,)
//        }
//    }

    fun adapterOnClick(){
        imgAdapter.onItemClick = { id, currentItemId ->
            if (isInternetConnected) {
                // القم بتنفيذ الإجراء فقط إذا كان هناك اتصال بالإنترنت
                val directions = ThirdFragmentDirections.actionToFourFragment(ID_Type_id, currentItemId)
                findNavController().navigate(directions)
            } else {
                // إذا كان الاتصال بالإنترنت معطلًا، لا تفعيل النقر (onclick)
            }
        }
    }



}