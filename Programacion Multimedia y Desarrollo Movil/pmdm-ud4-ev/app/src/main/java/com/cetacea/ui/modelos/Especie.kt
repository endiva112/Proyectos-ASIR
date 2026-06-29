package com.cetacea.ui.modelos

import androidx.annotation.RawRes

data class Especie(
    val nombre: String,
    val imagenRes: Int,          // imagen local (opcional según uso)
    val imagenUrl: String,       // imagen desde internet
    @RawRes val audioRes: Int,   // audio en res/raw
    val descripcion: String = "Descripción de la ballena"
)
