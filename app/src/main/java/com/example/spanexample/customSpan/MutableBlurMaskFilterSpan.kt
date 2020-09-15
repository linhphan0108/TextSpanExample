package com.example.spanexample.customSpan

import android.graphics.BlurMaskFilter
import android.graphics.MaskFilter
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance


class MutableBlurMaskFilterSpan(private var mRadius: Float) : CharacterStyle(),
    UpdateAppearance {
    var filter: MaskFilter? = null
        private set

    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            filter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL)
        }

    override fun updateDrawState(ds: TextPaint) {
        ds.maskFilter = filter
    }

    companion object {
        private const val TAG = "MutableBlurMaskFilterSpan"
    }

}