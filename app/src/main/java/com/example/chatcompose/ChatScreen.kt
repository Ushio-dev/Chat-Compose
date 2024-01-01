package com.example.chatcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ChatScreen(viewModel: ChatViewModel, navController: NavController, usuario: String) {
    val mensajes by viewModel.mensajes.collectAsState(initial = emptyList())

    var mensaje by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(mensajes.reversed()) { msg ->
                TextBox(testPosition = msg.userId != usuario, msg.msg ?: "")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InputMessage(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Bottom),
                onChange = { msg -> mensaje = msg },
                mensaje = mensaje
            )
            SendMessageButton(
                viewModel,
                mensaje,
                limpiar = { mensaje = "" },
                usuarioRegistrado = usuario)
        }
    }
}

@Composable
fun TextBox(testPosition: Boolean, texto: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (testPosition) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                text = texto,
                color = Color.Black,
                fontSize = 17.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun InputMessage(modifier: Modifier, onChange: (String) -> Unit, mensaje: String) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = mensaje,
        onValueChange = { onChange(it) },
    )
}

@Composable
fun SendMessageButton(
    viewModel: ChatViewModel,
    texto: String,
    limpiar: () -> Unit,
    usuarioRegistrado: String
) {
    IconButton(onClick = {
        viewModel.onEvent(ChatEvents.EnviarMensaje(texto, usuario = usuarioRegistrado))
        limpiar()
    }) {
        Icon(imageVector = Icons.Default.Send, contentDescription = "")
    }
}