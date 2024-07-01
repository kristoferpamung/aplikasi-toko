package com.smg.tokosmg.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smg.tokosmg.R
import com.smg.tokosmg.ui.theme.Gradient1
import com.smg.tokosmg.ui.theme.TextBlack
import com.smg.tokosmg.ui.theme.TextGrey
import com.smg.tokosmg.ui.theme.interFontFamily

@Composable
fun CustomTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    keyboardType: KeyboardType
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = TextGrey,
            fontWeight = FontWeight.Bold,
            fontFamily = interFontFamily
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = interFontFamily
                )
            },
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextBlack,
                unfocusedTextColor = TextBlack,
                unfocusedPlaceholderColor = TextGrey,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                errorPlaceholderColor = MaterialTheme.colorScheme.error
            ),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = interFontFamily
            ),
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            )
        )
    }
}

@Composable
fun CustomPasswordTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = TextGrey,
            fontWeight = FontWeight.Bold,
            fontFamily = interFontFamily
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = interFontFamily
                )
            },
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextBlack,
                unfocusedTextColor = TextBlack,
                unfocusedPlaceholderColor = TextGrey,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                errorPlaceholderColor = MaterialTheme.colorScheme.error
            ),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = interFontFamily
            ),
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    if (passwordVisibility)
                        Icon(
                            painter = painterResource(id = R.drawable.eye_slash_fill),
                            contentDescription = "Password Visibility ON",
                            modifier = Modifier.size(24.dp)
                        )
                    else Icon(
                        painter = painterResource(id = R.drawable.eye_fill),
                        contentDescription = "Password Visibility OFF",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )
    }
}