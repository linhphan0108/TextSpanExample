package com.example.spanexample

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.*
import android.graphics.RectF

import android.text.style.ReplacementSpan
import java.util.*


class BubbleSpan : ReplacementSpan() {
    private lateinit var mPaint: Paint
    private var mWidth = -1
    private val mRectF = RectF()
    private val mColors = IntArray(20)
    private fun initPaint() {
        mPaint = Paint()
        mPaint.color = Color.rgb(
            random.nextInt(255),
            random.nextInt(255),
            random.nextInt(255)
        )
        mPaint.isAntiAlias = true
    }

    private fun initColors() {
        for (index in mColors.indices) {
            mColors[index] = Color.rgb(
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
            )
        }
    }

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
        var charx = x
        for (i in start until end) {
            val charAt = extractText(text, i, i + 1)
            val charWidth: Float = paint.measureText(charAt)
            mRectF[charx, top.toFloat(), charWidth.let { charx += it; charx }] = bottom.toFloat()
            mPaint.color = mColors[i % mColors.size]
            canvas.drawOval(mRectF, mPaint)
        }
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    private fun extractText(text: CharSequence, start: Int, end: Int): String {
        return text.subSequence(start, end).toString()
    }

    companion object {
        private const val TAG = "BubbleSpan"
        private const val DEBUG = false
        var random: Random = Random()
    }

    init {
        initPaint()
        initColors()
    }
}