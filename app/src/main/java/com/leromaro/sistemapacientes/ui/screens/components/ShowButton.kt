package com.leromaro.sistemapacientes.ui.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ShowButton(
    text : String,
    icon : ImageVector,
    color : Color,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
){
    Log.d("MyLog","llega $text")
    if(text.isNotEmpty()){
        Log.d("MyLog","creo boton $text")
            Button(
                onClick =  onClick ,
                modifier = modifier,
            )  {
                Text(text = text)
            }
        }else{
        Log.d("MyLog","creo icono $text")
        Box(
            modifier = Modifier
                .size(25.dp)
                .background(
                    color= color,
                    shape = RoundedCornerShape(percent = 50)
                )
        ) {
            IconButton(
                onClick = onClick,
                modifier = modifier
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }

}