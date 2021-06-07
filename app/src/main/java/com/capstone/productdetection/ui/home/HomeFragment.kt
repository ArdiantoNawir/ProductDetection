package com.capstone.productdetection.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.productdetection.R
import com.capstone.productdetection.ViewModelFactory
import com.capstone.productdetection.databinding.FragmentHomeBinding
import com.capstone.productdetection.vo.Status

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    private var simpleImage = intArrayOf(
        R.drawable.akrilik,
        R.drawable.berrysablon,
        R.drawable.daur
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        val carouselView = homeBinding.carouselView

        carouselView.pageCount = simpleImage.size
        carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(simpleImage[position])
        }

        return homeBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.rvRecommended.setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_detailActivity)
            view.findNavController().navigate(R.id.action_navigation_home_to_detailActivity)
        }

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
            homeAdapter = HomeAdapter()

            homeViewModel.getRecommended().observe(viewLifecycleOwner, { recommended ->
                if (recommended != null) {
                    when (recommended.status) {
                        Status.LOADING -> true.progressBar()
                        Status.SUCCESS -> {
                            false.progressBar()
                            with(homeAdapter) {
                                submitList(recommended.data)
                            }
                        }
                        Status.ERROR -> {
                            false.progressBar()
                            Toast.makeText(
                                context,
                                "Data error, silahkan coba lagi",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {
        with(homeBinding.rvRecommended) {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            setHasFixedSize(true)
        }
    }

    private fun Boolean.progressBar() {
        homeBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}
