package com.smg.tokosmg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smg.tokosmg.ui.theme.latoFontFamily

@Composable
fun ErrorText (errorMessage: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        text = "* $errorMessage",
        fontSize = 14.sp,
        fontFamily = latoFontFamily,
        color = MaterialTheme.colorScheme.error
    )
}