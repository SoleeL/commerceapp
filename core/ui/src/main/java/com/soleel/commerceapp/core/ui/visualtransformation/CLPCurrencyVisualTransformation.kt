package com.soleel.commerceapp.core.ui.visualtransformation

import android.icu.text.NumberFormat
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale

class CLPCurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val numberFormat = NumberFormat.getInstance(Locale("es", "CL"))
        val rawText = text.text.replace(Regex("[^\\d]"), "") // Solo números

        val formattedText = if (rawText.isNotEmpty()) {
            "$" + numberFormat.format(rawText.toLong())
        } else {
            "$0"
        }

        return TransformedText(AnnotatedString(formattedText), CurrencyOffsetMapping(text.text, formattedText))
    }
}

class CurrencyOffsetMapping(private val original: String, private val transformed: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = transformed.length
    override fun transformedToOriginal(offset: Int): Int = original.length
}