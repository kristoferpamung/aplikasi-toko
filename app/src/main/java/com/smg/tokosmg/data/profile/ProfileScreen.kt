package com.smg.tokosmg.data.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.data.home.HomeViewModel
import com.smg.tokosmg.data.transaksi.TransaksiViewModel
import com.smg.tokosmg.util.rupiahFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen (
    homeViewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java),
    transaksiViewModel: TransaksiViewModel = viewModel(modelClass = TransaksiViewModel::class.java),
    navigateBack : () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUser()
        transaksiViewModel.getAllTransaksi()
    }

    val transaksiSelesai = transaksiViewModel.transaksiUiState.transaksiList.data?.filter { it.statusTransaksi == "Selesai" }
    val jumlahPengeluaran = transaksiSelesai?.sumOf { it.total }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateBack.invoke() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                },
                title = {
                    Text(text = "Halaman Profil", color = MaterialTheme.colorScheme.primaryContainer)
                }
            )
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainerLowest),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box (
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = homeViewModel.currentUser.user.data?.fotoProfil, contentDescription = "",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(shape = CircleShape)
                        .clickable {

                        }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = homeViewModel.currentUser.user.data?.nama ?: "", style = MaterialTheme.typography.displaySmall)
                Surface (
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.clip(shape = CircleShape).clickable {

                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.pencil_square), contentDescription = "", tint = MaterialTheme.colorScheme.surfaceContainerLowest, modifier = Modifier.padding(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = homeViewModel.currentUser.user.data?.email ?: "", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Card (
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.graph_up), contentDescription = "", tint = MaterialTheme.colorScheme.primaryContainer)
                            Text(text = "Total Pesanan", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text =  transaksiSelesai?.size.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Card (
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.cash_coin), contentDescription = "", tint = MaterialTheme.colorScheme.primaryContainer)
                            Text(text = "Total Pengeluaran", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (jumlahPengeluaran != null) rupiahFormat(jumlahPengeluaran)  else "Rp.0",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}