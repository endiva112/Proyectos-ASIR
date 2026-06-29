# crawler.py toma el control. Vuelve al home del teléfono, lanza la app, y entra en su bucle principal. En cada iteración hace siempre lo mismo: 
# toma captura de pantalla, descarga el XML, parsea el XML con parser_ui.py para obtener un JSON limpio de elementos interactivos, se lo pasa 
# junto con la captura a cliente_ia.py, y la IA le devuelve qué elementos son y qué representan funcionalmente. 
# Con eso decide qué acción ejecutar a continuación usando adb.py, y actualiza el grafo. 
# Repite hasta que no quedan acciones nuevas por explorar o hasta que el usuario lo para.
from colecciones.mensajes import MENSAJES
import utilidades.parser_ui as parsear_ui
import time, sys, json

class Crawler:
    # Constructor
    def __init__(self, adb, appSeleccionada, carpetaResultados, basicAI, competentAI):
        self.adb = adb
        self.appSeleccionada = appSeleccionada
        self.carpetaResultados = carpetaResultados
        self.basicAI = basicAI
        self.competentAI = competentAI
        self.historial = []

    # Métodos auxiliares
    def hacerCapturaPantalla(self, id):
        time.sleep(1) # tiempo en segundos
        self.adb.hacerCapturaPantalla()
        print("Captura de pantalla realizada")
        self.adb.descargarCapturaPantalla(self.carpetaResultados/"capturas", id)


    # Extrae los permisos, los procesa y lso guarda en el formato correcto
    def extraerPermisos(self):
        print(MENSAJES["infoExtrayendoPermisos"])

        partes = self.appSeleccionada.split("/")
        appSinActivity = partes[0]
        
        # Obtenemos permisos sin procesar
        stdout, stderr = self.adb.extraerPermisosAPP(appSinActivity)
        permisosRAW = stdout

        if stderr:
            print("(ERROR) ", stderr)
            sys.exit(1)

        # Abrir y leer un archivo completo
        with open("prompts/PERMISOS.txt", "r", encoding="utf-8") as f:
            prompt = f.read()
        
        # Preparamos el cuerpo de nuestra petición
        peticion = prompt + permisosRAW
        
        # Consultamos a la API
        respuestaIA = self.basicAI.atacarAPI(peticion)
        if respuestaIA == None:
            print("(Error) Ha ocurrido un problema al atacar a la API, asegurate de que la URL especificada en el .env sea la correcta")
            sys.exit(1)
        return respuestaIA

    # Toma los permisos extraidos, los pasa por una IA para ordenarlos y sacar conclusiones relevantes
    def generarInformePermisos(self):
        respuestaIA = self.extraerPermisos()
        print(MENSAJES["exitoExtracciónPermisos"])

        informePermisos = self.carpetaResultados / "Informe_de_permisos.txt"
        with open(informePermisos, "w", encoding="utf-8") as f:
            f.write(respuestaIA)



    # Método principal de la lógica del programa
    def iniciar(self):
        print(MENSAJES["infoComenzandoExploracion"])

        self.adb.pulsarHOME() # Primero salimos al Home

        idExploracion = 0 # lo ideal es que la app pueda ir generando un id unico para cada vista, pero eso es un problema para el futuro TODO

        # Empezamos la navegación desde feura de la app
        self.hacerCapturaPantalla(idExploracion)

        # Lanzamos la app
        self.adb.lanzarAPP(self.appSeleccionada)
        print("Lanzada la app")
        exploracionTerminada = False

        # Generamos informe de permisos                 COMENTADO PARA EVITAR CONSUMO DE TOKENS DURANTE EL TESTEO DE LA GENERACION DEL GRAFO TODO descomentar
        # self.generarInformePermisos()

        # BUCLE PRINCIPAL DEL CRAWLER
        while exploracionTerminada == False:
            idExploracion += 1
            self.hacerCapturaPantalla(idExploracion)

            # generar json navegacion
            self.adb.generarXMLVistaActual()
            xmlDump = self.carpetaResultados / "vistaActual.xml"  # Ruta del archivo que se va a crear
            self.adb.descargarXML(xmlDump)                        # Descarga el archivo en la ruta de vista
            xmlLimpio = parsear_ui.parsear_ui(xmlDump)            # Se parsea y se elimina lo no importante
            grafoVista = self.carpetaResultados / f"vista_{idExploracion}.json"
            with open(grafoVista, "w", encoding="utf-8") as f:
                f.write(json.dumps(xmlLimpio, ensure_ascii=False, indent=2))

            # leer reglas sobre el formato a devolver
            with open("prompts/EXPLORAR.txt", "r", encoding="utf-8") as f:
                plantilla = f.read()

            # prompt del usuario
            # objetivo = "Explora la app libremente, intenta no pasar por vistas ya exploradas si es posible"
            objetivo = "Explora la app buscando un barbershop. Cuando encuentres los precios de servicios para hombres en cualquier barbershop, indícame el precio de pedir cita y termina la exploración."

            historialTexto = "\n".join(self.historial)
            prompt = plantilla.replace("<<OBJETIVO>>", objetivo).replace("<<ELEMENTOS>>", json.dumps(xmlLimpio, ensure_ascii=False)).replace("<<HISTORIAL>>", historialTexto)

            # se ataca a la ia
            print("consultando al agente")
            respuestaIA = self.competentAI.atacarAPI_con_imagen(prompt, self.carpetaResultados/"capturas"/f"{idExploracion}.png")
            print("el agente ha respondido")
            respuestaIA = respuestaIA.strip().removeprefix("```json").removeprefix("```").removesuffix("```").strip() # FIXME no siempre lo genera así, hacer que esta linea lo haga solo si el archivo comienza con ```json

            ruta_respuesta = self.carpetaResultados / f"respuesta{idExploracion}.json"
            # se escribe la respuesta
            with open(ruta_respuesta, "w", encoding="utf-8") as f:
                f.write(respuestaIA)
            
            # Ahora leeremos el json
            with open(self.carpetaResultados/f"respuesta{idExploracion}.json", "r", encoding="utf-8") as f:
                datosJSON = f.read()
            datos = json.loads(datosJSON)

            accion = datos["accion"]
            coordenadas = datos["coordenadas"]
            coordenadas_scroll = datos["coordenadas_scroll"]
            textoQueEscribir = datos["texto_a_escribir"]
            print("La IA ha decidido: " + accion)

            # Crear memoria
            entrada = f"Iteración {idExploracion}: {accion} en {coordenadas} - {datos['razonamiento']} | Reflexión: {datos['reflexion']}"
            self.historial.append(entrada)

            historialPath = self.carpetaResultados / "historial.txt"
            with open(historialPath, "a", encoding="utf-8") as f:
                f.write(entrada + "\n")

            # Ejecutar acción
            if accion == "pulsar":
                x = coordenadas[0]
                y = coordenadas[1]
                self.adb.pulsar(x, y)

            elif accion == "mantener-pulsado":
                x = coordenadas[0]
                y = coordenadas[1]
                self.adb.mantenerPulsado(x, y)
                print("Por implementar")

            elif accion == "deslizar":
                x1 = coordenadas_scroll[0]
                y1 = coordenadas_scroll[1]
                x2 = coordenadas_scroll[2]
                y2 = coordenadas_scroll[3]
                duracion = coordenadas_scroll[4]
                self.adb.deslizar(x1, y1, x2, y2, duracion)

            elif accion == "escribir":
                self.adb.escribir(textoQueEscribir)

            elif accion == "intro":
                self.adb.pulsarENTER()

            elif accion == "volver":
                self.adb.pulsarBACK()

            elif accion == "esperar":
                time.sleep(2) # Detiene el programa 2 segundos

            elif accion == "fin":
                exploracionTerminada = True

            else:
                print("LA IA ALUCINA, MODIFICAR EL PROMPT Y HACER DEBUGGING PARA SOLUCIONARLO")
                sys.exit(1)

            # CORTAR EL BUCLE TRAS 15 INTERACCIONES PARA REDUCIR COSTES DURANTE TESTEO
            if idExploracion >= 80:
                exploracionTerminada = True

        print("Análisis del crawler finalizado")