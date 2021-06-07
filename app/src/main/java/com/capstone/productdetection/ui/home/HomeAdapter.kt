package com.capstone.productdetection.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.productdetection.databinding.ItemSellerBinding
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.ui.detail.DetailActivity
import com.capstone.productdetection.vo.Resource

class HomeAdapter: PagedListAdapter<DataModel, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataModel>() {
            override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemSellerBinding = ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemSellerBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null) holder.bind(items)
    }

    class HomeViewHolder(private val binding: ItemSellerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(content: DataModel) {
            with(binding) {
                title.text = content.title
                alamat.text = content.location
                desc.text = content.desc
                Glide.with(itemView.context).load(content.image).into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_PRODUCER, content.id)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }
}