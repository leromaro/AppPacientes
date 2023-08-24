package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowButton(
    text : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {}
){
    Button(
        onClick =  onClick ,
        modifier = modifier
        )  {
        Text(text = text)
    }
}