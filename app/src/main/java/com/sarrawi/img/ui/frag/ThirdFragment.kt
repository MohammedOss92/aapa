package com.sarrawi.img.ui.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.adapter.ImgAdapter
import com.sarrawi.img.db.repository.ImgRepository
import android.os.Parcelable
import androidx.navigation.fragment.findNavController
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
    private var ID = -1
    lateinit var image_url:String
    private var recyclerViewState: Parcelable? = null

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
        ID = ThirdFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }



    private fun setUpRv() {
        if (isAdded) {
            imgsViewModel.getAllImgsViewModel(ID).observe(viewLifecycleOwner) { imgs ->
                imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

                if (imgs.isEmpty()) {
                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
                    imgsViewModel.getAllImgsViewModel(ID)
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
                            binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(),2)
                            binding.rvImgCont.adapter = imgAdapter
                        } else {
                            imgAdapter.notifyDataSetChanged()
                        }
                    }
                }

                imgAdapter.onItemClick = { _, imgModel: ImgsModel,currentItemId ->
                    if (imgsViewModel.isConnected.value == true) {

                        val directions = ThirdFragmentDirections.actionToFourFragment(ID, currentItemId,imgModel.image_url)
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

        imgAdapter.onItemClick = { _, imgModel: ImgsModel,currentItemId ->
            if (imgsViewModel.isConnected.value == true) {
                val directions = ThirdFragmentDirections.actionToFourFragment(ID,currentItemId,imgModel.image_url)
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
