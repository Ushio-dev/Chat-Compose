package com.example.chatcompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(viewModel: ChatViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.InitialScreen.route) {
        composable(route = AppScreens.InitialScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.ChatScreen.route) {
            val usuario = it.arguments?.getString("usuario")
            ChatScreen(viewModel = viewModel, navController = navController, usuario = usuario ?: "yo")
        }
    }
}