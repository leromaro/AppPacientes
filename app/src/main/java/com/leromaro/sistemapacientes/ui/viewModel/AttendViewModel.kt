package com.leromaro.sistemapacientes.ui.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leromaro.sistemapacientes.model.Codes
import com.leromaro.sistemapacientes.model.Patients
import java.io.File
import java.io.IOException

class AttendViewModel : ViewModel() {

//    private val _uiState = MutableStateFlow(StartScreenState())
//    val startScreenState: StateFlow<StartScreenState> = _uiState.asStateFlow()

    val listAttend = mutableStateListOf<Pair<String, String>>()
    val listPatients = mutableStateListOf<Patients>()
    var currentValuePatients by mutableStateOf("SIN PACIENTES")
    var currentValueCodes by mutableStateOf(Codes.CONSULTAS.tipo)
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

    fun clearData(context: Context) {
        currentValuePatients = "SIN PACIENTES"
        currentValueCodes = Codes.CONSULTAS.tipo
        listAttend.clear()
        listPatients.clear()
        context.deleteFile("patient.dat")
        context.deleteFile("attend.dat")
    }

    fun saveDataAttend(context: Context, patient: String, code: String) {
        val path = context.filesDir
        try {
            val attendName = "attend.dat"
            val attendFile = File(path, attendName)
            if (!attendFile.exists()) {
                attendFile.createNewFile()
            }
            val text = "$patient, $code"
            File(path, attendName).bufferedWriter().use { out ->
                out.append(text)
//                out.appendLine()
            }
        } catch (e: IOException) {
            Log.e("MyLog", "error: $e")
        }
    }

    fun saveDataPatient(context: Context, patient: String) {
        val path = context.filesDir
        try {
            val patientName = "patient.dat"
            val patientsFile = File(path, patientName)
            Log.d("MyLog", "file $path - $patientName")
            if (!patientsFile.exists()) {
                patientsFile.createNewFile()
                Log.d("MyLog", "creando archivo")
            }
            File(path, patientName).bufferedWriter().use { out ->
                out.write(patient)
//                out.appendLine()
            }
        } catch (e: IOException) {
            Log.e("MyLog", "error: $e")
        }
    }
    fun addPatient(patient: String) {
        listPatients.add(Patients(patient.uppercase()))
        currentValuePatients = patient
    }
    fun loadSavedData(context: Context) {
        val patientsFile = File(context.filesDir, "patient.dat")
        val attendFile = File(context.filesDir, "attend.dat")
        if (patientsFile.exists() && attendFile.exists()
            ){
            val patientsList: MutableList<Patients> = mutableListOf()
            val attendList: MutableList<Pair<String, String>> = mutableListOf()
            try {
                patientsFile.bufferedReader().use { patientsReader ->
                    val patientsData = patientsReader.readText()
                    patientsList.addAll(parsePatientsData(patientsData))
                }
                attendFile.bufferedReader().use { attendReader ->
                    val attendData = attendReader.readText()
                    attendList.addAll(parseAttendData(attendData))
                }
                listPatients.addAll(patientsList)
                listAttend.addAll(attendList)
                currentValuePatients = listPatients[0].patient
                Log.d("MyLog", "Number of Patients: ${listPatients.size}")
            } catch (e: IOException) {
                Log.e("MyLog", "Error: $e")
            }
        }else{
            Log.d("MyLog", "archivos no existen")
        }
    }

    private fun parsePatientsData(data: String): List<Patients> {
        val lines = data.lines()
        val patientsList = mutableListOf<Patients>()
        for (line in lines) {
            val patient = Patients(line)
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
