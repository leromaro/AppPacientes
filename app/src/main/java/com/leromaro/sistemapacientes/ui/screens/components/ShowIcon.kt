package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable

fun ShowIcon(
    icon: ImageVector,
    description: String,
    onIconClick: () -> Unit,
    color : Color
) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(
                color,
                shape = RoundedCornerShape(percent = 50))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier.clickable {
                onIconClick()
            }
        )
    }
}
