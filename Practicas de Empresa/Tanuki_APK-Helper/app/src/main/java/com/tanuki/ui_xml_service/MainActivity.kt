package com.tanuki.ui_xml_service

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = TextView(this)
        setContentView(text)
        actualizarTexto()
    }

    override fun onResume() {//com.tanuki.ui_xml_service/.MainActivity
        super.onResume()
        actualizarTexto()

        // Llama directamente para probar
        val servicio = TanukiAccessibilityService.instance
        if (servicio != null) {
            ExtraerVista.capturar(servicio)
        } else {
            android.util.Log.e("ExtraerVista", "Servicio no activo todavía")
        }
    }

    private fun actualizarTexto() {
        val activo = TanukiAccessibilityService.instance != null
        if (activo) {
            text.text = "✅ Servicio activo"
        } else {
            text.text = "Servicio inactivo — toca para activar"
            text.setOnClickListener {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
        }
    }
}