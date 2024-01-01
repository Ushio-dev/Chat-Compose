package com.example.chatcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatcompose.data.FirebaseDataManager
import kotlinx.coroutines.launch

class ChatViewModel(
    private val firebaseDataManager: FirebaseDataManager = FirebaseDataManager()
) : ViewModel() {
    val mensajes = firebaseDataManager.getMessagges()
    fun onEvent(events: ChatEvents) {
        when (events) {
            is ChatEvents.EnviarMensaje -> {
                viewModelScope.launch {
                    var userId = firebaseDataManager.getIdReference()
                    firebaseDataManager.writeMessage(userId ?: "", Mensaje(events.usuario,events.mensaje))
                }
            }
        }
    }
}