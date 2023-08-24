package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

@Composable
fun AttendCard(context: Context, viewModel : AttendViewModel, index: Int, item : Pair<String,String>) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround){
        Text(text = "${item.first} - ${item.second}")
        ShowIcon(
            icon = Icons.Default.Clear,
            description = "clear attend",
            onIconClick = { viewModel.listAttend.removeAt(index)
                viewModel.lazyColumnDeleteItem(context) },
            Color.Red)
        }
    }
