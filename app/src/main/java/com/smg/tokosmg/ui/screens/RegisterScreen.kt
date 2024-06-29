package com.smg.tokosmg.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smg.tokosmg.data.RegisterUIEvent
import com.smg.tokosmg.data.RegisterViewModel
import com.smg.tokosmg.ui.components.CustomButton
import com.smg.tokosmg.ui.components.CustomPasswordTextField
import com.smg.tokosmg.ui.components.CustomTextField
import com.smg.tokosmg.ui.components.ErrorContainer
import com.smg.tokosmg.ui.components.ErrorText
import com.smg.tokosmg.ui.theme.Gradient2
import com.smg.tokosmg.ui.theme.TextGrey
import com.smg.tokosmg.ui.theme.latoFontFamily

@Composable
fun RegisterScreen (
    registerViewModel: RegisterViewModel = viewModel()
) {

    val context = LocalContext.current

    Scaffold { padding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
        ) {
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
                text = "Daftar Akun"
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
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextGrey
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable {  },
                    text = "Masuk",
                    fontSize = 16.sp,
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Gradient2
                )
            }
        }
    }
}