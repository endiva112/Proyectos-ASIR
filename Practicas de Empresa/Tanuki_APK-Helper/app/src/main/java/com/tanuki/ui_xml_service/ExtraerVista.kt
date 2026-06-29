package com.tanuki.ui_xml_service

import android.content.Context
import java.io.File

object ExtraerVista {
    fun capturar(servicio: TanukiAccessibilityService) {

        // Intenta obtener la ventana activa
        val root = servicio.rootInActiveWindow

        // Si no hay ventana, termina y avisa
        if (root == null) {
            android.util.Log.e("ExtraerVista", "rootInActiveWindow es null")
            return
        }

        // Si hay ventana, imprime datos básicos del nodo raíz
        android.util.Log.d("ExtraerVista", "=== DUMP INICIO ===")
        android.util.Log.d("ExtraerVista", "Package: ${root.packageName}")
        android.util.Log.d("ExtraerVista", "Clase raíz: ${root.className}")
        android.util.Log.d("ExtraerVista", "Hijos directos: ${root.childCount}")
        android.util.Log.d("ExtraerVista", "=== DUMP FIN ===")
    }
}