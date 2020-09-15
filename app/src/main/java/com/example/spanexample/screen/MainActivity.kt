
package com.example.spanexample.screen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.util.Property
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spanexample.customSpan.BackgroundDrawableTextSpan
import com.example.spanexample.customSpan.MutableBlurMaskFilterSpan
import com.example.spanexample.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBackgroundDrawableTextSpan()
        setupBlurAnimation()
    }

    private fun setupBackgroundDrawableTextSpan(){
        val text = "1 hello world"
        val drawable = ContextCompat.getDrawable(this,
            R.drawable.background_gradient
        )
        val textColor = Color.WHITE
        val backgroundDrawableTextSpan =
            BackgroundDrawableTextSpan(
                drawable,
                32,
                textColor,
                16
            )
        val spannableString = SpannableString(text)
        spannableString.setSpan(backgroundDrawableTextSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txt.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    private fun setupBlurAnimation(){
        val text = "blur animation"
        val maxRadius = 16f
        val span = MutableBlurMaskFilterSpan(
            maxRadius
        )
        val spannableString = SpannableString(text)
        spannableString.setSpan(span, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txt2.setText(spannableString, TextView.BufferType.SPANNABLE)
        val objectAnimator =
            ObjectAnimator.ofFloat(span, BLUR_RADIUS_PROPERTY, maxRadius, 0.1f)
        objectAnimator.addUpdateListener { //refresh
            txt2.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
            }
        })
        objectAnimator.duration = 2000
        objectAnimator.start()
    }

    private val BLUR_RADIUS_PROPERTY: Property<MutableBlurMaskFilterSpan, Float> =
        object : Property<MutableBlurMaskFilterSpan, Float>(
            Float::class.java, "BLUR_RADIUS_PROPERTY"
        ) {
            override operator fun set(span: MutableBlurMaskFilterSpan, value: Float?) {
                span.radius = value!!
            }

            override operator fun get(span: MutableBlurMaskFilterSpan): Float? {
                return span.radius
            }
        }

}
