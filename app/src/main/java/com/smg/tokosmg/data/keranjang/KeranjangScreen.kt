package com.smg.tokosmg.data.keranjang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import com.smg.tokosmg.data.beranda.BerandaViewModel
import com.smg.tokosmg.data.home.HomeViewModel
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.ui.components.ProductCardSmall
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat
import java.util.Calendar

@Composable
fun KeranjangScreen(
    padding : PaddingValues,
    homeViewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java),
    berandaViewModel: BerandaViewModel = viewModel(modelClass = BerandaViewModel::class.java),
    keranjangViewModel: KeranjangViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUser()
        berandaViewModel.getAllProducts()
    }
    val listItems = homeViewModel.currentUser.user.data?.keranjang
    val productItems = berandaViewModel.berandaUiState.productList.data
    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    fun img(nama: String) : String {
        val produk = productItems?.filter {
            it.nama == nama
        }
        return produk?.get(0)?.id ?: ""
    }

    fun getStok (nama: String) : Long {
        val produk = productItems?.filter {
            it.nama == nama
        }
        return produk?.get(0)?.stok ?: 0
    }

    fun getTotalHarga() : Long {
        return listItems.orEmpty().sumOf { it.subTotal }
    }

    if (listItems?.isEmpty() == true) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Tidak ada item di Keranjang")
        }
    }

    if (showDialog){
        Dialog(onDismissRequest = {
            showDialog = !showDialog
            error = false
        }) {
            Surface (
                shape = CardDefaults.shape
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Anda yakin melakukan pesanan?", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = buildAnnotatedString {
                        append("Tolong pesanan tersebut diambil sebelum ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("20 menit ");
                        }
                        append("setelah melakukan pemesanan, jika tidak maka pesanan tersebut dibatalkan")
                    }, style = MaterialTheme.typography.bodyMedium)
                    if (error){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Terdapat pesanan hari ini yang belum selesai atau gagal", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            val calendar = Calendar.getInstance()
                            calendar.add(Calendar.MINUTE, 20)
                            val newTime = calendar.time
                            val transaksi = listItems?.let {
                                Transaksi(
                                    item = it,
                                    total = getTotalHarga(),
                                    expiredDate = Timestamp(newTime)
                                )
                            }

                            transaksi?.item?.let {
                                keranjangViewModel.simpanTransaksi(transaksi, context, transaksiOk = { oke ->
                                    if (oke){
                                        error = false
                                        keranjangViewModel.kurangiStok(it, onSuccess = {
                                            isLoading = false
                                            showDialog = !showDialog
                                        })
                                    } else {
                                        error = true
                                        isLoading = false
                                    }
                                })

                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Pesan")
                            if (isLoading) {
                                Spacer(modifier = Modifier.width(8.dp))
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.surfaceContainerLowest, modifier = Modifier.size(16.dp))
                            }

                        }
                    }
                }
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn (
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(listItems.orEmpty()){ item ->
                ProductCardSmall(
                    productId = img(item.namaBarang),
                    stok = getStok(item.namaBarang),
                    produkItem = item,
                    onQuantityChange = keranjangViewModel::updateJumlahProduk,
                    onDelete = keranjangViewModel::hapusBarangKeranjang
                )
            }
        }

        if (listItems?.isNotEmpty() == true) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLowest),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "TOTAL BAYAR",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = interFontFamily,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
                Text(
                    text = rupiahFormat(getTotalHarga()),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = interFontFamily,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        showDialog = !showDialog
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "Pesan")
                }
            }
        }
    }
}