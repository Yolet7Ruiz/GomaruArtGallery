package com.yoletgr.gomaruart.feature.artgallery.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArtScreen(
    viewModel: ArtGalleryViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf(1) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Obra") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Selector de imagen
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedImageUri == null) "Seleccionar Imagen" else "Imagen Seleccionada")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Precio
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Categoría
            Row {
                CategoryButton(
                    text = "Anime",
                    selected = categoryId == 1,
                    onClick = { categoryId = 1 }
                )
                CategoryButton(
                    text = "Realismo",
                    selected = categoryId == 2,
                    onClick = { categoryId = 2 }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (title.isNotBlank() && price.isNotBlank()) {
                            val newArt = ArtItem(
                                title = title,
                                description = description,
                                price = price.toDoubleOrNull() ?: 0.0,
                                filePath = selectedImageUri?.toString() ?: "",
                                categoryId = categoryId
                            )
                            viewModel.addArt(newArt)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C3AED)
                    )
                ) {
                    Text("Guardar")
                }

                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun CategoryButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF7C3AED) else Color.LightGray
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(text)
    }
}