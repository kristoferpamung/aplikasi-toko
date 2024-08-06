package com.smg.tokosmg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.model.HargaProduk
import com.smg.tokosmg.ui.theme.TokoSMGTheme
import com.smg.tokosmg.ui.theme.interFontFamily
import com.smg.tokosmg.util.rupiahFormat

@Composable
fun ProductCard (
    productId: String,
    prices: List<HargaProduk>,
    quantity: Number,
    productUnit: String,
    name: String,
    onClick: () -> Unit
) {

    val hargaProduk = prices.sortedBy { it.harga }
    val imgUrl = "https://firebasestorage.googleapis.com/v0/b/toko-smg-da935.appspot.com/o/products%2F$productId.jpg?alt=media&token=fef749a9-24a0-4529-bf97-4e20552f961a"

    var seletedChip by remember {
        mutableIntStateOf(0)
    }

    val formatHargaRupiah = rupiahFormat(hargaProduk[seletedChip].harga)

    ElevatedCard(
        onClick = {
            onClick.invoke()
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        modifier = Modifier
            .width(250.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = imgUrl,
                    error = painterResource(id = R.drawable.no_product_img),
                    contentDescription = "product image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CardDefaults.elevatedShape),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .background(
                            color = if (quantity.toLong() <= 0) {
                                MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                            } else {
                                MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.8f)
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
                            text = if (quantity.toLong() <= 0) {
                                "Stok Habis"
                            } else {
                                "$quantity $productUnit"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = interFontFamily,
                                fontSize = 10.sp
                            )
                        )
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
        }
    }
}


@Preview
@Composable
private fun ProductCardPrev() {
    TokoSMGTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
        ) {
            items(8) {
//                ProductCardSmall(
//                    image = "https://images.pexels.com/photos/15843088/pexels-photo-15843088/free-photo-of-dark-photo-of-magnolia-flowers-on-a-twig.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
//                    title = "Gula Pasir",
//                    price = 15000
//                    )
//                ProductCard(
//                    prices = listOf(HargaProduk(price = 10000, unit = "Kg"), HargaProduk(price = 100000, unit = "drum")),
//                    imgUrl = "https://images.pexels.com/photos/15843088/pexels-photo-15843088/free-photo-of-dark-photo-of-magnolia-flowers-on-a-twig.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
//                    quantity = 256,
//                    productUnit = "Kg",
//                    name = "Kecap"
//                )
            }
        }

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
//        ) {
//            items(8) {
//                ProductCard(
//                    name = "Minyak Goreng",
//                    price = 10000,
//                    image = "https://images.pexels.com/photos/15843088/pexels-photo-15843088/free-photo-of-dark-photo-of-magnolia-flowers-on-a-twig.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
//                    unit = "Kg",
//                    quantity = 1000,
//                    unitPrices = listOf("0.1Kg","Kg")
//                )
//            }
//        }
    }
}