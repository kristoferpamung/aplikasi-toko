package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.model.HargaProduk
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat

@Composable
fun CustomAlertDialog (
    onDismissRequest : () -> Unit,
    name: String,
    imgUrl: String,
    quantity: String,
    productUnit : String,
    prices: List<HargaProduk>
) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = AlertDialogDefaults.shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )

        ) {
            Column (
                modifier = Modifier.padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = "product image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CardDefaults.elevatedShape),
                        contentScale = ContentScale.Crop,
                    )
                    Column (
                        modifier = Modifier.fillMaxHeight().padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.error,
                                    shape = MaterialTheme.shapes.large
                                )
                                .clickable {
                                    onDismissRequest.invoke()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "x",
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.8f),
                                    shape = MaterialTheme.shapes.large
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.box_fill),
                                    contentDescription = "quantity icon",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(10.dp)
                                )
                                Text(
                                    text = "$quantity $productUnit",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = interFontFamily,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = interFontFamily,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "formatHargaRupiah",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}