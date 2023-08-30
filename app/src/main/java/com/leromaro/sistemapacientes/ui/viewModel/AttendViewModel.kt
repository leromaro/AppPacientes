package com.leromaro.sistemapacientes.ui.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leromaro.sistemapacientes.model.Codes
import com.leromaro.sistemapacientes.model.Patients
import java.io.File
import java.io.FileWriter
import java.io.IOException

class AttendViewModel : ViewModel() {
    private val attendName = "attend.dat"
    private val patientName = "patient.dat"

    private val _resume = MutableLiveData<Boolean>()
    val resume : LiveData<Boolean> = _resume

    private val _valuePatients = MutableLiveData<String>()
    val valuePatients: LiveData<String> = _valuePatients

    private val _valuePatientSpinner = MutableLiveData<String>()
    val valuePatientSpinner: LiveData<String> = _valuePatientSpinner

    private val _valueCodesSpinner = MutableLiveData<String>()
    val valueCodesSpinner: LiveData<String> = _valueCodesSpinner

//    private val _listAttend = MutableLiveData<MutableList<Pair<String, String>>>()
//    val listAttend: LiveData<MutableList<Pair<String, String>>> = _listAttend
//    private val _listPatients = MutableLiveData<MutableList<Patients>>()
//    val listPatients: LiveData<MutableList<Patients>> = _listPatients

    val listAttend = mutableStateListOf<Pair<String, String>>()
    val listPatients = mutableStateListOf<Patients>()
    val totalPatients: Int
        get() = listAttend.map { it.first }.distinct().size

    val totalUnitCodes: Map<String, Int>
        get() = listAttend.groupBy { it.second }.mapValues { it.value.size }

    val totalCodes: Int
        get() = listAttend.size
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun lazyColumnDeleteItem(context: Context, index: Int) {
        listAttend.removeAt(index)
        if (listAttend.size == 0){
            _resume.value = false
        }
        val file = File(context.filesDir, attendName)
        file.delete()
        for (item in listAttend) {
            file.appendText("${item.first}\n${item.second}\n")
        }
    }

    fun addPatient(patient: String) {
        listPatients.add(Patients(patient.uppercase()))
//        _valuePatients.value = patient
    }

    fun saveDataPatient(context: Context, patient: String) {
        val path = context.filesDir
        try {
            val patientsFile = File(path, patientName)
            val attendFile = File(path, attendName)
            if (!patientsFile.exists()) {
                patientsFile.createNewFile()
            }
            attendFile.createNewFile()
            FileWriter(File(path, patientName), true).use {
                it.appendLine()
                it.write(patient)
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
        _resume.value = false
    }

    //    init{}
    fun loadSavedData(context: Context) {
        val patientsFile = File(context.filesDir, patientName)
        val attendFile = File(context.filesDir, attendName)
        attendFile.createNewFile()
        if (patientsFile.exists()) {
            val patientsList: MutableList<Patients> = mutableListOf()
            val attendList: MutableList<Pair<String, String>> = mutableListOf()
//            val attendList: MutableList<Attends> = mutableListOf()
            try {
                patientsFile.bufferedReader().use { value ->
                    var line: String?
                    while (value.readLine().also { line = it } != null) {
                        if (line?.isNotEmpty() == true) {
                            patientsList.addAll(parsePatientsData(line))
                        }
                    }
                }
                attendFile.bufferedReader().use { value ->
                    var line: String?
                    while (value.readLine().also { line = it } != null) {
                        attendList.addAll(parseAttendData(line))
                    }
                }
                listPatients.addAll(patientsList)
                listAttend.addAll(attendList)
                if (listAttend.size != 0){
                    _resume.value = true
                }
                _valuePatientSpinner.value = listPatients[0].patient
            } catch (e: IOException) {
                Log.e("MyLog", "Error: $e")
            }
        } else {
            Log.d("MyLog", "file exists")
        }
    }

    private fun parsePatientsData(data: String?): List<Patients> {
        val lines = data?.lines()
        val patientsList = mutableListOf<Patients>()
        if (lines != null) {
            for (line in lines) {
                val patient = Patients(line)
                patientsList.add(patient)
            }
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
                    attendList.add(attendPair)
                }
            }
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

    fun addAttend(valuePatientSpinner: String, valueCodesSpinner: String) {
        _resume.value = true
        listAttend.add(
            Pair(
                valuePatientSpinner, valueCodesSpinner
            )
        )
    }
}
