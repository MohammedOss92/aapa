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
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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

    fun InterstitialAd_fun (){


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


}
