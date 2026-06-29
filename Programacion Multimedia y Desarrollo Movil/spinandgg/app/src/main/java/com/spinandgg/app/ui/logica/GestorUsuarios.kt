package com.spinandgg.app.ui.logica

import com.spinandgg.app.ui.logica.GestorApuestas.apuestasExistentes
import com.spinandgg.app.ui.modelos.Apuesta
import com.spinandgg.app.ui.modelos.Usuario

object GestorUsuarios {
    val usuariosRegistrados = mutableListOf<Usuario>()
    var usuarioActivo: Usuario? = null      //usuario que en este momento está logueado en la app

    fun cargarUsuarios() {
        val usuarios = listOf(
            Usuario(
                1,
                "juanjo123",
                "Juan José Gómez Cobo",
                "juanjo@gmail.com",
                "+34 612 345 678",
                "15 de Noviembre, 2024",
                "1234",
                "user_1"
            ),

            Usuario(
                2,
                "alvaro666",
                "Álvaro Pérez Sánchez",
                "alvaro@gmail.com",
                "+34 678 901 234",
                "20 de Octubre, 2024",
                "abcd",
                "user_2"
            ),

            Usuario(
                3,
                "carlito07",
                "Carlos Rodríguez López",
                "carlos@gmail.com",
                "+34 611 223 344",
                "05 de Septiembre, 2024",
                "pass",
                "user_ico"
            )
        )

        usuariosRegistrados.clear()
        usuariosRegistrados.addAll(
            usuarios.map { usuario ->
                // Convertimos el nombre de la imagen a un drawable real
                usuario.copy(
                    userID = GestorApuestas.obtenerIdDesdeNombre(usuario.userImg)
                )
            }
        )
    }

    fun obtenerIdDesdeNombre(name: String): Int {
        return when (name) {
            "user_1" -> com.spinandgg.app.R.drawable.user_1
            "user_2" -> com.spinandgg.app.R.drawable.user_2
            "user_ico" -> com.spinandgg.app.R.drawable.user_ico
            else -> com.spinandgg.app.R.drawable.user_ico
        }
    }

    fun login(usernameOrEmail: String, password: String): Usuario? {
        return usuariosRegistrados.find {
            (it.username == usernameOrEmail || it.email == usernameOrEmail)
                    && it.password == password
        }
    }

    fun crearSesion(usuarioLogueado: Usuario) {
        usuarioActivo = usuarioLogueado
    }

}