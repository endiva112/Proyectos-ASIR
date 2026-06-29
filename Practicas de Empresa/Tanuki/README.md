# Tanuki

Tanuki es un crawler automático para exploración funcional de aplicaciones Android.  
Controla la app mediante ADB, captura la UI (pantalla + XML), analiza los elementos interactivos y decide acciones usando un modelo de IA.

> ⚠️ Solo funciona con Android. No es compatible con iOS.

---

## Requisitos

### 1. Python

- Python 3.10 o superior

### 2. Dependencias Python

Se recomienda usar un entorno virtual para mantener el proyecto aislado:

1. Crear el entorno virtual

    ```bash
    python -m venv venv
    ```

2.  Activarlo
    * Windows
    ```bash
    venv\Scripts\activate
    ```

    * Linux / macOS
    ```bash
    source venv/bin/activate
    ```

3. Instalar dependencias

    ```bash
    pip install -r requirements.txt
    ```

Para desactivar el entorno virtual cuando no se use:

```bash
deactivate
```

### 3. ADB (Android Debug Bridge)

Debe estar instalado y accesible desde la terminal:

```bash
adb --version
```

Ejemplo de salida válida:

```
Android Debug Bridge version 1.0.41
Version 36.0.0-13206524
```

ADB forma parte de las **Platform Tools** del SDK de Android. Puede instalarse mediante Android Studio o descargando directamente las [platform-tools](https://developer.android.com/tools/releases/platform-tools).

### 4. Dispositivo Android

Dos opciones disponibles:

#### A) Dispositivo físico

1. Activar **Opciones de desarrollador**.
2. Activar **"Depuración USB"**.
3. Conectar por USB.
4. Aceptar el diálogo de autorización en el dispositivo.
5. Verificar conexión:

```bash
adb devices
```

El dispositivo debe aparecer con el estado `device`.

#### B) Emulador (Android Studio)

1. Crear un dispositivo virtual (AVD).
2. Iniciarlo desde Android Studio.
3. Verificar:

```bash
adb devices
```

---

## Configuración

Crear un archivo `.env` en la raíz del proyecto tomando como referencia el `.env.example` incluido. Solo son necesarias estas variables:

```dotenv
# URL del modelo que se desea usar y su clave (si aplica)
AI_API_URL=https://api.openai.com/v1/models
AI_API_KEY=tuClave
AI_MODEL=gpt-4o
```

Se pueden usar tantos modelos como se deseen, pero el código solo cuenta con que se usen 2 agentes distintos

---

## Ejecución

Desde la raíz del proyecto:

```bash
py main.py
```

No requiere argumentos adicionales.

---

## Limitaciones

- Solo compatible con **Android** (usa ADB).
- Requiere **depuración USB** habilitada en el dispositivo.
- No analiza tráfico de red.
- No soporta **iOS**.

---

NOTAS PARA DESARROLLADORES SOBRE SCOPE DEL PROGRAMA (IGNORAR)

```
- DOABLE    Reconstrucción de flujos internos (interacción/eventos)
- MAYBE     Uso de memoria y tiempos de carga/renderizado
- DOABLE    Permisos solicitados
- MAYBE     Uso de información sensible del usuario
- NO        Comunicaciones externas (HTTPS, sockets)
- DOABLE    Capturas (o al menos análisis) de UI en distintas resoluciones/formatos de dispositivo
```

NECESSARIO INSTALAR: https://graphviz.org/download/ graphviz-14.1.3 