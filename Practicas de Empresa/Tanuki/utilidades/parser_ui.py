# parser_ui.py recibe la ruta del XML y devuelve una lista limpia de elementos interactivos con sus coordenadas calculadas.
import xml.etree.ElementTree as ET
import re

# Funciones auxiliares
def calcularCentroBounds(bounds):
    coordenadas = list(map(int, re.findall(r'\d+', bounds)))
    x1 = coordenadas[0]
    y1 = coordenadas[1]
    x2 = coordenadas[2]
    y2 = coordenadas[3]

    # // hace division entera, sin decimales, mejor que parsear la ecuacion a int
    x_mb = (x1+x2) // 2
    y_mb = (y1+y2) // 2
    centroBounds = x_mb, y_mb
    return centroBounds

# Hay veces que el elemento no tiene nombre, por ejemplo una tarjeta que dentro tiene un Label descriptivo, 
# en esos casos usamos el nombre de dicho elemento hijo para dar más contexto de lo que hace el padre
def agregarContexto(nodo, elemento, profundidad=0):
    for hijo in nodo:
        prefix = f"[{profundidad}] " if profundidad > 0 else ""
        
        if elemento["texto"] == "":
            texto = hijo.attrib.get("text", "")
            if texto != "":
                elemento["texto"] = prefix + texto

        if elemento["descripcion"] == "":
            descripcion = hijo.attrib.get("content-desc", "")
            if descripcion != "":
                elemento["descripcion"] = prefix + descripcion

        if elemento["texto"] != "" and elemento["descripcion"] != "":
            return elemento

        # Buscar en profundidad si no encontramos nada
        elemento = agregarContexto(hijo, elemento, profundidad + 1)

        if elemento["texto"] != "" and elemento["descripcion"] != "":
            return elemento

    return elemento


# Obtener parámetros para scroll de un elemento
def calcularInfoScroll(bounds):
    coordenadas = list(map(int, re.findall(r'\d+', bounds)))
    x1 = coordenadas[0]
    y1 = coordenadas[1]
    x2 = coordenadas[2]
    y2 = coordenadas[3]

    # Centro horizontal
    cx = (x1 + x2) // 2
    # Scroll hacia abajo: empezamos en 3/4 del contenedor y terminamos en 1/4
    inicio_y = y1 + (y2 - y1) * 3 // 4
    fin_y = y1 + (y2 - y1) * 1 // 4

    # El 1500 son 1,5 segundos, permite hacer scroll visible para debugging de esta función modificar en el futuro si se espera scroll más veloz o más lento
    return [cx, inicio_y, cx, fin_y, 1500]


# Ignora la mayoría de la UI y se retorna solo los elementos interactuables
# Se recorre cada nodo para extraer su interactuabilidad, ya que pueden existir elementos que permitan varias acciones
# Como un botón que hace cosas distintas dependiendo de si es pulsado o pulsado durante un tiempo
def parsear_ui(rutaXML):
    arbolDeNodos = ET.parse(rutaXML)
    elementos = []

    for nodo in arbolDeNodos.iter():
        tipos = []
                                                                    # DETECTA:
        if nodo.attrib.get("clickable") == "true":                  # ELEMENTOS UI CLICABLES - "PRESS"
            tipos.append("pulsar")
        if nodo.attrib.get("long-clickable") == "true":             # ELEMENTOS UI CON PULSADO LARGO - "LONG PRESS"
            tipos.append("mantener-pulsado")
        if nodo.attrib.get("scrollable") == "true":                 # ELEMENTOS UI SCROLLEABLES - "SCROLLABLE"
            tipos.append("deslizar")
        if nodo.attrib.get("checkable") == "true":                  # ELEMENTOS UI MARCABLES - "CHECKABLE"
            tipos.append("alternar")
        if nodo.attrib.get("class") == "android.widget.EditText":   # ELEMENTOS UI DONDE SE PUEDE ESCRIBIR - "EDIT TEXT"
            tipos.append("escribir")
        
        if len(tipos) > 0:
            elemento = {
                "texto": nodo.attrib.get("text", ""),
                "descripcion": nodo.attrib.get("content-desc", ""),
                "coordenadas": calcularCentroBounds(nodo.attrib.get("bounds", "")),
                # Dependiendo de si el elemento es scrolleable, obtengo valores para hacer el scroll o pongo none para que la ia sepa que hacer
                "coordenadas_scroll": calcularInfoScroll(nodo.attrib.get("bounds", "")) if "deslizar" in tipos else None,
                "tipos": tipos
            }

            if "deslizar" in tipos and len(tipos) == 1:
                elemento["texto"] = "contenedor_scrollable"
                elemento["descripcion"] = ""
            else:
                elemento = agregarContexto(nodo, elemento)

            elementos.append(elemento)
    return elementos
