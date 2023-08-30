package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.R
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun ResultScreen(navController: NavController, viewModel: AttendViewModel) {
    val totalPatients = viewModel.totalPatients
    val totalUnitCodes = viewModel.totalUnitCodes
    val totalCodes = viewModel.totalCodes
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//COLUMN SUP
        Card(
            modifier = Modifier.padding(0.dp)
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = R.string.pacientes_totales)} = $totalPatients",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${stringResource(id = R.string.atenciones_totales)} = $totalCodes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                for ((code, value) in totalUnitCodes) {
                    Text(
                        text = "$code = $value", fontSize = 16.sp, fontWeight = FontWeight.Normal
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowButton(stringResource(id = R.string.modificar),
                modifier = Modifier.width(150.dp),
                onClick = { navController.popBackStack() })
            Spacer(modifier = Modifier.height(16.dp))
            ShowButton(stringResource(id = R.string.salir),
                modifier = Modifier.width(150.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.clearData(context)
                        delay(1000)
                    }
                    exitProcess(0)
                })
            Banner()
        }
    }
}
