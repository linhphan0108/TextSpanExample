package com.example.spanexample

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt


class BackgroundDrawableTextSpan constructor(
    private val drawable: Drawable?,
    @ColorInt private val textColor: Int,
    private val paddingStart: Int = 0,
    private val paddingEnd: Int = 0
) : ReplacementSpan() {

    constructor(drawable: Drawable?, @ColorInt textColor: Int, padding: Int = 0):
            this(drawable, textColor, padding, padding)

    private var spanWidth = -1

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {

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
        paint.color = textColor
        canvas.drawText(text, start, end, x + paddingStart, y.toFloat(), paint)
    }
}