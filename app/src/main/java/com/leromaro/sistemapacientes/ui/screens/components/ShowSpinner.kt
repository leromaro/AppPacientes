package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowSpinner(
    tittle: String,
    currentValue: String,
    expanded: Boolean,
    onValueSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onSusses: () -> Unit,
    itemList: List<String>
) {
// TITTLE
    Text(
        text = tittle, fontSize = 15.sp
    )
//SPINNER PATIENTS
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.clickable {
                onSusses()
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(250.dp), text = currentValue, fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown, contentDescription = null
            )
            DropdownMenu(expanded = expanded, onDismissRequest = {
                onDismiss()
            }) {
                itemList.forEach { item ->
                    DropdownMenuItem(text = { Text(text = item, maxLines = 1) }, onClick = {
                        onValueSelected(item)
                        onDismiss()
                    })
                }
            }
        }
    }
}