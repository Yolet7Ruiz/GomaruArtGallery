package com.yoletgr.gomaruart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoletgr.gomaruart.feature.artgallery.di.ArtGalleryProvider
import com.yoletgr.gomaruart.feature.artgallery.presentation.screens.AddArtScreen
import com.yoletgr.gomaruart.feature.artgallery.presentation.screens.ArtGalleryAdminScreen
import com.yoletgr.gomaruart.feature.artgallery.presentation.screens.LoginScreen
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModel
import com.yoletgr.gomaruart.ui.theme.ArtGalleryTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val viewModel: ArtGalleryViewModel = viewModel(
                        factory = ArtGalleryProvider.factory
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                viewModel = viewModel,
                                onLoginSuccess = {
                                    navController.navigate("gallery") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("gallery") {
                            ArtGalleryAdminScreen(
                                viewModel = viewModel,
                                onNavigateToAdd = {
                                    navController.navigate("add_art")
                                },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("gallery") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("add_art") {
                            AddArtScreen(
                                viewModel = viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}