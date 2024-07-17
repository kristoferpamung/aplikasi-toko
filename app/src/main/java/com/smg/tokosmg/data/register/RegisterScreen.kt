package com.smg.tokosmg.data.register

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smg.tokosmg.R
import com.smg.tokosmg.ui.components.CustomButton
import com.smg.tokosmg.ui.components.CustomPasswordTextField
import com.smg.tokosmg.ui.components.CustomTextField
import com.smg.tokosmg.ui.components.ErrorContainer
import com.smg.tokosmg.ui.components.ErrorText
import com.smg.tokosmg.ui.theme.TextGrey
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun RegisterScreen (
    registerViewModel: RegisterViewModel = viewModel(modelClass = RegisterViewModel::class.java),
    navigateToHome : () -> Unit,
    navigateToLogin: () -> Unit
) {

    val context = LocalContext.current

    Scaffold {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(modifier = Modifier.size(32.dp), painter = painterResource(id = R.drawable.shop), contentDescription = "", tint = MaterialTheme.colorScheme.primaryContainer)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "SUKSES MAKMUR GEMILANG", style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = interFontFamily,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            if (registerViewModel.registerUIState.errorFromFirebase != null) {
                ErrorContainer(errorMessage = registerViewModel.registerUIState.errorFromFirebase!!)
            }
            CustomTextField(
                label = "Nama Lengkap",
                value = registerViewModel.registerUIState.fullName,
                placeholder = "Masukan Nama Lengkap",
                onValueChange = { name ->
                    registerViewModel.onEvent(RegisterUIEvent.FullNameChanged(name))
                },
                isError = registerViewModel.registerUIState.fullNameError != null,
                keyboardType = KeyboardType.Text
            )
            if (registerViewModel.registerUIState.fullNameError != null) {
                ErrorText(errorMessage = registerViewModel.registerUIState.fullNameError!!)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                label = "Email",
                value = registerViewModel.registerUIState.email,
                placeholder = "Masukan Alamat Email",
                onValueChange = { email ->
                    registerViewModel.onEvent(RegisterUIEvent.EmailChanged(email))
                },
                isError = registerViewModel.registerUIState.emailError != null,
                keyboardType = KeyboardType.Email
            )
            if (registerViewModel.registerUIState.emailError != null) {
                ErrorText(errorMessage = registerViewModel.registerUIState.emailError!!)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomPasswordTextField(
                label = "Kata Sandi",
                value = registerViewModel.registerUIState.password,
                placeholder = "Masukan Kata Sandi",
                onValueChange = { password ->
                    registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(password))
                },
                isError = registerViewModel.registerUIState.passwordError != null
            )
            if (registerViewModel.registerUIState.passwordError != null) {
                ErrorText(errorMessage = registerViewModel.registerUIState.passwordError!!)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomPasswordTextField(
                label = "Konfirmasi Kata Sandi",
                value = registerViewModel.registerUIState.confirmPassword,
                placeholder = "Masukan Konfirmasi Kata Sandi",
                onValueChange = { confirmPassword ->
                    registerViewModel.onEvent(RegisterUIEvent.ConfirmPasswordChanged(confirmPassword))
                },
                isError = registerViewModel.registerUIState.confirmPasswordError != null
            )
            if (registerViewModel.registerUIState.confirmPasswordError != null) {
                ErrorText(errorMessage = registerViewModel.registerUIState.confirmPasswordError!!)
            }
            Spacer(modifier = Modifier.height(48.dp))
            CustomButton(
                onClick = {
                    registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked(context))
                },
                text = "Daftar Akun",
                isLoading = registerViewModel.registerUIState.isLoading
            )
            Spacer(modifier = Modifier.height(64.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Apakah sudah memiliki akun?",
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextGrey
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        navigateToLogin.invoke()
                    },
                    text = "Masuk",
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            LaunchedEffect(key1 = registerViewModel.hasUser) {
                if (registerViewModel.hasUser) {
                    navigateToHome.invoke()
                }
            }
        }
    }
}