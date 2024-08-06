package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat

@Composable
fun ProductCardSmall(
    productId: String,
    produkItem: ProdukItem,
    stok: Long,
    onQuantityChange: (ProdukItem, Int) -> Unit,
    onDelete: (ProdukItem) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val imgUrl = "https://firebasestorage.googleapis.com/v0/b/toko-smg-da935.appspot.com/o/products%2F$productId.jpg?alt=media&token=fef749a9-24a0-4529-bf97-4e20552f961a"

    val formatHargaRupiah = rupiahFormat(produkItem.subTotal)
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = imgUrl,
                error = painterResource(id = R.drawable.no_product_img),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(shape = CardDefaults.shape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column (
                modifier = Modifier
                    .height(96.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = produkItem.namaBarang,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = interFontFamily
                    )
                )
                Text(
                    text = formatHargaRupiah,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = interFontFamily
                    )
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(shape = CircleShape)
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                if (produkItem.jumlah > 1) {
                                    onQuantityChange(produkItem, produkItem.jumlah.toInt() - 1)
                                    produkItem.jumlah -= 1
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "-")
                    }
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(shape = MaterialTheme.shapes.extraLarge),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "${produkItem.jumlah} ${produkItem.satuan}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = interFontFamily
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(shape = CircleShape)
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                if (produkItem.jumlah < stok) {
                                    onQuantityChange(produkItem, produkItem.jumlah.toInt() + 1)
                                    produkItem.jumlah += 1
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "+")
                    }
                }
            }
            Column (
                modifier = Modifier
                    .height(96.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.error
                        )
                        .clickable {
                            showDialog = !showDialog
                        },
                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash3_fill),
                        contentDescription = "delete item",
                        tint = MaterialTheme.colorScheme.surfaceContainerLowest,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = ! showDialog },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(produkItem)
                    }
                ) {
                    Text("Hapus")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = !showDialog
                    }
                ) {
                    Text(text="Batal", color = MaterialTheme.colorScheme.outline)
                }
            },
            title = {
                Text(text = "Hapus Produk")
            },
            text = {
                Text(text = buildAnnotatedString {
                    append("Anda yakin menghapus ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(produkItem.namaBarang)
                    }
                    append(" dari keranjang?")
                })
            }
        )
    }
}