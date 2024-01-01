package com.example.chatcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var usuario by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = usuario,
            onValueChange = {
                usuario = it
            },
            isError = error,
            supportingText = {
                if (error) {
                    Text(text = "Ingrese usuario", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedButton(onClick = {
            if (verificarNombre(usuario)) {
                navController.navigate("chat_screen/$usuario")
            } else {
                error = true
            }
        }) {
            Text(text = "Ingresar")
        }
    }
}


fun verificarNombre(usuario: String): Boolean {
    return usuario.isNotEmpty()
}