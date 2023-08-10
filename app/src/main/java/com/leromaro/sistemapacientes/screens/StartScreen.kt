package com.leromaro.sistemapacientes.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leromaro.sistemapacientes.model.Codigo
import com.leromaro.sistemapacientes.model.Pacientes
import com.leromaro.sistemapacientes.navigation.AppScreens
import com.leromaro.sistemapacientes.PracticasViewModel
import kotlin.system.exitProcess

// faltaria
// mostrar lista de atenciones y poder eliminar
// borrar un paciente agregado
// guardar los pacientes y atenciones en un archivo temporal
// que se lea al abrir y
// que se elimine al salir de la app o al apretar un botón



@Composable
fun StartScreen(navController : NavController, practicasViewModel: PracticasViewModel) {
    Start(navController, practicasViewModel)
}

@Composable
fun Start(navController: NavController, practicasViewModel: PracticasViewModel) {
    val listPacientes by rememberSaveable { mutableStateOf(mutableListOf<Pacientes>()) }
    var expandedPacientes by rememberSaveable { mutableStateOf(false) }
    var expandedPracticas by rememberSaveable { mutableStateOf(false) }
    var currentValuePacientes by rememberSaveable { mutableStateOf("sin pacientes") }
    var currentValueCodigos by rememberSaveable { mutableStateOf(Codigo.CONSULTAS.tipo) }
    var pacienteValue by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

//    columna general
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround){
//        columna superior
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Fila caja texto pacientes
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier.size(250.dp, 40.dp),
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    ),
                    cursorBrush = SolidColor(Color.Blue),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
//                                .padding(horizontal = 64.dp) // margin left and right
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF6383C4),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF071E61),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .padding(all = 4.dp), // inner padding
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            innerTextField()
                        }
                    },
                    maxLines = 1,
                    value = pacienteValue,
                    onValueChange = { pacienteValue = it
//                                    if (it.length >= 3){
//                                        listPacientesFiltrado.clear()
//                                        for (item in listPacientes){
//                                            if(it in item.toString()){
//                                                listPacientesFiltrado.add(item)
//                                            }
//                                        }
//                                    }
                    },
                    )
                Log.d("MiLog","value $pacienteValue")
                Spacer(modifier = Modifier.width(20.dp))
//                icono add
                Icon(modifier = Modifier
                    .clickable {
                        if (pacienteValue.isNotEmpty() && !listPacientes.contains(
                                Pacientes(
                                    pacienteValue
                                )
                            )
                        ) {
                            listPacientes.add(Pacientes(pacienteValue))
                            currentValuePacientes = pacienteValue
                            showToast(context, "Paciente guardado\r\n\r${pacienteValue}")
                        } else {
                            showToast(context, "Ingrese un nuevo paciente")
                        }
                        for (i in 0 until listPacientes.size) {
                            Log.d("MiLog", "paciente" + listPacientes[i])
                        }
                        pacienteValue = ""
                    }
                    .size(25.dp),
                    imageVector = Icons.Default.Add, contentDescription = "agregar"
                )
                Spacer(modifier = Modifier.width(20.dp))
//                icono borrar
                Icon(modifier = Modifier
                    .clickable {
                        pacienteValue = ""
                    }
                    .size(25.dp),
                    imageVector = Icons.Default.Clear, contentDescription = "eliminar"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))//entre caja y spinner
            //Fila spinner pacientes
            Text(text = "Selección de paciente",
                fontSize = 15.sp)
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            expandedPacientes = !expandedPacientes
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(250.dp),
                        text = currentValuePacientes,
                        fontSize = 20.sp
                    )
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    DropdownMenu(expanded = expandedPacientes, onDismissRequest = {
                        expandedPacientes = false
                    }) {
                        listPacientes.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.paciente) },
                                onClick = {
                                    currentValuePacientes = it.paciente
                                    expandedPacientes = false
                                })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))//entre pinner y spinner
            //Fila spinner prácticas
            Text(text = "Selección de código",
                fontSize = 15.sp)
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            expandedPracticas = !expandedPracticas
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(250.dp),
                        text = currentValueCodigos,
                        fontSize = 20.sp
                    )
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    DropdownMenu(expanded = expandedPracticas, onDismissRequest = {
                        expandedPracticas = false
                    }) {
                        Codigo.values().forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.tipo) },
                                onClick = {
                                    currentValueCodigos = it.tipo
                                    expandedPracticas = false
                                })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))//entre spinner y boton guardar
            ShowButton("GUARDAR",
                onClick = { if(currentValuePacientes!="sin pacientes"){
                    practicasViewModel.listAtencion.add(Pair(currentValuePacientes,currentValueCodigos) )
                    showToast(context, "Atención guardada\r\n\r${currentValuePacientes} - $currentValueCodigos")
                }else{
                    showToast(context, "Debe ingresar al menos un paciente")
                }
                    },
                modifier = Modifier.width(150.dp)
            )
        }
//        columna inf
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ShowButton("RESUMEN",
                onClick = { if(practicasViewModel.listAtencion.size !=0) {
                    navController.navigate(route = AppScreens.ResultScreen.route)
                }else{
                    showToast(context,"Debe guardar al menos una atención")
                }
                     },
                modifier = Modifier.width(150.dp))
            Spacer(modifier = Modifier.height(30.dp))
            ShowButton("SALIR",
                modifier = Modifier.width(150.dp),
                onClick = { exitProcess(0) } )
//            onClick = { exitProcess(0) },
//                modifier = Modifier.width(150.dp))  {
//                Text(text = "SALIR")
//            }
           Banner()
        }
    }
}

fun showToast(contexto: Context, mensaje: String) {
    Toast.makeText(contexto,mensaje,Toast.LENGTH_SHORT).show()

}
//@Composable
//fun ShowButton(
//    text : String,
//    modifier: Modifier = Modifier,
//    onClick : () -> Unit = {}
//){
//    Button(
//        onClick =  onClick ,
//        modifier = modifier)  {
//        Text(text = text)
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun PreviewStart(){
//    val navController = rememberNavController()
//    var practicasViewModel = mutableListOf<Pair<String,String>>()
//    practicasViewModel.add(Pair("Victor", "Consulta"))
//    StartScreen(navController, practicasViewModel)
//}