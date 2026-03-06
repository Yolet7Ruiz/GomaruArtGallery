package com.yoletgr.gomaruart.feature.artgallery.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoletgr.gomaruart.feature.artgallery.presentation.components.AdminArtCard
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel
import com.yoletgr.gomaruart.feacture.artgallery.presentation.screens.UpdateArtScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtGalleryAdminScreen(
    viewModel: ArtGalleryViewModel,
    onNavigateToAdd: () -> Unit,
    onLogout: () -> Unit
) {
    // Escuchamos el estado único del ViewModel
    val state by viewModel.uiState.collectAsState()

    // LÓGICA DE NAVEGACIÓN ELEVADA:
    // En lugar de un remember local, el ViewModel nos dirá si hay una obra seleccionada para editar
    if (state.selectedArtForEdit != null) {
        UpdateArtScreen(
            obra = state.selectedArtForEdit!!,
            viewModel = viewModel,
            onNavigateBack = { viewModel.onCancelEdit() } // El VM limpia la selección
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Galería") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    ),
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToAdd,
                    containerColor = Color(0xFF7C3AED),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFF7C3AED)
                        )
                    }

                    state.errorMessage != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Error: ${state.errorMessage}", color = Color.Red)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadArt() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }

                    state.artItems.isEmpty() -> {
                        Text(
                            text = "No hay obras. ¡Agrega una!",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.artItems) { obra ->
                                AdminArtCard(
                                    obra = obra,
                                    onDelete = { viewModel.deleteArt(it) },
                                    onEdit = { viewModel.onEditClick(it) } // Avisamos al VM que queremos editar
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}