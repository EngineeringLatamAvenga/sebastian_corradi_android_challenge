package com.ualachallenge.ui.info

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ualachallenge.R

@Composable
fun InfoScreen(back: () -> Unit){
    Button(
        onClick = { back() }
    ) {
        Text(text = stringResource(R.string.back))
    }
}