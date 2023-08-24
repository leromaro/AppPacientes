package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.R

@Composable
fun DialogScreen(navController : NavController) {
    AlertDialog(onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "OK")}
            },
        title= {Text(text = "Ayuda")},
        text ={Text(
            "Ingrese los pacientes de manera que el nombre no se repita (Pérez I y Pérez S)," +
                " presione el botón + verde para agregar el paciente.\r\n\r" +
                "El programa diferencia entre mayúsculas y minúsculas: Pérez y pérez contarán como dos pacientes, perez contará como otro. \r\n\r" +
                "Presione el botón x amarillo para limpiar la caja de texto.\r\n\r" +
                "Para guardar una atención, elija el par paciente - código de cada atención y presione el botón + azul.\r\n\r"+
                "Si lo desea puede borrar las atenciones agregadas preionando el botón x rojo.\r\n\r"+
                "Al terminar de llenar todas las atenciones del mes, abra el menú en la esquina superior derecha y elija ${stringResource(
                    id = R.string.resumen_mensual
                )} \r\n\r" +
                "Desde el menú tiene la opción de ${stringResource(id = R.string.borrar)} todo el contenido cargado o ${stringResource(
                    id = R.string.salir
                )} de la app que también borrará todo el contenido")}
    )
}