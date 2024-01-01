package com.example.chatcompose

sealed class AppScreens(val route: String) {
    object InitialScreen : AppScreens("initial_screen")
    object ChatScreen : AppScreens("chat_screen/{usuario}")
}