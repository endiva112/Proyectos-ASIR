package com.spinandgg.app.ui.modelos

import android.R
import androidx.annotation.Nullable

data class Usuario(
    var userID: Int,
    var username: String,
    var realname: String,
    var email: String,
    var telephone: String,
    var logInDate: String,
    var password: String,
    var userImg: String,
    var balance: Double = 0.0,
    var bets: MutableList<Apuesta> = mutableListOf()
)
