package com.capstone.productdetection.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.productdetection.R
import com.capstone.productdetection.databinding.ActivityDetailBinding
import com.capstone.productdetection.databinding.ContentDetailBinding
import com.capstone.productdetection.model.utils.DataModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCER = "extra_producer"
    }

    private lateinit var detailProducer: ContentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailProducer = activityDetailBinding.detail


        setContentView(activityDetailBinding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this@DetailActivity, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val producerName = extras.getString(EXTRA_PRODUCER)

            if (producerName != null) {
                val dummyProducer = viewModel.getDetail(producerName)
                showDetailProducer(dummyProducer)
            }
        }
    }

    private fun showDetailProducer(producer: DataModel) {
        detailProducer.tvTitle.text = producer.title
        detailProducer.tvDesc.text = producer.desc
        detailProducer.tvAlamat.text = producer.location
        Glide.with(this)
            .load(producer.image)
            .into(detailProducer.imgPoster)
        Glide.with(this)
            .load(producer.image)
            .into(detailProducer.imgPosterHeader)
    }
}