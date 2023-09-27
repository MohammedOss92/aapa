package com.sarrawi.img

import androidx.fragment.app.Fragment



class FirstFragment : Fragment() {

//    private var _binding: FragmentFirstBinding? = null
//
//    private val binding get() = _binding!!
//    private val imgtypesViewmodel: ImgTypes_ViewModel by lazy {
//        ViewModelProvider(requireActivity(), ImgTypes_ViewModel.ImgTypesViewModelFactory(requireActivity().application)).get(ImgTypes_ViewModel::class.java)
//    }
//
//
//    private val typesAdapter by lazy {
//        TypesAdapter(requireActivity())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentFirstBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setUpRv()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun setUpRv() = imgtypesViewmodel.viewModelScope.launch {
//        imgtypesViewmodel.getAllTypes_ViewModel().observe(requireActivity()){imgTypes->
//            typesAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//            typesAdapter.imgTypes_list = imgTypes
//            if(binding.rvImgTypes.adapter == null){
//
//                binding.rvImgTypes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//
//                binding.rvImgTypes.adapter = typesAdapter
//                typesAdapter.notifyDataSetChanged()
//            }else{
//                typesAdapter.notifyDataSetChanged()
//            }
//        }
//    }
}