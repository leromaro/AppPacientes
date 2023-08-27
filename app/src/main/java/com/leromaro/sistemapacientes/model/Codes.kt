package com.leromaro.sistemapacientes.model

enum class Codes(
    val tipo : String
    ){
        CONSULTAS("CONSULTAS"),
        URGENCIAS("URGENCIAS"),
        PREVENTIVA("PREVENTIVA"),
        RESTAURACIONES("RESTAURACIONES"),
        PEDIATRIA("PEDIATRÍA"),
        PERIODONCIA("PERIODONCIA"),
        RX("RX"),
        CIRUGIA("CIRUGíA")
}