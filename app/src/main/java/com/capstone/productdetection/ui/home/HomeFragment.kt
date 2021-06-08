package com.capstone.productdetection.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.productdetection.R
import com.capstone.productdetection.ViewModelFactory
import com.capstone.productdetection.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private var simpleImage = intArrayOf(
        R.drawable.ikm_1,
        R.drawable.ikm_2,
        R.drawable.ikm_3
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        val carouselView = binding.carouselView

        carouselView.pageCount = simpleImage.size
        carouselView.setImageListener{ position, imageView ->
            imageView.setImageResource(simpleImage[position])
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecommended.setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_detailActivity)
            view.findNavController().navigate(R.id.action_navigation_home_to_detailActivity)
        }

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
            val homeAdapter = HomeAdapter()

            homeViewModel.getRecommended().observe(viewLifecycleOwner, { data ->
                homeAdapter.setRecommended(data)
                homeAdapter.notifyDataSetChanged()
            })

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
}