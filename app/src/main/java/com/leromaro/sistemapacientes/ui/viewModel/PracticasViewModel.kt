package com.leromaro.sistemapacientes.ui.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leromaro.sistemapacientes.model.Codigo
import com.leromaro.sistemapacientes.model.Pacientes
import java.io.File

class PracticasViewModel : ViewModel() {
    val listaAtencion = mutableStateListOf<Pair<String, String>>()
    val listaPacientes = mutableStateListOf<Pacientes>()
    var currentValuePacientes by  mutableStateOf("sin pacientes")
    var currentValueCodigos by   mutableStateOf(Codigo.CONSULTAS.tipo)
    val pacientesTotales: Int
        get() = listaAtencion.map { it.first }.distinct().size

    val codigosTotales: Map<String, Int>
        get() = listaAtencion.groupBy { it.second }.mapValues { it.value.size }

    fun lazyColumnDeleteItem(context: Context) {
        val file = File(context.filesDir, "contactos.txt")
        file.delete()
        for (item in listaAtencion) {
            file.appendText("${item.first}\n${item.second}\n")
        }
    }
    fun clearDataFiles(context: Context) {
        currentValuePacientes = "sin pacientes"
        currentValueCodigos = Codigo.CONSULTAS.tipo
        context.deleteFile("pacientes.dat")
        context.deleteFile("atencion.dat")
    }

    fun resetData() {
        listaAtencion.clear()
        listaPacientes.clear()
    }

    fun loadSavedData(context: Context) {
        val pacientesFile = File(context.filesDir, "pacientes.dat")
        val atencionFile = File(context.filesDir, "atencion.dat")

        if (pacientesFile.exists() && atencionFile.exists()) {
            val pacientesData = pacientesFile.readText()
            val atencionData = atencionFile.readText()

            val pacientesList: List<Pacientes> = parsePacientesData(pacientesData)
            val atencionList: List<Pair<String, String>> = parseAtencionData(atencionData)

            // Agrega los datos a las listas correspondientes en el ViewModel
            listaPacientes.addAll(pacientesList)
            listaAtencion.addAll(atencionList)
        }
    }

    private fun parsePacientesData(data: String): List<Pacientes> {
        val lines = data.lines()
        val pacientesList = mutableListOf<Pacientes>()
        for (line in lines) {
            val paciente = Pacientes(line)
            pacientesList.add(paciente)
        }
        return pacientesList
    }

    private fun parseAtencionData(data: String): List<Pair<String, String>> {
        val lines = data.lines()
        val atencionList = mutableListOf<Pair<String, String>>()
        for (line in lines) {
            val parts = line.split(',')
            if (parts.size == 2) {
                val paciente = parts[0].trim()
                val codigo = parts[1].trim()
                val atencionPair = paciente to codigo
                atencionList.add(atencionPair)
            }
        }
        return atencionList
    }
}
