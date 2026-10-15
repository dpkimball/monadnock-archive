package com.oxbow.monadnock.binding

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import android.widget.ImageView
import com.oxbow.monadnock.testing.OpenForTesting

import com.bumptech.glide.Glide

import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
@OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(fragment).load(url).into(imageView)
    }
}
