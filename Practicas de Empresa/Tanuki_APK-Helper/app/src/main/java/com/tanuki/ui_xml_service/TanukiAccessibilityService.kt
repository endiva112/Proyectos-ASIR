package com.tanuki.ui_xml_service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class TanukiAccessibilityService : AccessibilityService() {

    companion object {
        var instance: TanukiAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        android.util.Log.d("Accessibility", "Servicio conectado")
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Esto se dispara cuando OTRA app hace algo
        event ?: return

        val root = rootInActiveWindow ?: return
        android.util.Log.d("Tanuki", "App activa: ${event.packageName}")
        android.util.Log.d("Tanuki", "Raíz: ${root.className}, hijos: ${root.childCount}")

        // Aquí llamas a ExtraerVista si quieres
        // ExtraerVista.capturar(root)  ← pasas el nodo, no el contexto
    }


    override fun onInterrupt() {}
}