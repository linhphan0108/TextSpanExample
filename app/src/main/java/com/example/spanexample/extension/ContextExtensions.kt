package com.example.spanexample.extension

import android.content.Context
import android.graphics.Typeface.*
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.text.Annotation
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.example.spanexample.util.CustomTypefaceSpan

fun Context.getXmlStyledString(
    @StringRes stringResId: Int,
    replacementList: List<Pair<String, CharSequence>> = emptyList(),
    customAnnotations: List<Pair<String, Any>> = emptyList()
): CharSequence {
    val xmlText = resources.getText(stringResId)
    if (xmlText !is SpannedString) {
        return xmlText
    }

    val spannableString = SpannableStringBuilder(xmlText)
    xmlText.getSpans(0, xmlText.length, Annotation::class.java).forEach { annotation ->
        when (annotation.key) {
            "type" -> {
                when (annotation.value) {
                    "bold" -> StyleSpan(BOLD)
                    "underline" -> UnderlineSpan()
                    "italic" -> StyleSpan(ITALIC)
                    "bolditalic" -> StyleSpan(BOLD_ITALIC)
                    else -> null
                }?.let { span ->
                    spannableString.applySpan(span, annotation)
                }
            }
            "font" -> {
                spannableString.applyFontAnnotation(this, annotation)
            }
            "replacement" -> {
                replacementList.find { (key, _) ->
                    key == annotation.value
                }?.let { (_, replacementValue) ->
                    spannableString.replaceAnnotation(annotation, replacementValue)
                }
            }
            "custom" -> {
                val customAnnotation = customAnnotations.find { it.first == annotation.value }
                if (customAnnotation != null) {
                    spannableString.applySpan(customAnnotation.second, annotation)
                }
            }
        }
    }
    return spannableString
}

private fun SpannableStringBuilder.replaceAnnotation(
    annotation: Annotation,
    replacementValue: CharSequence
) {
    replace(
        getSpanStart(annotation),
        getSpanEnd(annotation),
        replacementValue
    )
}

private fun SpannableStringBuilder.applySpan(span: Any, annotation: Annotation) {
    setSpan(span, getSpanStart(annotation), getSpanEnd(annotation), SPAN_EXCLUSIVE_EXCLUSIVE)
}

private fun SpannableStringBuilder.applyFontAnnotation(context: Context, annotation: Annotation) {
    val fontName = annotation.value
    val typeface = ResourcesCompat.getFont(context, context.resources.getIdentifier(fontName, "font", context.packageName))
    if (typeface != null) {
        applySpan(CustomTypefaceSpan(typeface), annotation)
    }
}