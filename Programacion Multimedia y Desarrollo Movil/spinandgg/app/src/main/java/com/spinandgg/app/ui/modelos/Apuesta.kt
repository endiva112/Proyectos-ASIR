package com.spinandgg.app.ui.modelos

data class Apuesta(
    val betID: Int,
    val betImg: String,
    val eventDate:String,
    val teamAName: String,
    val teamBName: String,
    val betOddTeamA: Double,
    val betOddDraw: Double,
    val betOddTeamB: Double
)