package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorContainer (
    errorMessage: String
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .background(
                MaterialTheme.colorScheme.error,
                shape = MaterialTheme.shapes.extraSmall
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Message Error Icon",
                tint = Color.White
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                text = errorMessage,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}