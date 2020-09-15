package com.example.spanexample.customSpan

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt


class BackgroundDrawableTextSpan constructor(
    private val drawable: Drawable?,
    private val textSize: Int = 0,
    @ColorInt private val textColor: Int,
    private val paddingStart: Int = 0,
    private val paddingEnd: Int = 0
) : ReplacementSpan() {

    constructor(drawable: Drawable?, textSize: Int = 0, @ColorInt textColor: Int, padding: Int = 0):
            this(drawable, textSize, textColor, padding, padding)

    private var spanWidth = -1
    private val textBound = Rect()

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {

        if (textSize > 0){
            paint.textSize = textSize.toFloat()
        }
        paint.color = textColor
        paint.getTextBounds(text.toString(), start, end, textBound)
        //return text with relative to the Paint
        spanWidth = paint.measureText(text, start, end).toInt() + paddingStart + paddingEnd
        return spanWidth
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        drawable?.setBounds(x.toInt(), top, x.toInt() + spanWidth, bottom)
        drawable?.draw(canvas)
        val t = (bottom - top + textBound.height()) * 0.5f
        canvas.drawText(text, start, end, x + paddingStart, t, paint)
    }
}