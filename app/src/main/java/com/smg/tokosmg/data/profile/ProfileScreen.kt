package com.smg.tokosmg.data.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.smg.tokosmg.R
import com.smg.tokosmg.data.home.HomeViewModel
import com.smg.tokosmg.data.transaksi.TransaksiViewModel
import com.smg.tokosmg.ui.theme.success
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
    val userId = Firebase.auth.currentUser?.uid
    val storage = FirebaseStorage.getInstance().reference.child("images/$userId")

    val transaksiSelesai = transaksiViewModel.transaksiUiState.transaksiList.data?.filter { it.statusTransaksi == "Selesai" }
    val jumlahPengeluaran = transaksiSelesai?.sumOf { it.total }
    var showEditName by remember { mutableStateOf(false) }


    // untuk edit foto
    var imgUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imgUri = it
    }

    var imageUrl by remember {
        mutableStateOf("https://firebasestorage.googleapis.com/v0/b/toko-smg-da935.appspot.com/o/images%2F$userId?alt=media&token=c668883c-f6f7-4892-95e7-03b509e79b87")
    }

    LaunchedEffect(key1 = imgUri) {
        imgUri?.let { storage.putFile(it).addOnCompleteListener { success ->
            if (success.isSuccessful) {
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/toko-smg-da935.appspot.com/o/images%2F$userId?alt=media&token=c668883c-f6f7-4892-95e7-03b509e79b87"
            }
        } }
    }

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

            AsyncImage(
                model = imageUrl, contentDescription = "",
                error = painterResource(id = R.drawable.person),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )

            Spacer(modifier = Modifier.height(32.dp))
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = homeViewModel.currentUser.user.data?.nama ?: "", style = MaterialTheme.typography.displaySmall)
                Surface (
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .clickable {
                            showEditName = !showEditName
                        }
                ) {
                    Icon(painter = painterResource(id = R.drawable.pencil_square), contentDescription = "", tint = MaterialTheme.colorScheme.surfaceContainerLowest, modifier = Modifier.padding(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = homeViewModel.currentUser.user.data?.email ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = homeViewModel.currentUser.user.data?.nomorHp ?: "", style = MaterialTheme.typography.bodyMedium)
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
            if (showEditName) {
                var newName by remember { mutableStateOf("") }
                var textFieldError by remember { mutableStateOf(false) }

                Dialog(onDismissRequest = { showEditName = !showEditName }) {
                    Surface (
                        shape = CardDefaults.shape
                    ) {
                        Column (
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "Edit Nama",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                isError = textFieldError,
                                value = newName,
                                onValueChange = {newName = it},
                                placeholder = {
                                    Text(text = "Masukan nama baru")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                if (newName.isNotEmpty()) {
                                    homeViewModel.editUsername(newName, onSuccess = {
                                        if(it) {
                                            showEditName = !showEditName
                                        } else {
                                            textFieldError = !textFieldError
                                        }
                                    })
                                } else {
                                    textFieldError = !textFieldError
                                }
                            },
                                modifier = Modifier.fillMaxWidth()
                                ) {
                                Text(text = "Ok")
                            }
                        }
                    }
                }
            }
        }
    }
}