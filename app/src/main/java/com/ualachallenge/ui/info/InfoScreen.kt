package com.ualachallenge.ui.info

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun InfoScreen(back: () -> Unit){
    Button(
        onClick = { back() }
    ) {
        Text(text = "Volver")
    }
}