package com.example.chatcompose

sealed interface ChatEvents {
    data class EnviarMensaje(var mensaje: String, var usuario: String): ChatEvents
}