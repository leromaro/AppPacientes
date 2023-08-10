package com.leromaro.sistemapacientes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.PracticasViewModel
import kotlin.system.exitProcess

@Composable
fun ResultScreen(navController : NavController, practicasViewModel: PracticasViewModel) {
    val pacientesTotales = practicasViewModel.pacientesTotales
    val codigosTotales = practicasViewModel.codigosTotales
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pacientes totales = $pacientesTotales",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            for ((codigo, cantidad) in codigosTotales) {
                Text(
                    text = "$codigo = $cantidad",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowButton("MODIFICAR",
                modifier = Modifier.width(150.dp),
                onClick = { navController.popBackStack() } )
//            Button(
//                onClick = {
//                    navController.popBackStack()},
//                modifier = Modifier.width(150.dp))  {
//                Text(text = "VOLVER")
//            }
            Spacer(modifier = Modifier.height(50.dp))
            ShowButton("SALIR",
                modifier = Modifier.width(150.dp),
                onClick = { exitProcess(0) } )
//            Button(
//                onClick = { exitProcess(0) },
//                modifier = Modifier.width(150.dp))  {
//                Text(text = "SALIR")
//            }
            Banner()
        }
    }
}
