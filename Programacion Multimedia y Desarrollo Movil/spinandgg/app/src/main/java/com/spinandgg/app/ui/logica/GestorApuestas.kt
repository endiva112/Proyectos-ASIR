package com.spinandgg.app.ui.logica

import com.spinandgg.app.ui.modelos.Apuesta

object GestorApuestas {
    val apuestasExistentes = mutableListOf<Apuesta>()

    fun cargarApuestas() {
        val apuestas = listOf(
            Apuesta(1, "bet_1", "13/12/2025 - 18:45", "Karmine Corp", "Vitality", 1.25, 4.30, 3.50),
            Apuesta(2, "bet_2", "15/12/2025 - 21:27", "T1", "Dplus Kia", 1.75, 23.12, 2.14),
            Apuesta(3, "bet_3", "12/01/2026 - 14:00", "Real Madrid", "Manchester City", 1.75, 13.07, 1.80)
        )

        apuestasExistentes.clear()
        apuestasExistentes.addAll(
            apuestas.map { apuesta ->
                // Convertimos el nombre de la imagen a un drawable real
                apuesta.copy(
                    betID = obtenerIdDesdeNombre(apuesta.betImg)
                )
            }
        )
    }

    fun obtenerIdDesdeNombre(name: String): Int {
        return when (name) {
            "bet_1" -> com.spinandgg.app.R.drawable.bet_1
            "bet_2" -> com.spinandgg.app.R.drawable.bet_2
            "bet_3" -> com.spinandgg.app.R.drawable.bet_3
            else -> com.spinandgg.app.R.drawable.fotologin
        }
    }


}