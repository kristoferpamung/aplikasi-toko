package com.smg.tokosmg.data.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.ui.components.CustomChip
import com.smg.tokosmg.ui.components.ProductCard
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat

@Composable
fun BerandaScreen (
    berandaViewModel: BerandaViewModel = viewModel(modelClass = BerandaViewModel::class.java),
    padding : PaddingValues
) {
    val berandaUiState = berandaViewModel.berandaUiState
    val context = LocalContext.current
    var cari by remember {
        mutableStateOf("")
    }
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    var openAlertDialog by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        berandaViewModel.getAllProducts()
    }

    val allProduct = berandaUiState.productList.data

    val filterProduct = allProduct?.filter { produk ->
        produk.nama.contains(cari, ignoreCase = true)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        OutlinedTextField(
            value = cari,
            onValueChange = {
                cari = it
            },
            placeholder = {
                Text(text = "Cari Produk")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            enabled = !openAlertDialog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.extraLarge),
            shape = MaterialTheme.shapes.extraLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledIndicatorColor = Color.Transparent
            )
        )

        when (berandaUiState.productList){
            is Resources.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
            is Resources.Error -> {
                Text(text = "Terjadi Error")
            }
            is Resources.Success -> {
                if (filterProduct?.isEmpty() == true) {
                    Text(
                        text = "Barang tidak tersedia",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                ) {
                    itemsIndexed(filterProduct ?: emptyList()) { index, produk ->
                        ProductCard(
                            productId = produk.id,
                            prices = produk.hargaProduk,
                            quantity = produk.stok,
                            productUnit = produk.satuan,
                            name = produk.nama,
                            onClick = {
                                openAlertDialog = !openAlertDialog
                                selectedItem = index
                            }
                        )
                    }
                }
            }
        }
    }

    if (openAlertDialog) {
        val selectedProduct = filterProduct?.get(selectedItem)

        var jumlahBarang by remember {
            mutableIntStateOf(1)
        }
        var seletedChip by remember {
            mutableIntStateOf(0)
        }
        val hargaProduk = selectedProduct?.hargaProduk?.sortedBy { it.harga }

        val formatHargaRupiah = rupiahFormat(hargaProduk?.get(seletedChip)?.harga ?: 0)
        val formatTotalRupiah = rupiahFormat(hargaProduk?.get(seletedChip)?.harga?.times(jumlahBarang)!!)

        val produkItem = ProdukItem(
            namaBarang = selectedProduct.nama,
            jumlah = jumlahBarang.toLong(),
            harga = hargaProduk[seletedChip].harga,
            satuan = hargaProduk[seletedChip].satuan,
            bobot = hargaProduk[seletedChip].amount,
            subTotal = hargaProduk[seletedChip].harga.times(jumlahBarang)
        )

        Dialog(
            onDismissRequest = {
                openAlertDialog = !openAlertDialog
            }
        ) {
            val imgUrl = "https://firebasestorage.googleapis.com/v0/b/toko-smg-da935.appspot.com/o/products%2F${selectedProduct.id}.jpg?alt=media&token=fef749a9-24a0-4529-bf97-4e20552f961a"

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = AlertDialogDefaults.shape)
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                Column (
                    modifier = Modifier.padding(12.dp)
                ) {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        AsyncImage(
                            model = imgUrl,
                            error = painterResource(id = R.drawable.no_product_img),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = CardDefaults.shape)
                        )
                        Column (
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.error,
                                        shape = MaterialTheme.shapes.large
                                    )
                                    .clip(shape = MaterialTheme.shapes.large)
                                    .clickable {
                                        openAlertDialog = !openAlertDialog
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                                    modifier = Modifier.graphicsLayer(rotationZ = 45f)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(all = 8.dp)
                                    .background(
                                        color = if (selectedProduct.stok <= 0) {
                                            MaterialTheme.colorScheme.error.copy(
                                                alpha = 0.8f
                                            )
                                        } else { MaterialTheme.colorScheme.surfaceContainerLowest.copy(
                                                alpha = 0.8f
                                            )
                                        },
                                        shape = MaterialTheme.shapes.large
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                            ){
                                Row (
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
                                        text = if (selectedProduct.stok <= 0) { "Stok Habis" } else {
                                            "${selectedProduct.stok} ${selectedProduct.satuan}"
                                        },
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
                        text = selectedProduct.nama,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = interFontFamily,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatHargaRupiah,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        hargaProduk.forEachIndexed { index, it ->
                            CustomChip(
                                selected = index == seletedChip,
                                text = it.satuan,
                                onClick = {
                                    seletedChip = index
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FilledTonalButton(
                            enabled = jumlahBarang > 1,
                            onClick = {
                                if (jumlahBarang > 1) {
                                    jumlahBarang -= 1
                                }
                            }
                        ) {
                            Text(
                                text = "-",
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(32.dp)
                                .clip(shape = MaterialTheme.shapes.extraLarge),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "$jumlahBarang ${hargaProduk[seletedChip].satuan}" ,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = interFontFamily
                                )
                            )
                        }
                        FilledTonalButton(
                            enabled = jumlahBarang < selectedProduct.stok,
                            onClick = {
                                if (jumlahBarang < selectedProduct.stok) {
                                    jumlahBarang += 1
                                }
                            }
                        ) {
                            Text(text = "+")
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        enabled = selectedProduct.stok > 0,
                        onClick = {
                            berandaViewModel.updateKeranjang(context =context, produkItem = produkItem)
                            openAlertDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = formatTotalRupiah,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(painter = painterResource(id = R.drawable.cart_plus_fill), contentDescription = "")
                    }
                }
            }
        }
    }
}