package com.leromaro.sistemapacientes.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.model.Codigo
import com.leromaro.sistemapacientes.model.Pacientes
import com.leromaro.sistemapacientes.ui.screens.components.ShowButton
import com.leromaro.sistemapacientes.ui.viewModel.PracticasViewModel

// borrar un paciente agregado
// guardar los pacientes y atenciones en un archivo temporal que se lea al abrir
//agregar iconos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, viewModel: PracticasViewModel) {
    val listaPacientes = viewModel.listaPacientes
    var expandedPacientes by rememberSaveable { mutableStateOf(false) }
    var expandedPracticas by rememberSaveable { mutableStateOf(false) }
    val currentValuePacientes = viewModel.currentValuePacientes
    val currentValueCodigos = viewModel.currentValueCodigos
    var pacienteValue by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
//COLUMNA GENERAL
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
// FILA CAJA TEXTO
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(modifier = Modifier.width(250.dp),
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        singleLine = true,
                        maxLines = 1,
                        value = pacienteValue,
                        onValueChange = { pacienteValue = it },
                        label = { Text("Nombre del paciente") })
//                    onValueChange = { pacienteValue = it
////                                    if (pacienteValue.length >= 3){
////                                        listPacientesFiltrado.clear()
////                                        for (item in listPacientes){
////                                            if(pacientesValue in item.toString()){
////                                                listPacientesFiltrado.add(item)
                    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
//ADD
                    Icon(modifier = Modifier
                        .clickable {
                            if (pacienteValue.isNotEmpty() && !listaPacientes.contains(
                                    Pacientes(pacienteValue)
                                )
                            ) {
                                listaPacientes.add(Pacientes(pacienteValue))
                                viewModel.currentValuePacientes = pacienteValue
                                showToast(context, "Paciente guardado\r\n\r${pacienteValue}")
                            } else {
                                showToast(context, "Ingrese un nuevo paciente")
                            }
                            for (i in 0 until listaPacientes.size) {
                                Log.d("MiLog", "paciente" + listaPacientes[i])
                            }
                            pacienteValue = ""
                        }
                        .size(25.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "agregar")
                    Spacer(modifier = Modifier.width(20.dp))
//BORRAR
                    Icon(modifier = Modifier
                        .clickable {
                            pacienteValue = ""
                        }
                        .size(25.dp),
                        imageVector = Icons.Default.Clear,
                        contentDescription = "eliminar")
                }
                Spacer(modifier = Modifier.height(20.dp))//entre caja y spinner
//FILA SPINNERS Y BOTÓN
                Row {
// COLUMNA SPINNERS
                    Column {
                        Text(
                            text = "Selección de paciente", fontSize = 15.sp
                        )
//SPINNER PACIENTES
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedPacientes = !expandedPacientes
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.width(250.dp),
                                    text = currentValuePacientes,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                                DropdownMenu(expanded = expandedPacientes, onDismissRequest = {
                                    expandedPacientes = false
                                }) {
                                    listaPacientes.forEach {
                                        DropdownMenuItem(text = { Text(text = it.paciente) },
                                            onClick = {
                                                viewModel.currentValuePacientes = it.paciente
                                                expandedPacientes = false
                                            })
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))//entre pinner y spinner
                        Text(
                            text = "Selección de código", fontSize = 15.sp
                        )
//SPINNER CODIGOS
                        Row(
                            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedPracticas = !expandedPracticas
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.width(250.dp),
                                    text = currentValueCodigos,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                                DropdownMenu(expanded = expandedPracticas, onDismissRequest = {
                                    expandedPracticas = false
                                }) {
                                    Codigo.values().forEach {
                                        DropdownMenuItem(
                                            text = { Text(text = it.tipo) },
                                            onClick = {
                                                viewModel.currentValueCodigos = it.tipo
                                                expandedPracticas = false
                                            })
                                    }
                                }
                            }
                        }
                    }
//                    Column(
//                        verticalArrangement = Arrangement.Center
//                        ) {
                        ShowButton(
                            "+", onClick = {
                                if (currentValuePacientes != "sin pacientes") {
                                    viewModel.listaAtencion.add(
                                        Pair(
                                            currentValuePacientes, currentValueCodigos
                                        )
                                    )
                                    showToast(
                                        context,
                                        "Atención guardada\r\n\r${currentValuePacientes} - $currentValueCodigos"
                                    )
                                } else {
                                    showToast(context, "Debe ingresar al menos un paciente")
                                }
                            },
                            modifier = Modifier.width(40.dp)
                        )
                    }
//                }
//ATENCIONCARD
            LazyColumn {
                itemsIndexed(viewModel.listaAtencion){indice, item->
                    AtencionCard(context, viewModel, indice, item)

                }
                }
            }
//ANUNCIOS
            Banner()
        }
    }

fun showToast(contexto: Context, mensaje: String) {
    Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show()
}
