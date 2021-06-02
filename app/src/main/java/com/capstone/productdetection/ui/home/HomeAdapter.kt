package com.capstone.productdetection.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.productdetection.databinding.ItemSellerBinding
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.ui.detail.DetailActivity

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var listRecommended = ArrayList<DataModel>()

    fun setRecommended(recommended: List<DataModel>) {
        this.listRecommended.clear()
        this.listRecommended.addAll(recommended)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemSellerBinding = ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemSellerBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val items = listRecommended[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int = listRecommended.size

    class HomeViewHolder(private val binding: ItemSellerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(content: DataModel) {
            with(binding) {
                title.text = content.title
                alamat.text = content.location
                desc.text = content.desc
                Glide.with(itemView.context).load(content.image).into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_PRODUCER, content.title)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }
}