package com.sofaacademy.sofaminiproject.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.TextLayoutAndDropdownMenuBinding

class TextLayoutAndDropdownMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TextLayoutAndDropdownMenuBinding =
        TextLayoutAndDropdownMenuBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TextLayoutAndDropdownMenu, 0, 0)
            .apply {
                try {
                    binding.textInputLayout.hint =
                        getString(R.styleable.TextLayoutAndDropdownMenu_hint)
                } finally {
                    recycle()
                }
            }

        binding.autoCompleteTv.filters = emptyArray()
    }

    fun setText(text: String) {
        binding.autoCompleteTv.apply {
           setText(text, false)
        }
    }

    fun setStringArrayAdapter(adapter: ArrayAdapter<String>) {
        binding.autoCompleteTv.setAdapter(adapter)
    }

    fun getSelectedItemText(): String {
        return binding.autoCompleteTv.text.toString()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        binding.autoCompleteTv.onItemClickListener = onItemClickListener
    }
}
