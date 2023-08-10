package com.leromaro.sistemapacientes.model

enum class Codigo(val tipo : String) {
    CONSULTAS("consultas"),
    URGENCIAS("urgencias"),
    PREVENTIVA("preventiva"),
    RESTAURACIONES("restauraciones"),
    PEDIATRIA("pediatrÍa"),
    PERIODONCIA("periodoncia"),
    RX("rx"),
    CIRUGIA("cirugÍa")
}