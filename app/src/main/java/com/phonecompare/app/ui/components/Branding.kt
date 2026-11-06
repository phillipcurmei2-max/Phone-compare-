package com.phonecompare.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.phonecompare.app.ui.theme.ElectricBlue
import com.phonecompare.app.ui.theme.Spacing

/**
 * The PhoneCompare logo mark: two overlapping phone-screen outlines, echoing
 * the app's core "overlay two screens" idea. Drawn as vectors so it scales
 * crisply and needs no raster asset.
 */
@Composable
fun LogoMark(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 24.dp,
) {
    val outline = MaterialTheme.colorScheme.onBackground
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val corner = CornerRadius(w * 0.18f, w * 0.18f)
        val strokeBack = Stroke(width = w * 0.08f)
        val strokeFront = Stroke(width = w * 0.09f)

        // Back screen outline (muted).
        drawRoundRect(
            color = outline.copy(alpha = 0.45f),
            topLeft = Offset(0f, h * 0.12f),
            size = Size(w * 0.62f, h * 0.82f),
            cornerRadius = corner,
            style = strokeBack,
        )
        // Front screen outline (accent).
        drawRoundRect(
            color = ElectricBlue,
            topLeft = Offset(w * 0.34f, h * 0.04f),
            size = Size(w * 0.62f, h * 0.82f),
            cornerRadius = corner,
            style = strokeFront,
        )
    }
}

/** The PhoneCompare wordmark, with the accent applied to "Compare". */
@Composable
fun BrandWordmark(
    modifier: Modifier = Modifier,
    showMark: Boolean = true,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (showMark) {
            LogoMark(size = 24.dp)
            Spacer(Modifier.width(Spacing.xs))
        }
        Text(
            text = wordmarkText(),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun wordmarkText(): AnnotatedString = buildAnnotatedString {
    withStyle(
        SpanStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        ),
    ) {
        append("Phone")
    }
    withStyle(
        SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
        ),
    ) {
        append("Compare")
    }
}
