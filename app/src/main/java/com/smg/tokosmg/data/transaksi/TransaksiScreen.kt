package com.smg.tokosmg.data.transaksi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smg.tokosmg.ui.components.CardTransaksi
import com.smg.tokosmg.ui.theme.interFontFamily
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.smg.tokosmg.R

@Composable
fun TransaksiScreen (
    transaksiViewModel: TransaksiViewModel = viewModel(modelClass = TransaksiViewModel::class.java),
    padding : PaddingValues
) {
    LaunchedEffect(key1 = Unit) {
        transaksiViewModel.getAllTransaksi()
    }
    val items = listOf(
        "Semua",
        "Menunggu Konfirmasi",
        "Diterima",
        "Selesai",
        "Ditolak"
    )

    var currentCategory by remember {
        mutableIntStateOf(0)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    val allTransaksi = transaksiViewModel.transaksiUiState.transaksiList.data

    val sortByDate = allTransaksi?.sortedByDescending {
        it.tanggal
    }

    val filteredTransaksi = sortByDate?.filter { transaksi ->
        transaksi.statusTransaksi.contains(items[currentCategory], ignoreCase = true)
    }

    Column (
        modifier = Modifier.padding(padding)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Daftar Pesanan",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            IconButton(
                onClick = { 
                    showDialog = !showDialog
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.exclamation_circle), contentDescription = "")
            }
        }
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(items) { index, item ->
                if (index == currentCategory) {
                    FilledTonalButton(
                        onClick = {
                            currentCategory = index
                        }
                    ) {
                        Text(text = item)
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            currentCategory =index
                        }
                    ) {
                        Text(text = item)
                    }
                }
            }
        }
        if (transaksiViewModel.transaksiUiState.transaksiList.data?.isEmpty() == true) {
            Text(text = "Anda belum melakukan pesanan", modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        } else {
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                if (currentCategory == 0) {
                    items(sortByDate ?: emptyList() ) {
                        CardTransaksi(transaksi = it)
                    }
                } else {
                    items(filteredTransaksi ?: emptyList()) {
                        CardTransaksi(transaksi = it)
                    }
                }
            }
        }
    }
    
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = !showDialog }) {
            Card {
                Column (
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Deskripsi Status Pemesanan", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.extraSmall)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                    ) {
                        Text(
                            text = "Menunggu Konfrimasi",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "Menunggu pesanan anda diterima oleh admin toko")
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.extraSmall)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                    ) {
                        Text(
                            text = "Diterima",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "Pesanan anda sudah disiapkan dan silahkan ambil di toko")

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.extraSmall)
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            )
                    ) {
                        Text(
                            text = "Selesai",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "Pesanan sudah sudah dibayar dan diambil")
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.extraSmall)
                            .background(
                                color = MaterialTheme.colorScheme.error
                            )
                    ) {
                        Text(
                            text = "Ditolak",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "Pesanan ditolak karena pesananmu tidak diambil di hari yang sama")

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { showDialog = !showDialog },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}