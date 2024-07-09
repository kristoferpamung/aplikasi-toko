package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smg.tokosmg.ui.theme.TokoSMGTheme
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun CustomChip(
    selected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    when {
        selected -> FilledTonalButton(
            onClick = { onClick.invoke() },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 10.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        else -> OutlinedButton(
            onClick = { onClick.invoke() },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 10.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Preview
@Composable
private fun CustomChipsPrev () {
    TokoSMGTheme {
        Row {
            CustomChip(selected = true, text = "hello", onClick = {})
            CustomChip(selected = false, text = "hello", onClick = {})
        }
    }
}