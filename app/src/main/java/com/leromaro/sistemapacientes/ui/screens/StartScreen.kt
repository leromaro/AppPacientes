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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import com.leromaro.sistemapacientes.R
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.leromaro.sistemapacientes.model.Codes
import com.leromaro.sistemapacientes.model.Patients
import com.leromaro.sistemapacientes.navigation.AppScreens
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.screens.components.ShowSpinner
import com.leromaro.sistemapacientes.ui.viewModel.AttendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, viewModel: AttendViewModel) {
    val context = LocalContext.current

    val counterMaxLength = 20

    val valuePatients : String by viewModel.valuePatients.observeAsState(initial = "")
    val valuePatientSpinner : String by viewModel.valuePatientSpinner.observeAsState(initial = valuePatients.ifEmpty { stringResource(
        id = R.string.sin_pacientes
    ) }) // if empty -> lambda else valuePatients
    val valueCodesSpinner : String by viewModel.valueCodesSpinner.observeAsState(initial = Codes.CONSULTAS.tipo)
    val patientList = viewModel.listPatients

    var expandedPatients by rememberSaveable { mutableStateOf(false) }
    var expandedCodes by rememberSaveable { mutableStateOf(false) }

    val message = stringResource(id = R.string.guardado)
    val noPatients = stringResource(id = R.string.sin_pacientes)
    val errorOnePatient = stringResource(id = R.string.error_un_paciente)
    val saved = stringResource(id = R.string.guardado)
    val erase = stringResource(id = R.string.toast_borrado)
    val errorNewPatient = stringResource(id = R.string.error_nuevo_paciente)
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
            AppBar(navController,
                viewModel,
                Icons.Default.Info,
                "info",
                {navController.navigate(AppScreens.DialogScreen.route)},
                MaterialTheme.colorScheme.onPrimary,
                true)
// FILA CAJA TEXT
            Card(
                modifier = Modifier
                    .padding(16.dp, 1.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                    ){
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
                        shape = RoundedCornerShape(size = 15.dp),
                        value = valuePatients,
                        onValueChange = {
                            if (counterMaxLength > it.length ){
                                viewModel.onTextChange(it.uppercase()) }
                            },
                        label = { Text(stringResource(id = R.string.paciente)) },
                        trailingIcon = {
                            if (valuePatients.isNotBlank())
//ERASE BUTTON
                                ShowButton(
                                    text = "",
                                    Icons.Default.Clear,
                                    Color.Yellow,
                                    modifier = Modifier.width(40.dp),
                                    onClick = {
                                        viewModel.showToast(context, erase)
                                        viewModel.textErase()
                                    }
                                )
                        }

                    )
                    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                    Column {
//SAVE BUTTON
                        ShowButton(
                            text = "",
                            Icons.Default.Add,
                            Color.Green,
                            modifier = Modifier.width(40.dp),
                            onClick =  {
                                if (
                                    valuePatients.isNotEmpty() && !patientList.contains(Patients(valuePatients)                         )
                                ) {
                                    viewModel.addPatient(valuePatients)
                                    viewModel.saveDataPatient(context, valuePatients)
                                    viewModel.spinnerPatientLoad(valuePatients)
                                    viewModel.showToast(
                                        context, "$message \r\n\r${valuePatients}"
                                    )
                                } else {
                                    viewModel.showToast(context, errorNewPatient)
                                }
                                viewModel.textErase()
                            }
                        )
                    }
                }
            }
//FILA SPINNERS Y BUTTON
            Card(
                modifier = Modifier
               .padding(16.dp, 1.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
            ){
                Row(modifier = Modifier
                    .padding(16.dp)) {
// COLUMN SPINNERS
                    Column(modifier = Modifier
                        .weight(0.8f)) {
//SPINNER PATIENTS
                        ShowSpinner(
                            stringResource(id = R.string.seleccion_paciente),
                            valuePatientSpinner,
                            expandedPatients,
                            onValueSelected = {
                              viewModel.onPatientSelected(it)
                            },
                            onDismiss = { expandedPatients = false },
                            onSusses = { expandedPatients = true },
                            patientList.map { it.patient })
                        Spacer(modifier = Modifier.height(20.dp))//entre spinner y spinner
//SPINNER CODES
                        ShowSpinner(
                            stringResource(id = R.string.seleccion_codigo),
                            valueCodesSpinner,
                            expandedCodes,
                            onValueSelected = {
                                viewModel.onCodesSelected(it)
                            },
                            onDismiss = {
                                expandedCodes = false
                            },
                            onSusses = {
                                expandedCodes = true
                            },
                            itemList = Codes.values().map { it.tipo })
                    }
// SAVE BUTTON
                    Column(modifier = Modifier.
                    height(130.dp),
                        verticalArrangement = Arrangement.Center) {
                        ShowButton(
                            text = "",
                            Icons.Default.Add,
                            Color.Blue,
                            modifier = Modifier.width(40.dp),
                            onClick = {
                                if (valuePatientSpinner != noPatients) {
                                    viewModel.saveDataAttend(context, valuePatientSpinner, valueCodesSpinner  )
                                    viewModel.addAttend(valuePatientSpinner,valueCodesSpinner)
                                    viewModel.showToast(
                                        context, "$saved \r\n\r${valuePatientSpinner} - $valueCodesSpinner"
                                    )
                                } else {
                                    viewModel.showToast(context, errorOnePatient)
                                }
                            }
                        )
                    }
                }
            }
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

