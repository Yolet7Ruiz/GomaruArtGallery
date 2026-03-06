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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel

@Composable
fun LoginScreen(
    viewModel: ArtGalleryViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Solo observamos el éxito del login para navegar
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLoginSuccess()
        }
    }

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
            value = viewModel.loginEmail, // Lee del VM
            onValueChange = { viewModel.onLoginEmailChange(it) }, // Escribe en el VM
            placeholder = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = viewModel.loginError || state.errorMessage != null,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.loginPassword, // Lee del VM
            onValueChange = { viewModel.onLoginPasswordChange(it) }, // Escribe en el VM
            placeholder = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.loginError || state.errorMessage != null,
            singleLine = true
        )

        // Error dinámico desde el VM o el Estado global
        if (viewModel.loginError || state.errorMessage != null) {
            Text(
                text = state.errorMessage ?: "Usuario o contraseña incorrectos",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp),
                color = Color(0xFF7C3AED)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.performLogin() }, // Lógica delegada al VM
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C3AED)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = !state.isLoading
        ) {
            Text(
                text = "Entrar al Panel",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}