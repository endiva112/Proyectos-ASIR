# adb.py ejecuta cualquier acción física sobre el dispositivo: tap, swipe, back, captura, descarga de archivos, etc
import subprocess

class ADB:
    # Constructor
    def __init__(self, dispositivo):
        self.dispositivo = dispositivo #Atributo
    
    # Método ejecutor de cualquier comando, retorna resultado y error
    def _lanzar(self, comando):
        resultado = subprocess.run(f"adb -s {self.dispositivo} {comando}", shell=True, capture_output=True, text=True)
        return resultado.stdout.strip(), resultado.stderr.strip()

    def instalarAPK(self, apk):
        stdout, stderr = self._lanzar(f'install -r -t "{apk}"')
        return stdout, stderr

    def desinstalarAPP(self, app):
        stdout, stderr = self._lanzar(f"uninstall {app}")
        return stdout, stderr

    def listarAPPs(self):
        return self._lanzar("shell pm list packages")

    # Lista solo las apps instaladas por el usuario
    def listarAPPsUsuario(self):
        return self._lanzar("shell pm list packages -3")

    #Lista solo las apps instaladas por el sistema
    def listarAPPsSistema(self):
        return self._lanzar("shell pm list packages -s")

    # Muestra rutas de las apks, no creo que se llegue a usar
    #def listarAPPsRutas(self):
    #    self._lanzar("shell pm list packages -f")

    # Devuelve nombre y activity de una apk, necesario para poder lanzarla
    def obtenerAppActivity(self, app):
        return self._lanzar(f"shell cmd package resolve-activity --brief --components {app}")

    # Lanza la app seleccionada con la activity seleccionada
    def lanzarAPP(self, app_y_activity):
        stdout, stderr = self._lanzar(f"shell am start -n {app_y_activity}")
        return stderr

    # region Generar y extraer info

    def extraerPermisosAPP(self, app):
        return self._lanzar(f"shell dumpsys package {app}")

    def hacerCapturaPantalla(self):
        stdout, stderr = self._lanzar("shell screencap -p /sdcard/screen.png")
        return stderr

    def descargarCapturaPantalla(self, ubicacion, idImagen):
        stdout, stderr = self._lanzar(f"pull /sdcard/screen.png {ubicacion}/{idImagen}.png")
        return stderr

    def generarXMLVistaActual(self):
        stdout, stderr = self._lanzar("shell uiautomator dump")
        return stderr

    def descargarXML(self, ubicacion):
        stdout, stderr = self._lanzar(f"pull /sdcard/window_dump.xml {ubicacion}")
        return stderr
    # endregion

    # region EXPLORACION

    # Pulsar el botón de HOME del teléfono
    def pulsarHOME(self):
        stdout, stderr = self._lanzar("shell input keyevent 3")
        return stderr

    # Pulsar el botón de BACK del teléfono
    def pulsarBACK(self):
        stdout, stderr = self._lanzar("shell input keyevent 4")
        return stderr

    # El equivalente a pulsar ENTER
    def pulsarENTER(self):
        stdout, stderr = self._lanzar("shell input keyevent 66")
        return stderr

    # Hace un tap en una coordenada
    def pulsar(self, x, y):
        stdout, stderr = self._lanzar(f"shell input tap {x} {y}")
        return stderr

    # Deslizar
    def deslizar(self, x1, y1, x2, y2, duracion):
        stdout, stderr = self._lanzar(f"shell input swipe {x1} {y1} {x2} {y2} {duracion}")
        return stderr

    # Mantener pulsado - TODO LA MAYORIA DE UI QUE UTILIZA ESTO REQUIERE QUE SE ELIHA OPCION ANTES DE DEJAR DE PULSAR, PUEDE SER NECESARIO MODIFICAR ESTA FUNCION EN EL FUTURO
    def mantenerPulsado(self, x, y):
        duracion = 2000 # Hardcodeado que el mantener pulsado sea de 2 segundos
        stdout, stderr = self._lanzar(f"shell input swipe {x} {y} {x} {y} {duracion}")
        return stderr

    # ADB intentará escribir en el elemento que este focuseado
    def escribir(self, texto):
        stdout, stderr = self._lanzar(f"shell input text {texto}")
        return stderr


    # endregion