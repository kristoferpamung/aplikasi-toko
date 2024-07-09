package com.smg.tokosmg.data.transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smg.tokosmg.ui.components.CardTransaksi
import com.smg.tokosmg.ui.theme.interFontFamily

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
        "Selesai",
        "Gagal"
    )

    var currentCategory by remember {
        mutableIntStateOf(0)
    }

    val allTransaksi = transaksiViewModel.transaksiUiState.transaksiList.data

    val filteredTransaksi = allTransaksi?.filter { transaksi ->
        transaksi.statusTransaksi.contains(items[currentCategory], ignoreCase = true)
    }

    Column (
        modifier = Modifier.padding(padding)
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
                    items(allTransaksi ?: emptyList() ) {
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
}