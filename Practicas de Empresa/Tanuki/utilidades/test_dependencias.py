# Este archivo declara funciones que se prueban antes del lanzamiento del programa para controlar
# los errores más comunes por falta de dependecidas o archivos de configuración

# Comprueba que ADB este disponible para usar
def testADB():
    try:
        import subprocess
        subprocess.run(["adb", "version"], capture_output=True, text=True, check=True)
        print("(OK) ADB está disponible")
    except FileNotFoundError:
        print("(ERROR) ADB no está instalado o no se encuentra en el PATH.")
        print("(INFO) Este programa se cimenta sobre ADB, para más información, leer el README")
    except subprocess.CalledProcessError as e:
        print("Error al ejecutar ADB:", e)

# Comprueba si existe el archivo .env en la raíz del proyecto
def test_envExiste():
    import os
    if os.path.isfile(".env"):
        print("(OK) .env existente")
        return True
    else:
        print("(WARNING) El archivo .env no existe, cree uno usando .env.example como plantilla")
        return False

# Comprueba si la URL de la API es correcta
def testURL():
    import os
    import requests
    from dotenv import load_dotenv

    load_dotenv()
    url = os.getenv("AI_API_URL")
    if not url:
        print("(WARNING) La variable AI_API_URL no está definida en el .env")
        return False
    
    try:
        # Hacemos un GET simple para ver si responde
        response = requests.get(url, timeout=5)
        if response.status_code == 200:
            print(f"(OK) La URL parece ser válida y accesible")
            return True
        else:
            print(f"(WARNING) La URL parece válida pero podría dar problemas, respondió con código {response.status_code}")
            return False
    except requests.exceptions.RequestException as e:
        print(f"(WARNING) La URL parece ser inválida, comprueba los endpoints para la API que estás intentado usar")
        return False

# Comprueba si se ha importado dotenv
def test_python_dotenv():
    try:
        import dotenv
        return True
    except ImportError:
        return False

# Comprueba si se ha importado requests
def test_requests():
    try:
        import requests
        return True
    except ImportError:
        return False

# Comprueba si se tienen todas las dependencias necesarias para lanzar el programa
def testRequirements():
    dotenv_ok = test_python_dotenv()
    requests_ok = test_requests()
    
    if not (dotenv_ok and requests_ok):
        print("(WARNING) Faltan dependencias. Por favor, ejecuta:")
        print("\tpip install -r requirements.txt")
        print("Si ya lo hiciste y sigue apareciendo este error, repórtalo.")
        return False
    else:
        print("(OK) Todas las dependencias están disponibles")
        return True

# Funcion principal
def probarDependecias():
    testADB()
    testRequirements()
    test_envExiste()
    testURL()