package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import com.leromaro.sistemapacientes.R
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.model.Codigo
import com.leromaro.sistemapacientes.model.Pacientes
import com.leromaro.sistemapacientes.ui.screens.components.ShowIcon
import com.leromaro.sistemapacientes.ui.screens.components.ShowSpinner
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

// save the patients y attend in a temporal file, read an open
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, viewModel: AttendViewModel) {
    val message = stringResource(id = R.string.guardado)
    val noPatients = stringResource(id = R.string.sin_pacientes)
    val errorOnePatient = stringResource(id = R.string.error_un_paciente)
    val saved = stringResource(id = R.string.guardado)
    val erase = stringResource(id = R.string.toast_borrado)
    val errorNewPatient = stringResource(id = R.string.error_nuevo_paciente)
    val patientList = viewModel.listPatients
    var expandedPatients by rememberSaveable { mutableStateOf(false) }
    var expandedCodes by rememberSaveable { mutableStateOf(false) }
    val currentValuePatients = viewModel.currentValuePatients
    val currentValueCodes = viewModel.currentValueCodes
    var patientValue by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
//COLUMN GENERAL
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//COLUMN GENERAL
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//APPBAR
            AppBar(navController, viewModel)
// FILA CAJA TEXT
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .width(250.dp)
                        .weight(0.5f),
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    ),
                    singleLine = true,
                    maxLines = 1,
                    value = patientValue,
                    onValueChange = { patientValue = it.uppercase() },
                    label = { Text(stringResource(id = R.string.paciente)) })
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Column {
                    //ADD
                    ShowIcon(
                        Icons.Default.Add,
                        stringResource(id = R.string.agregar_paciente),
                        onIconClick = {
                            if (
                                patientValue.isNotEmpty() && !patientList.contains(Pacientes(patientValue)                         )
                            ) {
                                viewModel.addPatient(patientValue)
                                viewModel.showToast(
                                    context, "$message \r\n\r${patientValue}"
                                )
                            } else {
                                viewModel.showToast(context, errorNewPatient)
                            }
                            patientValue = ""
                        },
                        Color.Green
                    )
                    Spacer(modifier = Modifier.height(ButtonDefaults.IconSpacing))
//ERASE
                    ShowIcon(
                        Icons.Default.Clear, stringResource(id = R.string.eliminar), onIconClick = {
                            viewModel.showToast(context, erase)
                            patientValue = ""
                        }, Color.Yellow
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))//entre caja y spinner
//FILA SPINNERS Y BUTTON
            Row(modifier = Modifier
                .padding(16.dp)) {
// COLUMN SPINNERS
                Column(modifier = Modifier
                    .weight(0.8f)) {
//SPINNER PATIENTS
                    ShowSpinner(
                        stringResource(id = R.string.seleccion_paciente),
                        currentValuePatients,
                        expandedPatients,
                        onValueSelected = { newValue ->
                            viewModel.currentValuePatients = newValue
                        },
                        onDismiss = { expandedPatients = false },
                        onSusses = { expandedPatients = true },
                        patientList.map { it.paciente })
                    Spacer(modifier = Modifier.height(20.dp))//entre spinner y spinner
//SPINNER CODES
                    ShowSpinner(
                        stringResource(id = R.string.seleccion_codigo),
                        currentValueCodes,
                        expandedCodes,
                        onValueSelected = { newValue ->
                            viewModel.currentValueCodes = newValue
                        },
                        onDismiss = {
                            expandedCodes = false
                        },
                        onSusses = {
                            expandedCodes = true
                        },
                        itemList = Codigo.values().map { it.tipo })
                }
// SAVE BUTTON
                Column(modifier = Modifier.
                    height(130.dp),
                    verticalArrangement = Arrangement.Center) {
                    ShowIcon(
                        Icons.Default.Add,
                        description = stringResource(id = R.string.agregar_atencion),
                        onIconClick = {
                            if (currentValuePatients != noPatients) {
                                viewModel.listAttend.add(
                                    Pair(
                                        currentValuePatients, currentValueCodes
                                    )
                                )
                                viewModel.showToast(
                                    context, "$saved \r\n\r${currentValuePatients} - $currentValueCodes"
                                )
                            } else {
                                viewModel.showToast(context, errorOnePatient)
                            }
                        },
                        Color.Blue
                    )
                }
            }
//                }
//ATTEND CARD
            LazyColumn {
                itemsIndexed(viewModel.listAttend) { index, item ->
                    AttendCard(context, viewModel, index, item)
                }
            }
        }
//ADS
        Banner()
    }
}

