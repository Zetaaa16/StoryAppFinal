package com.fadhil.storyappfinal.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.fadhil.storyappfinal.databinding.ActivityDetailBinding
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_DATE
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_DESC
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_IMAGE
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_NAME

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        Glide.with(this)
            .load(image)
            .into(binding.itemPhoto)

        binding.apply {
            tvItemName.text = name
            tvDescription.text = desc

        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = name
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}