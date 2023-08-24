package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.leromaro.sistemapacientes.ui.viewModel.PracticasViewModel

// borrar un paciente agregado
// guardar los pacientes y atenciones en un archivo temporal que se lea al abrir
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, viewModel: PracticasViewModel) {
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        //        columna superior
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//APPBAR
            AppBar(navController, viewModel)
// FILA CAJA TEXT
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(modifier = Modifier.width(250.dp),
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        singleLine = true,
                        maxLines = 1,
                        value = patientValue,
                        onValueChange = { patientValue = it },
                        label = { Text(stringResource(id = R.string.paciente)) })
//                    onValueChange = { pacienteValue = it
////                                    if (pacienteValue.length >= 3){
////                                        listPacientesFiltrado.clear()
////                                        for (item in listPacientes){
////                                            if(pacientesValue in item.toString()){
////                                                listPacientesFiltrado.add(item)
                    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
//ADD
                    ShowIcon(
                        Icons.Default.Add,
                        stringResource(id = R.string.agregar_paciente),
                        onIconClick = {
                            if (patientValue.isNotEmpty() && !patientList.contains(
                                    Pacientes(patientValue)
                                )
                            ) {
                                patientList.add(Pacientes(patientValue))
                                viewModel.currentValuePatients = patientValue
                                viewModel.showToast(
                                    context,
                                    "$message \r\n\r${patientValue}"
                                )
                            } else {
                                viewModel.showToast(context, errorNewPatient)
                            }
                            patientValue = ""
                        },
                        Color.Green)
                    Spacer(modifier = Modifier.width(20.dp))
//ERASE
                    ShowIcon(
                        Icons.Default.Clear,
                        stringResource(id = R.string.eliminar),
                        onIconClick = {
                            viewModel.showToast(context, erase)
                            patientValue = ""
                        },
                        Color.Yellow)
                }
                Spacer(modifier = Modifier.height(20.dp))//entre caja y spinner
//FILA SPINNERS Y BUTTON
                Row {
// COLUMN SPINNERS
                    Column {
                        Text(
                            text = stringResource(id = R.string.seleccion_paciente), fontSize = 15.sp
                        )
//SPINNER PATIENTS
//                        ShowSpinner(
//                            currentValue = currentValuePatients,
//                            expanded = expandedPatients,
//                            onValueSelected = { newValue ->
//                                viewModel.currentValuePatients = newValue
//                            },
//                            onDismiss = {
//                                expandedPatients = false
//                            },
//                            itemList = patientList.map { it.paciente }
//                        )
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedPatients = !expandedPatients
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.width(250.dp),
                                    text = currentValuePatients,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                                DropdownMenu(expanded = expandedPatients, onDismissRequest = {
                                    expandedPatients = false
                                }) {
                                    patientList.forEach {
                                        DropdownMenuItem(text = { Text(text = it.paciente) },
                                            onClick = {
                                                viewModel.currentValuePatients = it.paciente
                                                expandedPatients = false
                                            })
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))//entre pinner y spinner
                        Text(
                            text = stringResource(id = R.string.seleccion_codigo), fontSize = 15.sp
                        )
//SPINNER CODES
//                        ShowSpinner(
//                            currentValue = currentValueCodes,
//                            expanded = expandedPractics,
//                            onValueSelected = { newValue ->
//                                viewModel.currentValueCodes = newValue
//                            },
//                            onDismiss = {
//                                expandedPractics = false
//                            },
//                            itemList = Codigo.values().map { it.tipo }
//                        )
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedCodes = !expandedCodes
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.width(250.dp),
                                    text = currentValueCodes,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                                DropdownMenu(expanded = expandedCodes, onDismissRequest = {
                                    expandedCodes = false
                                }) {
                                    Codigo.values().forEach {
                                        DropdownMenuItem(
                                            text = { Text(text = it.tipo) },
                                            onClick = {
                                                viewModel.currentValueCodes = it.tipo
                                                expandedCodes = false
                                            })
                                    }
                                }
                            }
                        }
                    }
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
                                context,
                                "$saved \r\n\r${currentValuePatients} - $currentValueCodes"
                            )
                        } else {
                            viewModel.showToast(context, errorOnePatient)
                        } },
                        Color.Blue)
                    }
//                }
//ATTEND CARD
            LazyColumn {
                itemsIndexed(viewModel.listAttend){ indice, item->
                    AtencionCard(context, viewModel, indice, item)

                }
                }
            }
//ADS
            Banner()
        }
    }

