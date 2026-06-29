# archivo de testeo para probar funcionalidades por separado
# import utilidades.parser_ui as parser
import utilidades.utilidades as utils
import utilidades.adb as ADB
import utilidades.parser_ui as parser
import xml.etree.ElementTree as ET
import json, subprocess


# dispositivos = utils.obtenerDispositivos()
# utils.listarDispositivos(dispositivos)
# dispositivo = utils.seleccionarDispositivo(dispositivos)

# Creada instancia de adb para dispositivo seleccionado
adb = ADB.ADB("emulator-5554")

def parsear_Experimental(rutaXML):
    arbolDeNodos = ET.parse(rutaXML)
    return


def main():
    # utils.explorarAppYaInstalada(adb)
    stdout, stderr = adb.obtenerAppActivity("org.nativescript.almonte.patrimonio")
    #print(stdout)
    # adb.lanzarAPP(stdout)

    # generar dump
    adb.generarXMLVistaActual()
    adb.descargarXML(".")

    # parsear
    resultado = parser.parsear_ui("window_dump.xml")

    with open("z_3limpio.json", "w", encoding="utf-8") as f:
        json.dump(resultado, f, ensure_ascii=False, indent=2)




main()


