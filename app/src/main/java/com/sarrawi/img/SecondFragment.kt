package com.sarrawi.img

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sarrawi.img.adapter.TypesAdapter
import com.sarrawi.img.adapter.TypesAdapter_T
import com.sarrawi.img.databinding.FragmentSecondBinding
import com.sarrawi.img.db.viewModel.ImgTypes_ViewModel
import com.sarrawi.img.model.Img_Types_model
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private val imgtypesViewmodel: ImgTypes_ViewModel by lazy {
        ViewModelProvider(requireActivity(), ImgTypes_ViewModel.ImgTypesViewModelFactory(requireActivity().application)).get(
            ImgTypes_ViewModel::class.java)
    }

    private val typesAdapter_T by lazy {
        TypesAdapter_T(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        adapterOnClick()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setUpRv() = imgtypesViewmodel.viewModelScope.launch {
            imgtypesViewmodel.getAllTypes_ViewModel().observe(requireActivity()){imgTypes->
                typesAdapter_T.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
                typesAdapter_T.imgTypes_list = imgTypes
                if(binding.rvImgTypes.adapter == null){


                    binding.rvImgTypes.layoutManager = GridLayoutManager(requireContext(), 2) // هنا تعيين عدد الأعمدة إلى 2
                    binding.rvImgTypes.adapter = typesAdapter_T
                    typesAdapter_T.notifyDataSetChanged()
                }else{
                    typesAdapter_T.notifyDataSetChanged()
                }
            }
        }

    fun adapterOnClick(){
        typesAdapter_T.onItemClick = {id ->
            val directions = SecondFragmentDirections.actionSecondFragmentToThirdFragment(id)
            findNavController().navigate(directions)

        }
    }

}