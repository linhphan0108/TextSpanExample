package com.example.spanexample.customSpan

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan


/**
 */
class FrameSpan : ReplacementSpan() {
    private val mPaint: Paint = Paint()
    private var mWidth = 0
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        //return text with relative to the Paint
        mWidth = paint.measureText(text, start, end).toInt()
        return mWidth
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
        //draw the frame with custom Paint
        canvas.drawRect(x, top.toFloat(), x + mWidth, bottom.toFloat(), mPaint)
    }

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLUE
        mPaint.isAntiAlias = true
    }
}