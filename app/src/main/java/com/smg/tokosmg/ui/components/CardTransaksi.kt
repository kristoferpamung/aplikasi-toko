package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.smg.tokosmg.R
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun CardTransaksi (transaksi: Transaksi) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val formatTanggal = SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm", Locale.getDefault())

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        onClick = {
            expanded = !expanded
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Invoice : ")
                }
                append(transaksi.idTransaksi)
            })
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = formatTanggal.format(transaksi.tanggal.toDate()))
                }
                
                Box(
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.extraSmall)
                        .background(
                            color = when (transaksi.statusTransaksi) {
                                "Menunggu Konfirmasi" -> MaterialTheme.colorScheme.error
                                "Diterima" -> MaterialTheme.colorScheme.primaryContainer
                                "Selesai" -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.error
                            }
                        )
                ) {
                    if (transaksi.statusTransaksi == "Menunggu Konfirmasi") {
                        CountdownTimer(expirationTime = transaksi.expiredDate)
                    } else {
                        Text(
                            text = transaksi.statusTransaksi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Text(text = buildAnnotatedString {
                append("Total Bayar : ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    ),
                ) {
                    append(rupiahFormat(transaksi.total))
                }
            })
            if (expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Daftar Pesanan:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = interFontFamily
                        )
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "No.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.weight(0.2f)
                        )
                        Text(
                            text = "Nama Barang",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Harga",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.weight(0.6f)
                        )
                        Text(
                            text = "Jumlah",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.weight(0.4f)
                        )
                        Text(
                            text = "Subtotal",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.weight(0.6f)
                        )
                    }
                    transaksi.item.forEachIndexed { index, it ->
                        Row (
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${index +1}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                ),
                                modifier = Modifier.weight(0.2f)

                            )
                            Text(
                                text = it.namaBarang,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = rupiahFormat(it.harga),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                ),
                                modifier = Modifier.weight(0.6f)
                            )
                            Text(
                                text = "${it.jumlah} ${it.satuan}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                ),
                                modifier = Modifier.weight(0.4f)
                            )
                            Text(
                                text = rupiahFormat(it.subTotal),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                ),
                                modifier = Modifier.weight(0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountdownTimer(expirationTime: Timestamp) {
    var remainingTime by remember { mutableLongStateOf(calculateRemainingTime(expirationTime)) }

    // This LaunchedEffect will update the remaining time every second
    LaunchedEffect(expirationTime) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime = calculateRemainingTime(expirationTime)
        }
    }

    // Display the remaining time in Minutes:Seconds format
    Text(
        text = formatTime(remainingTime),
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.surfaceContainerLowest),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
}


// Function to calculate the remaining time in milliseconds
private fun calculateRemainingTime(expirationTime: Timestamp): Long {
    val currentTime = System.currentTimeMillis()
    val expirationTimeInMillis = expirationTime.toDate().time
    return expirationTimeInMillis - currentTime
}

// Function to format the remaining time in Minutes:Seconds format
private fun formatTime(timeInMillis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
    return String.format("%02d:%02d", minutes, seconds)
}