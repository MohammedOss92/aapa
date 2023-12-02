package com.sarrawi.img.ui.frag

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.databinding.FragmentThirdBinding
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.*
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.paging.PagingAdapterImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThirdFragment : Fragment() {

    private lateinit var _binding: FragmentThirdBinding
    private val binding get() = _binding
    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository by lazy { ImgRepository(retrofitService,requireActivity().application) }
    private val imgsViewModel: Imgs_ViewModel by viewModels {
        ViewModelFactory(requireContext(), mainRepository)
    }
//    private val imgAdapter by lazy { ImgAdapter(requireActivity()) }
    private val imgAdaptert by lazy { PagingAdapterImage(requireActivity()) }
    private var ID = -1
//    private var ID: Int? = null

    lateinit var image_url:String

//    private val favoriteImageRepository by lazy { FavoriteImageRepository(requireActivity().application) }
//    private val favoriteImagesViewModel: FavoriteImagesViewModel by viewModels {
//        ViewModelFactory2(favoriteImageRepository)
//    }

//    private val a by lazy {  FavoriteImageRepository(requireActivity().application) }
//    private val imgsffav: FavoriteImagesViewModel by viewModels {
//        ViewModelFactory2(a)
//    }

    private var currentItemId = -1
    var clickCount = 0
    var mInterstitialAd: InterstitialAd?=null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgsViewModel.isConnected.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                setUpRvth()
//                setUpRvsnip()
                adapterOnClick()
                imgAdaptert.updateInternetStatus(isConnected)
                binding.lyNoInternet.visibility = View.GONE
            } else {
//                binding.progressBar.visibility = View.GONE
                binding.lyNoInternet.visibility = View.VISIBLE
                imgAdaptert.updateInternetStatus(isConnected)
            }
        }
//        InterstitialAd_fun()
        setUpRvth()
//        setUpRvsnip()
        adapterOnClick()

        imgsViewModel.checkNetworkConnection(requireContext())

//        imgsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            if (isLoading) {
//                binding.progressBar.visibility = View.VISIBLE
//            } else {
//                binding.progressBar.visibility = View.GONE
//            }
//        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()

    }

    private fun setUpRvth() {
        if (isAdded) {
        // تعيين المدير التخطيط (GridLayout) لـ RecyclerView أولاً
            binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 2)

        // تعيين المحمل للـ RecyclerView بعد تعيين المدير التخطيط
            binding.rvImgCont.adapter = imgAdaptert


//            imgsViewModel.getImgsData(ID).observe(viewLifecycleOwner) {


                imgsViewModel.getsnippetsid(ID).observe(viewLifecycleOwner) {

                    imgAdaptert.submitData(viewLifecycleOwner.lifecycle, it)
                    imgAdaptert.notifyDataSetChanged()


                }

        // اختيار دالة التعيين وضبط السياسة لـ RecyclerView
            imgAdaptert.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
// بعد تحديث البيانات

    }
}



//    private val sportsItemListener = PagingAdapterImage.OnClickListener { imgsModel ->
//        val direction: NavDirections =
//            ThirdFragmentDirections.actionToFourFragment(imgsModel)
//        Navigation.findNavController(requireView()).navigate(direction)
//    }



    fun adapterOnClick() {
//        imgAdaptert.onItemClick = { _, imgModel: ImgsModel, currentItemId ->
        imgAdaptert.onItemClick = { _ ->
            if (imgsViewModel.isConnected.value == true) {

                clickCount++
                if (clickCount >= 2) {
                    // بمجرد أن يصل clickCount إلى 2، اعرض الإعلان
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(requireActivity())
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                    clickCount = 0 // اعيد قيمة المتغير clickCount إلى الصفر بعد عرض الإعلان

                }


                val directions = ThirdFragmentDirections.actionToFourFragment(
                    ID
                )
                findNavController().navigate(directions)

// الانتظار حتى يتم تحميل الشاشة الجديدة

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(100) // انتظر لبعض الوقت للتأكد من أن التحديثات قد تم تطبيقها
                    binding.rvImgCont.scrollToPosition(currentItemId)
                }
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

    fun InterstitialAd_fun() {
        MobileAds.initialize(requireActivity()) { initializationStatus ->
            // do nothing on initialization complete
        }

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireActivity(),
            "ca-app-pub-1895204889916566/2401606550",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until an ad is loaded.
                    mInterstitialAd = interstitialAd
                    Log.i("onAdLoadedL", "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d("onAdLoadedF", loadAdError.toString())
                    mInterstitialAd = null
                }
            }
        )
    }

    //                favoriteImagesViewModel.getAllFav()
//                    .observe(viewLifecycleOwner) { favoriteImages ->
//                        val allImages: List<ImgsModel> = newImgs
//                        for (image in allImages) {
//                            val isFavorite =
//                                favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
//                            image.is_fav = isFavorite // قم بتحديث حالة الصورة
//                        }
}

