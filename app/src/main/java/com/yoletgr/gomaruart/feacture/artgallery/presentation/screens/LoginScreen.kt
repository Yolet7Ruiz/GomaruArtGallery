package com.yoletgr.gomaruart.feature.artgallery.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel

@Composable
fun LoginScreen(
    viewModel: ArtGalleryViewModel,
    onLoginSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF3E8FF)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("🎨", fontSize = 40.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bienvenida",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = error,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = error,
            singleLine = true
        )

        if (error) {
            Text(
                text = "Usuario o contraseña incorrectos",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {

                if (username.isNotBlank() && password.isNotBlank()) {
                    onLoginSuccess()
                    error = false
                } else {
                    error = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C3AED)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Entrar al Panel",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}