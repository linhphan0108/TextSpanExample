package com.example.spanexample

import android.graphics.Color
import android.os.Parcel
import android.text.TextPaint
import android.text.style.ForegroundColorSpan


class MutableForegroundColorSpan : ForegroundColorSpan {
    private var mAlpha = 255
    private var mForegroundColor: Int

    constructor(alpha: Int, color: Int) : super(color) {
        mAlpha = alpha
        mForegroundColor = color
    }

    constructor(src: Parcel) : super(src) {
        mForegroundColor = src.readInt()
        mAlpha = src.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(mForegroundColor)
        dest.writeFloat(mAlpha.toFloat())
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = foregroundColor
    }

    /**
     * @param alpha from 0 to 255
     */
    fun setAlpha(alpha: Int) {
        mAlpha = alpha
    }

    fun setForegroundColor(foregroundColor: Int) {
        mForegroundColor = foregroundColor
    }

    val alpha: Float
        get() = mAlpha.toFloat()

    override fun getForegroundColor(): Int {
        return Color.argb(
            mAlpha,
            Color.red(mForegroundColor),
            Color.green(mForegroundColor),
            Color.blue(mForegroundColor)
        )
    }
}