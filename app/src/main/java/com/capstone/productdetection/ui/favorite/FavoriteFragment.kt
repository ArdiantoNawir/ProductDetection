package com.capstone.productdetection.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.productdetection.R
import com.capstone.productdetection.ViewModelFactory
import com.capstone.productdetection.databinding.FragmentFavoriteBinding
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        favoriteBinding =
            FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(favoriteBinding.rvFav)

        if (activity != null) {

            true.progressBar()

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            favoriteAdapter = FavoriteAdapter()

            viewModel.getFavListRecommended().observe(viewLifecycleOwner, {
                false.progressBar()
                favoriteAdapter.submitList(it)
            })

            favoriteBinding.rvFav.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = favoriteAdapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipePosition = viewHolder.adapterPosition
                val recommendedData = favoriteAdapter.getSwipedItem(swipePosition)
                recommendedData?.let {
                    viewModel.setFavListRecommended(it)
                }

                val snackbar = Snackbar.make(view as View, R.string.undo, Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    recommendedData?.let {
                        viewModel.setFavListRecommended(it)
                    }
                }
                snackbar.show()
            }
        }

    })

    private fun Boolean.progressBar() {
        favoriteBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}