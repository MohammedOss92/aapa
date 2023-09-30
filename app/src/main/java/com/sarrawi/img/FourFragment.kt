package com.sarrawi.img

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.lifecycle.Lifecycle
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

        }}

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

}





