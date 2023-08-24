package com.leromaro.sistemapacientes.ui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leromaro.sistemapacientes.model.Codigo
import com.leromaro.sistemapacientes.model.Pacientes
import java.io.File

class AttendViewModel : ViewModel() {
    val listAttend = mutableStateListOf<Pair<String, String>>()
    val listPatients = mutableStateListOf<Pacientes>()
    var currentValuePatients by  mutableStateOf("sin pacientes")
    var currentValueCodes by   mutableStateOf(Codigo.CONSULTAS.tipo)
    val totalPatients: Int
        get() = listAttend.map { it.first }.distinct().size

    val totalCodes: Map<String, Int>
        get() = listAttend.groupBy { it.second }.mapValues { it.value.size }

    fun showToast(contexto: Context, mensaje: String) {
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show()
    }
    fun lazyColumnDeleteItem(context: Context) {
        val file = File(context.filesDir, "contactos.txt")
        file.delete()
        for (item in listAttend) {
            file.appendText("${item.first}\n${item.second}\n")
        }
    }
    fun clearDataFiles(context: Context) {
        currentValuePatients = "sin pacientes"
        currentValueCodes = Codigo.CONSULTAS.tipo
        context.deleteFile("pacientes.dat")
        context.deleteFile("atencion.dat")
    }

    fun resetData() {
        listAttend.clear()
        listPatients.clear()
    }

    fun loadSavedData(context: Context) {
        val patientsFile = File(context.filesDir, "pacientes.dat")
        val attendFile = File(context.filesDir, "atencion.dat")

        if (patientsFile.exists() && attendFile.exists()) {
            val pactientsData = patientsFile.readText()
            val attendData = attendFile.readText()

            val patientsList: List<Pacientes> = parsePatientsData(pactientsData)
            val attendList: List<Pair<String, String>> = parseAttendData(attendData)

            // Agrega los datos a las listas correspondientes en el ViewModel
            listPatients.addAll(patientsList)
            listAttend.addAll(attendList)
        }
    }

    private fun parsePatientsData(data: String): List<Pacientes> {
        val lines = data.lines()
        val patientsList = mutableListOf<Pacientes>()
        for (line in lines) {
            val patient = Pacientes(line)
            patientsList.add(patient)
        }
        return patientsList
    }

    private fun parseAttendData(data: String): List<Pair<String, String>> {
        val lines = data.lines()
        val attendList = mutableListOf<Pair<String, String>>()
        for (line in lines) {
            val parts = line.split(',')
            if (parts.size == 2) {
                val patient = parts[0].trim()
                val code = parts[1].trim()
                val attendPair = patient to code
                attendList.add(attendPair)
            }
        }
        return attendList
    }
}
