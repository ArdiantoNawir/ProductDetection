package com.capstone.productdetection.ui.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.productdetection.databinding.ItemResultBinding
import com.capstone.productdetection.model.utils.MaterialModel

class CaptureAdapter: RecyclerView.Adapter<CaptureAdapter.CaptureViewHolder>() {

    private var listResult = ArrayList<String>()

    fun setList(list: List<String>) {
        this.listResult.clear()
        this.listResult.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptureViewHolder {
        val itemResultBinding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaptureViewHolder(itemResultBinding)
    }

    override fun onBindViewHolder(holder: CaptureViewHolder, position: Int) {
        val items = listResult[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int = listResult.size

    class CaptureViewHolder(private val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(content: String) {
            with(binding) {
                itemBrgjd.text = content
            }
        }

    }
}