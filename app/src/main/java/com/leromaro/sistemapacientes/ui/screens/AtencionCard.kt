package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.viewModel.PracticasViewModel

@Composable
fun AtencionCard(context: Context, viewModel : PracticasViewModel, indice: Int, item : Pair<String,String>) {
    Row{
        Text(text = "${item.first} - ${item.second}")
        ShowButton(
            text = "X",
            modifier = Modifier.width(40.dp),
            onClick = {viewModel.listaAtencion.removeAt(indice)
                viewModel.lazyColumnDeleteItem(context)})
        }
    }
