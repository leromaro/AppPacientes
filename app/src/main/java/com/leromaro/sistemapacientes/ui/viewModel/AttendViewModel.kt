package com.leromaro.sistemapacientes.ui.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leromaro.sistemapacientes.model.Codes
import com.leromaro.sistemapacientes.model.Patients
import java.io.File
import java.io.FileWriter
import java.io.IOException
//eliminar menu buscar fila y cuando empieza con un os pacientes que no cargue el nombre en el text
class AttendViewModel : ViewModel() {
    private val attendName = "attend.dat"
    private val patientName = "patient.dat"

    private val _valuePatients = MutableLiveData<String>()
    val valuePatients : LiveData<String> = _valuePatients

    private val _valuePatientSpinner = MutableLiveData<String>()
    val valuePatientSpinner: LiveData<String> = _valuePatientSpinner

    private val _valueCodesSpinner = MutableLiveData<String>()
    val valueCodesSpinner: LiveData<String> =_valueCodesSpinner

//    private val _listAttend = MutableLiveData<MutableList<Pair<String, String>>>()
//    val listAttend: LiveData<MutableList<Pair<String, String>>> = _listAttend
//
//    private val _listPatients = MutableLiveData<MutableList<Patients>>()
//    val listPatients: LiveData<MutableList<Patients>> = _listPatients


    val listAttend = mutableStateListOf<Pair<String, String>>()
    val listPatients = mutableStateListOf<Patients>()
    val totalPatients: Int
        get() = listAttend.map { it.first }.distinct().size

    val totalCodes: Map<String, Int>
        get() = listAttend.groupBy { it.second }.mapValues { it.value.size }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun lazyColumnDeleteItem(context: Context, index : Int) {
        listAttend.removeAt(index)
        val file = File(context.filesDir, attendName)
        file.delete()
        for (item in listAttend) {
            file.appendText("${item.first}\n${item.second}\n")
        }
    }
    fun addPatient(patient: String) {
        listPatients.add(Patients(patient.uppercase()))
        Log.d("MyLog", "agregar paciente $patient size: ${listPatients.size}")
//        _valuePatients.value = patient
    }
    fun saveDataPatient(context: Context, patient: String) {
        val path = context.filesDir
        try {
            val patientsFile = File(path, patientName)
            val attendFile = File(path, attendName)
            Log.d("MyLog", "file $path - $patientName")
            if (!patientsFile.exists()) {
                patientsFile.createNewFile()
                Log.d("MyLog", "creando archivos en paciente")
            }else{
                Log.d("MyLog", "archivo existe")
            }
            attendFile.createNewFile()
            FileWriter(File(path, patientName), true).use {
                it.appendLine()
                it.write(patient)
            }
            File(path, patientName).bufferedReader().use { value ->
                var line: String?
                while (value.readLine().also { line = it } != null) {
                    Log.d("MyLog","line: $line")
                }
            }
        } catch (e: IOException) {
            Log.e("MyLog", "error: $e")
        }
    }

    fun saveDataAttend(context: Context, patient: String, code: String) {
        val path = context.filesDir
        try {
            val attendFile = File(path, attendName)
            if (!attendFile.exists()) {
                attendFile.createNewFile()
            }
            val text = "$patient, $code"
            FileWriter(File(path, attendName), true).use {
                it.appendLine()
                it.write(text)
            }
            File(path, attendName).bufferedReader().use { value ->
                var line: String?
                while (value.readLine().also { line = it } != null) {
                    Log.d("MyLog","line: $line")
                }
            }
        } catch (e: IOException) {
            Log.e("MyLog", "error: $e")
        }
    }
    fun clearData(context: Context) {
        _valuePatients.value = ""
        _valuePatientSpinner.value = "SIN PACIENTES"
        _valueCodesSpinner.value = Codes.CONSULTAS.tipo
        listAttend.clear()
        listPatients.clear()
        context.deleteFile(patientName)
        context.deleteFile(attendName)
    }

//    podría ir en init{}
    fun loadSavedData(context: Context) {
        val patientsFile = File(context.filesDir, patientName)
        val attendFile = File(context.filesDir, attendName)
        attendFile.createNewFile()
        if (patientsFile.exists()
            ){
            val patientsList: MutableList<Patients> = mutableListOf()
            val attendList: MutableList<Pair<String, String>> = mutableListOf()
//            val attendList: MutableList<Attends> = mutableListOf()
            try {
                patientsFile.bufferedReader().use{ value ->
                    var line: String?
                    while (value.readLine().also { line = it } != null) {
                        if (line?.isNotEmpty() == true){
                                patientsList.addAll(parsePatientsData(line))
                            }
                    }
                    Log.d("MyLog", "patientListTemp luego de grabar size ${patientsList.size}")
                }
                attendFile.bufferedReader().use{ value ->
                    var line: String?
                    while (value.readLine().also { line = it } != null) {
                        attendList.addAll(parseAttendData(line))
                    }
                    Log.d("MyLog", "patientListTemp luego de grabar size ${patientsList.size}")
                }

                listPatients.addAll(patientsList)
                Log.d("MyLog"," listPatients size: ${listPatients.size}")
                listAttend.addAll(attendList)
                Log.d("MyLog"," listAttend size: ${listAttend.size}")
                _valuePatients.value = listPatients[0].patient
            } catch (e: IOException) {
                Log.e("MyLog", "Error: $e")
            }
        }else{
            Log.d("MyLog", "archivos no existen")
        }
    }

    private fun parsePatientsData(data: String?): List<Patients> {
        val lines = data?.lines()
        val patientsList = mutableListOf<Patients>()
        if (lines != null) {
            for (line in lines) {
                val patient = Patients(line)
                Log.d("MyLog"," parse patient patient: $patient")
                patientsList.add(patient)
            }
        }
        for (patientsLines in patientsList){
            Log.d("MyLog"," listPatients lista: $patientsLines")
        }
        return patientsList
    }

    private fun parseAttendData(data: String?): List<Pair<String, String>> {
        val lines = data?.lines()
        val attendList = mutableListOf<Pair<String, String>>()
        if (lines != null) {
            for (line in lines) {
                val parts = line.split(',')
                if (parts.size == 2) {
                    val patient = parts[0].trim()
                    val code = parts[1].trim()
                    val attendPair = patient to code
                    Log.d("MyFileLoad"," parse attend attendPair: $attendPair")
                    attendList.add(attendPair)
                }
            }
        }
        for (attendLines in attendList){
            Log.d("MyFileLoad"," listAttend lista: $attendLines")
        }
        return attendList
    }
    fun onTextChange(patient: String) {
        _valuePatients.value = patient
    }

    fun onPatientSelected(selected: String) {
        _valuePatientSpinner.value = selected
    }

    fun textErase() {
        _valuePatients.value = ""
    }

    fun spinnerPatientLoad(valuePatients: String) {
        _valuePatientSpinner.value = valuePatients
    }

    fun onCodesSelected(code: String) {
        _valueCodesSpinner.value = code
    }
    fun openFileWithExternalEditor(context: Context) {
        val file = File(context.filesDir, patientName)
        val uri = FileProvider.getUriForFile(context, "com.leromaro.sistemapacientes.fileprovider", file)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "text/plain")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}
