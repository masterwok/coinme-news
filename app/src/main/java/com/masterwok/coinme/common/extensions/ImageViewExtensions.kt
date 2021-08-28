package com.masterwok.coinme.common.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.masterwok.coinme.R

private const val PLACEHOLDER_IMAGE_STROKE_WIDTH = 5f
private const val PLACEHOLDER_IMAGE_CENTER_RADIUS = 30f

fun ImageView.loadImage(articleImageUri: Uri?) = Glide
    .with(this)
    .load(articleImageUri)
    .placeholder(CircularProgressDrawable(context).apply {
        strokeWidth = PLACEHOLDER_IMAGE_STROKE_WIDTH
        centerRadius = PLACEHOLDER_IMAGE_CENTER_RADIUS
        start()
    })
    .fallback(R.drawable.ic_baseline_image_not_supported_24)
    .centerCrop()
    .into(this)
