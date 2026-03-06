package com.yoletgr.gomaruart.feacture.artgallery.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateArtScreen(
    obra: ArtItem,
    viewModel: ArtGalleryViewModel,
    onNavigateBack: () -> Unit
) {
    // Elevamos el estado: Cargamos los datos de la obra en el ViewModel al iniciar
    LaunchedEffect(obra) {
        viewModel.loadArtToEdit(obra)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Obra") },
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
            // Título: Lee del ViewModel y le avisa de cambios
            OutlinedTextField(
                value = viewModel.titleInput,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción: Lee del ViewModel y le avisa de cambios
            OutlinedTextField(
                value = viewModel.descriptionInput,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Precio: Lee del ViewModel y le avisa de cambios
            OutlinedTextField(
                value = viewModel.priceInput,
                onValueChange = { viewModel.onPriceChange(it) },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Selector de Categoría usando el estado del ViewModel
            Text("Categoría:", style = MaterialTheme.typography.labelLarge)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                CategoryButton(
                    text = "Anime",
                    selected = viewModel.categoryIdInput == 1,
                    onClick = { viewModel.onCategoryChange(1) }
                )
                CategoryButton(
                    text = "Realismo",
                    selected = viewModel.categoryIdInput == 2,
                    onClick = { viewModel.onCategoryChange(2) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones de Acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (viewModel.titleInput.isNotBlank() && viewModel.priceInput.isNotBlank()) {
                            // Creamos la copia con los datos actualizados del ViewModel
                            val updatedArt = obra.copy(
                                title = viewModel.titleInput,
                                description = viewModel.descriptionInput,
                                price = viewModel.priceInput.toDoubleOrNull() ?: obra.price,
                                categoryId = viewModel.categoryIdInput
                            )
                            viewModel.updateArt(updatedArt)
                            // El cierre de la pantalla lo maneja el ViewModel al limpiar el estado
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Actualizar")
                }

                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
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
            containerColor = if (selected) Color(0xFF7C3AED) else Color(0xFFE5E7EB),
            contentColor = if (selected) Color.White else Color.Black
        ),
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}