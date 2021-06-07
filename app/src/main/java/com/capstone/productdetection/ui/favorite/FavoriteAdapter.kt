package com.capstone.productdetection.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.productdetection.R
import com.capstone.productdetection.databinding.ItemSellerBinding
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.ui.detail.DetailActivity

class FavoriteAdapter : PagedListAdapter<DataModel, FavoriteAdapter.FavoriteViewHolder>(
DIFF_CALLBACK
) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataModel>() {
            override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedItem(swipedPosition: Int): DataModel? = getItem(swipedPosition)

    inner class FavoriteViewHolder(private val binding: ItemSellerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommended: DataModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(recommended.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                title.text = recommended.title
                alamat.text = recommended.location
                desc.text = recommended.desc

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_PRODUCER, recommended.id)
                        itemView.context.startActivity(it)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val recommended = getItem(position)
        if (recommended != null) holder.bind(recommended)
    }
}