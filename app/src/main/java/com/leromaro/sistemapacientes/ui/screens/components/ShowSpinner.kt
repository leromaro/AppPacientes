package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leromaro.sistemapacientes.model.Pacientes

@Composable
fun ShowSpinner(
    text : String,
    onClick: () -> Unit,
    value : String,
    icon : ImageVector,
    expanded : Boolean,
    onExpanded : () -> Unit,
    listaPacientes : List<Pacientes>,
    textoItem : Pacientes,
    onDropdown :() -> Unit
    ) {
    Text(text = text,
        fontSize = 15.sp)
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick
                           },
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(250.dp),
                text = value,
                fontSize = 20.sp
            )
            Icon(imageVector = icon,
                contentDescription = null)
            DropdownMenu(expanded = expanded,
                onDismissRequest =
                onExpanded
            ) {
                listaPacientes.forEach {
                    DropdownMenuItem(
                        text =  {Text(text = textoItem.paciente)} ,
                        onClick =
                            onDropdown
                        )
                }
            }
        }
    }
}