package com.sarrawi.img

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.repository.ImgRepository
import kotlinx.coroutines.launch
import android.view.*
import android.widget.Toast
import androidx.core.view.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.adapter.ViewPagerAdapter
import com.sarrawi.img.databinding.FragmentFourBinding
import com.sarrawi.img.db.Dao.Imgs_Dao
import com.sarrawi.img.db.repository.FavoriteImageRepository
import com.sarrawi.img.db.viewModel.*
import com.sarrawi.img.model.FavoriteImage
import com.sarrawi.img.model.ImgsModel

class FourFragment : Fragment() {

private lateinit var _binding: FragmentFourBinding

private val binding get() = _binding


private val retrofitService = ApiService.provideRetrofitInstance()

private val mainRepository by lazy {  ImgRepository(retrofitService,requireActivity().application) }
private val a by lazy {  FavoriteImageRepository(requireActivity().application) }


private val imgsViewmodel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(),mainRepository)
        }




    private val imgsffav: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(a)
    }



    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
        ViewModelFactory2(favoriteImageRepository)
    }


    private val viewPagerAdapter by lazy {
    ViewPagerAdapter(requireActivity())
        }

    private var ID_Type_id = -1
    private  var currentItemId:Int=0



        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        _binding = FragmentFourBinding.inflate(inflater, container, false)
            setHasOptionsMenu(true)
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
            setHasOptionsMenu(true)
            menu_item()
            setUpRv()
            adapterOnClick()
            imgsffav.updateImages()
            // Live Connected
            imgsViewmodel.isConnected.observe(requireActivity()) {
                    isConnected ->

                if (isConnected) {
//                  setUpViewPager()
                    adapterOnClick()
                 binding.lyNoInternet.visibility = View.GONE
                    
                  }
                else {
//                     binding.progressBar.visibility = View.GONE
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


//            imgsViewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//                if (isLoading) {
//                    binding.progressBar.visibility = View.VISIBLE // عرض ProgressBar إذا كان التحميل قيد التقدم
//                } else {
//                    binding.progressBar.visibility = View.GONE // إخفاء ProgressBar إذا انتهى التحميل
//                }
//            }


        }

//    private fun setUpRv() {
//        if (isAdded) {
//            imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(viewLifecycleOwner) { imgs ->
//                // تم استدعاء الدالة فقط إذا كان ال Fragment متصلاً بنشاط
//                viewPagerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//                if (imgs.isEmpty()) {
//                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
//                    imgsViewmodel.getAllImgsViewModel(ID_Type_id)
//                } else {
//                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
//                    viewPagerAdapter.img_list = imgs
//                    if (binding.rvImgCont.adapter == null) {
//                        binding.rvImgCont.layoutManager = LinearLayoutManager(requireContext())
//                        binding.rvImgCont.adapter = viewPagerAdapter
//                    } else {
//                        viewPagerAdapter.notifyDataSetChanged()
//                    }
//                }
//
//
//            }
//        }
//    }

    private fun setUpRv() {
        if (isAdded) {
            imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(viewLifecycleOwner) { imgs ->
                viewPagerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

                if (imgs.isEmpty()) {
                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
                    imgsViewmodel.getAllImgsViewModel(ID_Type_id)
                } else {
                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView

                    // هنا قم بالحصول على البيانات المفضلة المحفوظة محليًا من ViewModel
                    favoriteImagesViewModel.getAllFav().observe(viewLifecycleOwner) { favoriteImages ->
                        val allImages: List<ImgsModel> = imgs

                        for (image in allImages) {
                            val isFavorite = favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
                            image.is_fav = isFavorite // قم بتحديث حالة الصورة
                        }

                        viewPagerAdapter.img_list = allImages

                        if (binding.rvImgCont.adapter == null) {
                            binding.rvImgCont.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvImgCont.adapter = viewPagerAdapter
                        } else {
                            viewPagerAdapter.notifyDataSetChanged()
                        }
                    }
                }


            }
        }
    }
//    private fun setUpViewPager() =
//        imgsViewmodel.viewModelScope.launch {
//        imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
//             // print data
//            if (imgs != null) {
//                viewPagerAdapter.img_list=imgs
//                binding.viewpager.adapter =viewPagerAdapter
//                binding.viewpager.setCurrentItem(currentItemId,false) // set for selected item
//                viewPagerAdapter.notifyDataSetChanged()
//
//            }
//
//            else {
//                // No data
//             }
//
//        }
//        }

            override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.menu_fragment_four, menu)
            }

            override fun onOptionsItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
//                    R.id.action_option1 -> {
//                        // اتخاذ إجراء عند اختيار Option 1
//                        true
//                    }
//                    R.id.action_option2 -> {
//                        // اتخاذ إجراء عند اختيار Option 2
//                        true
//                    }
                    else -> super.onOptionsItemSelected(item)
                }
            }



    private fun menu_item() {
        // The usage of an interface lets you inject your own implementation

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                // menuInflater.inflate(R.menu.menu_zeker, menu) // هنا لا داعي لتكرار هذا السطر
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {
                    R.id.aa -> {

                        return true
                    }
//                    R.id.action_share -> {
//                        val zekr = zeker_list[view_pager2?.currentItem ?: 0]
//                        // Perform the share action using ShareText class
//                        ShareText.shareText(
//                            requireContext(),
//                            "Share via",
//                            "Zekr Header",
//                            zekr.Description ?: ""
//                        )
//                        return true
//                    }
                    // ... (قائمة من المزيد من العناصر)
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun adapterOnClick (){
        viewPagerAdapter.onbtnClick = {it:ImgsModel,i:Int ->
            val fav= FavoriteImage(it.id!!,it.ID_Type_id,it.new_img,it.image_url)

            println("it.is_fav: ${it.is_fav}")
            if (it.is_fav) {
                it.is_fav = false
                imgsffav.removeFavoriteImage(fav)

                imgsffav.updateImages()
                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
                snackbar.show()
//                setUpViewPager()

                viewPagerAdapter.notifyDataSetChanged()
                println("it.is_fav: ${it.is_fav}")
            }

            else{
                it.is_fav = true
                imgsffav.addFavoriteImage(fav)

                imgsffav.updateImages()
                val snackbar = Snackbar.make(view!!, "تم الاضافة", Snackbar.LENGTH_SHORT)
                snackbar.show()
//                setUpViewPager()

                viewPagerAdapter.notifyDataSetChanged()
                println("it.is_fav: ${it.is_fav}")
            }
            // تحقق من قيمة it.is_fav
            println("it.is_fav: ${it.is_fav}")
//            setUpViewPager()

            viewPagerAdapter.notifyDataSetChanged()
            println("it.is_fav: ${it.is_fav}")
        }


    }
/*
    if (it.is_fav!!){
                imgsViewmodel.update_fav(it.id!!,false)
                imgsViewmodel.delete_fav(fav)
                val snackbar = Snackbar.make(view!!,"تم الحذف",Snackbar.LENGTH_SHORT)
                    snackbar.show()
                setUpViewPager()
                viewPagerAdapter.notifyDataSetChanged()

            }else{
                imgsViewmodel.update_fav(it.id!!,true)
                imgsViewmodel.add_fav(fav)
                val snackbar = Snackbar.make(view!!,"تم الاضافة",Snackbar.LENGTH_SHORT)
                snackbar.show()
                setUpViewPager()
                viewPagerAdapter.notifyDataSetChanged()

            }*/
}





