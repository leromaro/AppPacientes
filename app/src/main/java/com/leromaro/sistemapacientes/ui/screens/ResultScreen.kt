package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.R
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel
import kotlin.system.exitProcess

@Composable
fun ResultScreen(navController : NavController, viewModel: AttendViewModel) {
    val totalPatients = viewModel.totalPatients
    val totalCodes = viewModel.totalCodes
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
//COLUMN SUP
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${stringResource(id = R.string.pacientes_totales)} = $totalPatients",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            for ((code, value) in totalCodes) {
                Text(
                    text = "$code = $value",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        ShowButton(
            stringResource(id = R.string.modificar),
            modifier = Modifier.width(150.dp),
            onClick = { navController.popBackStack() } )
        Spacer(modifier = Modifier.height(50.dp))
        ShowButton(
            stringResource(id = R.string.salir),
            modifier = Modifier.width(150.dp),
            onClick = { exitProcess(0) } )
        Banner()
    }
}
