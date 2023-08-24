package com.leromaro.sistemapacientes.ui.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DialogScreen(navController : NavController) {
    AlertDialog(onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "OK")}
            },
        title= {Text(text = "Ayuda")},
        text ={Text("Ingrese los pacientes de manera que el nombre no se repita (Pérez I y Pérez S)," +
                " presione el ícono + para agregar el paciente.\n" +
                "\nPresione el ícono x para limpiar la caja de texto.\r\n\r" +
                "Para guardar una atención, elija el par paciente - código de cada atención y presione el botón + azul.\r\n\r"+
                "Al terminar de llenar todas las atenciones del mes, abra el menú en la esquina superior derecha y elija Resumen")}
    )
}