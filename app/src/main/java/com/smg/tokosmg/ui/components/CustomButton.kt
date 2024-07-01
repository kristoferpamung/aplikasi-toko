package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smg.tokosmg.ui.theme.Gradient1
import com.smg.tokosmg.ui.theme.Gradient2
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraSmall)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.primary
                    )
                )
            )
            .clickable {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = interFontFamily,
            letterSpacing = TextUnit(1.2f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}