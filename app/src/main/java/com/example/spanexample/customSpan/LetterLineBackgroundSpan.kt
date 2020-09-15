package com.example.spanexample.customSpan

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import java.util.*


/**
 *
 */
class LetterLineBackgroundSpan : LineBackgroundSpan {
    private val mCPaint: Paint = Paint()
    private val mVPaint: Paint
    private val mRectF = RectF()
    override fun drawBackground(
        c: Canvas,
        p: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lnum: Int
    ) {
        var charx = left.toFloat()
        for (i in start until end) {
            val charAt = extractText(text, i, i + 1)
            val charWidth = p.measureText(charAt)
            mRectF[charx, top.toFloat(), charWidth.let { charx += it; charx }] = bottom.toFloat()
            if (Arrays.binarySearch(
                    sV,
                    charAt[0]
                ) >= 0
            ) {
                c.drawRect(mRectF, mVPaint)
            } else {
                c.drawRect(mRectF, mCPaint)
            }
        }
    }

    private fun extractText(text: CharSequence, start: Int, end: Int): String {
        return text.subSequence(start, end).toString()
    }

    companion object {
        private val sV = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
    }

    init {
        mCPaint.color = Color.MAGENTA
        mCPaint.isAntiAlias = true
        mVPaint = Paint()
        mVPaint.color = Color.YELLOW
        mVPaint.isAntiAlias = true
    }
}