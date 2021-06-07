package com.capstone.productdetection.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.productdetection.R
import com.capstone.productdetection.ViewModelFactory
import com.capstone.productdetection.databinding.ActivityDetailBinding
import com.capstone.productdetection.databinding.ContentDetailBinding
import com.capstone.productdetection.model.utils.DataModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCER = "extra_producer"
    }

    private lateinit var detailProducerBinding: ContentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailProducerBinding = activityDetailBinding.detail

        setContentView(activityDetailBinding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val producerId = extras.getInt(EXTRA_PRODUCER)

            viewModel.getDetail(producerId).observe(this, { results ->
                showDetailProducer(results)
            })
        }
    }

    private fun showDetailProducer(producer: DataModel) {
        detailProducerBinding.tvTitle.text = producer.title
        detailProducerBinding.tvDesc.text = producer.desc
        detailProducerBinding.tvAlamat.text = producer.location
        Glide.with(this)
            .load(producer.image)
            .transform(RoundedCorners(20))
            .into(detailProducerBinding.imgPoster)
        Glide.with(this)
            .load(producer.image)
            .into(detailProducerBinding.imgPosterHeader)
    }
}