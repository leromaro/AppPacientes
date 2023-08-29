package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

@Composable
fun AttendCard(
    context: Context, viewModel: AttendViewModel, index: Int, item: Pair<String, String>
) {
    Card(modifier = Modifier
        .padding(16.dp, 1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Text(text = item.first)
            }
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Text(text = item.second)
            }
            ShowIcon(
                icon = Icons.Default.Clear, description = "clear attend", onIconClick = {
                    viewModel.lazyColumnDeleteItem(context, index)
                }, Color.Red
            )
        }
    }

}
