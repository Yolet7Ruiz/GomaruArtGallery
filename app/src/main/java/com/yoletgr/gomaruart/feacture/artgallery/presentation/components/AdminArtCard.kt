package com.yoletgr.gomaruart.feature.artgallery.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

@OptIn(ExperimentalMaterial3Api::class) // Necesario para el onClick de la Card
@Composable
fun AdminArtCard(
    obra: ArtItem,
    onDelete: (Int) -> Unit,
    onEdit: (ArtItem) -> Unit //  Acción para editar
) {
    Card(
        onClick = { onEdit(obra) }, // Al tocar la tarjeta se abre la edición
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = obra.filePath.ifEmpty { "https://picsum.photos/400/600" },
                contentDescription = obra.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = obra.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Text(
                    text = "$${obra.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF7C3AED)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //  Icono de editar para que el usuario sepa que puede dar click
                    Text(
                        text = "Editar",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 4.dp)
                    )

                    IconButton(onClick = { onDelete(obra.id) }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}