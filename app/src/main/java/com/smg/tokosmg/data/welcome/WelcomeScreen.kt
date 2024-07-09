package com.smg.tokosmg.data.welcome

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smg.tokosmg.R
import com.smg.tokosmg.ui.components.CustomButton
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun WelcomeScreen (
    navigateToLogin : () -> Unit
) {
    var checkSyarat by remember {
        mutableStateOf(true)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val googleMapUri = remember { Uri.parse("https://maps.app.goo.gl/Yh87U2T9x3DkyDuQ6") }

    
    Scaffold {
        Image(
            painter = painterResource(id = R.drawable.welcome_img), contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(color = Color.Black.copy(alpha = 0.6f)))
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Checkbox(checked = checkSyarat, onCheckedChange = {checkSyarat = !checkSyarat})
                TextButton(
                    onClick = {
                        showDialog = !showDialog
                    },
                    colors = ButtonDefaults.textButtonColors().copy(
                        contentColor = Color.White
                    )
                ){
                    Text(text = "Saya menyetujui syarat dan ketentuan yang berlaku", textDecoration = TextDecoration.Underline)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
            CustomButton(
                onClick = {
                    if (checkSyarat) {
                        navigateToLogin.invoke()
                    } else {
                         Toast.makeText(context, "Belum menyetujui syarat dan ketentuan", Toast.LENGTH_LONG).show()
                    } },
                text = "Masuk",
                isLoading = false
            )
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 56.dp)
        ) {
            Text(
                text = "SELAMAT DATANG",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = interFontFamily,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Icon(
                painter = painterResource(R.drawable.shop),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "TOKO SUKSES MAKMUR GEMILANG",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = interFontFamily,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pesan berbagai kebutuhan anda dengan mudah dan cepat.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = interFontFamily,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Keuntungan:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = interFontFamily,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.cash_coin), contentDescription = "", tint = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Harga terjangkau, cocok untuk pembelian jumlah banyak.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = interFontFamily,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.clock_history), contentDescription = "", tint = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ambil pesananmu tanpa perlu mengantri",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = interFontFamily,
                        color = Color.White
                    )
                )
            }

            if(showDialog) {
                Dialog(onDismissRequest = { showDialog = !showDialog }) {
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Syarat & Ketentuan",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "1. Pastikan lokasi Anda berada tidak jauh dari toko. Silakan cek lokasi toko sebelum melakukan transaksi.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = interFontFamily
                                )
                            )

                            TextButton(onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, googleMapUri)
                                context.startActivity(intent)
                            }) {
                                Text(text = "Cek lokasi toko")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "2. Transaksi yang tidak diambil selama hari yang sama akan dibatalkan.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = interFontFamily
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "3. Melakukan transaksi sebanyak tiga kali berturut-turut tanpa mengambil pesanan akan mengakibatkan pemblokiran akun.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = interFontFamily
                                )
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    showDialog = !showDialog
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Text(text = "Saya mengerti")
                            }
                        }
                    }
                }
            }
        }
    }
}