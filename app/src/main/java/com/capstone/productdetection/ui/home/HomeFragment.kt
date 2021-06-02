package com.capstone.productdetection.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.productdetection.R
import com.capstone.productdetection.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


//carouselView.pageCount = carouselImages.size
//carouselView.setImageListener(imageListener)
// aku lihatnya di homeactivity di step nya, tapi kalo dimasukin fragment kurang tau aku

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecommended.setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_detailActivity)
            view.findNavController().navigate(R.id.action_navigation_home_to_detailActivity)
        }

        if (activity != null) {
            homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

            val listProducer = homeViewModel.getRecomended()
            val homeAdapter = HomeAdapter()
            homeAdapter.setRecommended(listProducer)

            with(binding.rvRecommended) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = homeAdapter
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
//    val carouselImages = intArrayOf(
//        R.drawable.ikm_1,
//        R.drawable.ikm_2,
//        R.drawable.ikm_3,
//        R.drawable.ikm_4
//    )
//
//    val imageListener = ImageListener {position, imageView ->
//        imageView.setImageResource(carouselImages[position])
//    }
}