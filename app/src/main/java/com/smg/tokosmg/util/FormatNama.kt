package com.smg.tokosmg.util

fun formatNama(input: String): String {
    return input.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}