package com.example.spanexample

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan


/**
 */
class StrikeSpan(strokeWidth: Int) : ReplacementSpan() {
    private val mPaint: Paint = Paint()
    private var mWidth = 0
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
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
        val centerY = (top + bottom) * 0.5f
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
        canvas.drawLine(x, centerY, x + mWidth, centerY, mPaint)
    }

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLUE
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = strokeWidth.toFloat()
    }
}