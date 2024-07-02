package com.smg.tokosmg.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.data.BerandaViewModel
import com.smg.tokosmg.model.ProductModel
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.ui.components.CustomButton
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun BerandaScreen (
    paddingValues: PaddingValues,
    berandaViewModel: BerandaViewModel = viewModel()
) {

    val berandaUiState = berandaViewModel.berandaUiState

    var search by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableIntStateOf(1)
    }

    LaunchedEffect(key1 = Unit) {
        berandaViewModel.getAllProducts()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
            },
            placeholder = {
                Text(text = "Cari Produk")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.extraSmall),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 12.dp, top = 0.dp, bottom = 16.dp, end = 12.dp)
                ) {
                    items(berandaUiState.productList.data ?: emptyList()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.extraSmall
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.scrim,
                                    shape = MaterialTheme.shapes.extraSmall
                                )
                        ) {
                            Column {
                                AsyncImage(
                                    model = it.productImg,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                        .height(156.dp)
                                        .clip(shape = MaterialTheme.shapes.extraSmall),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = it.productName,
                                    fontFamily = interFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 26.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = it.productPrices[1].price.toString(),
                                    fontFamily = interFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                                Row (
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp)
                                ) {
                                    FilterChip(
                                        onClick = {  },
                                        label = {
                                            Text(text = "Kg")
                                        },
                                        selected = true
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    FilterChip(
                                        onClick = {  },
                                        label = {
                                            Text(text = "gram")
                                        },
                                        selected = false
                                    )
                                }
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 0.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            quantity -= 1
                                        },
                                        shape = MaterialTheme.shapes.extraSmall
                                    ) {
                                        Text(
                                            text = "-",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                shape = MaterialTheme.shapes.small,
                                                color = MaterialTheme.colorScheme.surfaceContainer
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        BasicTextField(
                                            value = quantity.toString(),
                                            onValueChange = {
                                                quantity = it.toInt()
                                            },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                keyboardType = KeyboardType.Number
                                            ),
                                            singleLine = true,
                                            textStyle = TextStyle(
                                                textAlign = TextAlign.Center,
                                                fontSize = 26.sp
                                            ),
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        )
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            quantity += 1
                                        },
                                        shape = MaterialTheme.shapes.extraSmall
                                    ) {
                                        Text(
                                            text = "+",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    onClick = {  }
                                ) {
                                    Text(text = "Masukan Keranjang")
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}