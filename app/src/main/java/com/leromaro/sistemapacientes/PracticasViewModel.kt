package com.leromaro.sistemapacientes

import androidx.lifecycle.ViewModel

class PracticasViewModel : ViewModel() {
    val listAtencion = mutableListOf<Pair<String,String>>()
    val pacientesTotales: Int
        get() = listAtencion.map { it.first }.distinct().size

    val codigosTotales: Map<String, Int>
        get() = listAtencion.groupBy { it.second }.mapValues { it.value.size }
}