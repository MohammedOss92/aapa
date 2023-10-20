package com.sarrawi.img.ui.frag

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.sarrawi.img.R
import com.sarrawi.img.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var _binding: FragmentSplashScreenBinding

    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        var rotate_z = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_z)
//        binding.imageView2.startAnimation(rotate_z)

        Handler(Looper.myLooper()!!).postDelayed({
//            val direction = SplashFragmentDirections.actionSplashFragmentToFirsFragment()
//            findNavController().navigate(direction)

            findNavController()
                .navigate(
                    R.id.action_splashScreenFragment_to_SecondFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(
                            R.id.splashScreenFragment,
                            true).build()
                )

        },5000)
    }


}