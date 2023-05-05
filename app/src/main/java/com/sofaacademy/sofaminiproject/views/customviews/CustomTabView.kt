package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.sofaacademy.sofaminiproject.databinding.CustomTabViewBinding

class CustomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomTabViewBinding = CustomTabViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setValue(value: String) {
        binding.textView.text=value
    }

    fun setImage(idImgRes: Int) {
        binding.icon.setImageDrawable(getDrawable(context,idImgRes))
    }
}
