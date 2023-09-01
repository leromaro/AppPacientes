package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

@Composable
fun AttendCard(
    context: Context, viewModel: AttendViewModel, index: Int, item: Pair<String, String>
) {
    Card(
        modifier = Modifier.padding(16.dp, 1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(0.1f)
            ) {
                val number : Int = index +1
                Text(text = "$number")
            }
            Column(
                modifier = Modifier.weight(0.3f)
            ) {
                Text(text = item.first,
                    maxLines = 1)
            }
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Text(text = item.second,
                    maxLines = 1)
            }
            ShowButton(
                text = "",
                icon =  Icons.Default.Clear,
                color = Color.Red,
                modifier = Modifier.width(40.dp),
                onClick = {
                    viewModel.lazyColumnDeleteItem(context, index)
                } )
        }
    }

}
