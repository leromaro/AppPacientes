package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import com.leromaro.sistemapacientes.ui.viewModel.PracticasViewModel

@Composable
fun AtencionCard(context: Context, viewModel : PracticasViewModel, indice: Int, item : Pair<String,String>) {
    Row{
        Text(text = "${item.first} - ${item.second}")
        ShowIcon(
            icon = Icons.Default.Clear,
            description = "borrar atención",
            onIconClick = { viewModel.listAttend.removeAt(indice)
                viewModel.lazyColumnDeleteItem(context) },
            Color.Red)
        }
    }
