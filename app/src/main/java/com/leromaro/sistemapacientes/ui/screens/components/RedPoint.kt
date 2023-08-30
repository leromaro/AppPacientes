package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RedPoint(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(10.dp)
            .background(Color.Red, shape = CircleShape)
    )
}