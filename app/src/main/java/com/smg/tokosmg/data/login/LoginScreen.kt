package com.smg.tokosmg.data.login

import android.content.Context
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
fun LoginScreen(
    loginViewModel : LoginViewModel = viewModel(modelClass = LoginViewModel::class.java),
    navigateToHome : () -> Unit,
    navigateToRegister: () -> Unit
) {

    val context: Context = LocalContext.current

    Scaffold {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalArrangement = Arrangement.Center
        ) {
            Column (modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
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
            if (loginViewModel.loginUIState.errorFromFirebase != null) {
                ErrorContainer(errorMessage = loginViewModel.loginUIState.errorFromFirebase!!)
            }
            CustomTextField(
                label = "Email",
                value = loginViewModel.loginUIState.email,
                placeholder = "Masukan Alamat Email",
                onValueChange = { email ->
                    loginViewModel.onEvent(LoginUIEvent.EmailChanged(email))
                },
                isError = loginViewModel.loginUIState.emailError != null,
                keyboardType = KeyboardType.Email
            )
            if (loginViewModel.loginUIState.emailError != null) {
                ErrorText(errorMessage = loginViewModel.loginUIState.emailError!!)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomPasswordTextField(
                label = "Password",
                value = loginViewModel.loginUIState.password,
                placeholder = "Masukan Password",
                onValueChange = { password ->
                    loginViewModel.onEvent(LoginUIEvent.PasswordChanged(password))
                },
                isError = loginViewModel.loginUIState.passwordError != null
            )
            if (loginViewModel.loginUIState.passwordError != null) {
                ErrorText(errorMessage = loginViewModel.loginUIState.passwordError!!)
            }
            Spacer(modifier = Modifier.height(48.dp))
            CustomButton(
                onClick = {
                    loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked(context))
                },
                text = "Masuk",
                isLoading = loginViewModel.loginUIState.isLoading
            )
            Spacer(modifier = Modifier.height(64.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Apakah belum memiliki akun?",
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextGrey
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        navigateToRegister.invoke()
                    },
                    text = "Mendaftar",
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LaunchedEffect(key1 = loginViewModel.hasUser) {
            if (loginViewModel.hasUser) {
                navigateToHome.invoke()
            }
        }
    }
}