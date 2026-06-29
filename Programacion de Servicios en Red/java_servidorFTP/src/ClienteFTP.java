import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.Scanner;

public class ClienteFTP {

    // ── Credenciales del servidor FTP ──────────────────────────────────────────
    private static final String SERVIDOR  = "ftp.dlptest.com";
    private static final String USUARIO   = "dlpuser";
    private static final String PASSWORD  = "rNrKYTX9g7z3RgJRmxWuGHbeu";

    // Carpeta local donde se guardan los archivos descargados
    private static final String CARPETA_DESCARGAS = "descargas/";

    private static FTPClient ftpClient = new FTPClient();

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("       Cliente FTP en Java                ");
        System.out.println("==========================================");

        // Conectar al arrancar el programa
        if (!conectar()) {
            System.out.println("No se pudo establecer la conexión. Saliendo...");
            return;
        }

        // Crear carpeta local de descargas si no existe
        new File(CARPETA_DESCARGAS).mkdirs();

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 5) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("(ATENCIÓN)  Opción inválida. Introduzca un número del 1 al 5.\n");
                continue;
            }

            switch (opcion) {
                case 1 -> listarArchivos();
                case 2 -> crearCarpeta(scanner);
                case 3 -> crearFichero(scanner);
                case 4 -> descargarFichero(scanner);
                case 5 -> salir();
                default -> System.out.println("(ATENCIÓN)  Opción no reconocida. Elija entre 1 y 5.\n");
            }
        }

        scanner.close();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENÚ
    // ══════════════════════════════════════════════════════════════════════════
    private static void mostrarMenu() {
        System.out.println("\nMenú de opciones:");
        System.out.println("  1. Listar archivos y directorios");
        System.out.println("  2. Crear una carpeta");
        System.out.println("  3. Crear un fichero en el servidor");
        System.out.println("  4. Descargar un fichero");
        System.out.println("  5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  1. CONECTAR
    // ══════════════════════════════════════════════════════════════════════════
    private static boolean conectar() {
        System.out.println("\nConectando con el servidor FTP...");
        try {
            ftpClient.connect(SERVIDOR, 21);
            ftpClient.setSoTimeout(10_000);         // timeout 10 s

            boolean login = ftpClient.login(USUARIO, PASSWORD);
            if (!login) {
                System.out.println("Error: Credenciales incorrectas.");
                ftpClient.disconnect();
                return false;
            }

            // Modo pasivo (recomendado para evitar problemas de firewall)
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("(OK) Conexión establecida correctamente.");
            return true;

        } catch (IOException e) {
            System.out.println("Error: No se pudo establecer la conexión.");
            System.out.println("       Verifique su conexión a Internet o la disponibilidad del servidor FTP.");
            System.out.println("       Detalle: " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  2. LISTAR ARCHIVOS
    // ══════════════════════════════════════════════════════════════════════════
    private static void listarArchivos() {
        System.out.println("\nListando archivos y carpetas en el directorio actual...");
        try {
            FTPFile[] files = ftpClient.listFiles();

            if (files == null || files.length == 0) {
                System.out.println("  (El directorio está vacío)");
                return;
            }

            for (FTPFile file : files) {
                if (file.isDirectory()) {
                    System.out.println("  [DIR]  " + file.getName());
                } else {
                    System.out.println("  [FILE] " + file.getName());
                }
            }

        } catch (IOException e) {
            System.out.println("Error: No se pudo obtener la lista de archivos.");
            System.out.println("       Verifique la conexión con el servidor FTP.");
            System.out.println("       Detalle: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  3. CREAR CARPETA
    // ══════════════════════════════════════════════════════════════════════════
    private static void crearCarpeta(Scanner scanner) {
        System.out.print("\nIngrese el nombre de la carpeta a crear: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("(ATENCIÓN)  El nombre no puede estar vacío.");
            return;
        }

        System.out.println("Intentando crear la carpeta \"" + nombre + "\"...");
        try {
            boolean exito = ftpClient.makeDirectory(nombre);
            if (exito) {
                System.out.println("(OK) Carpeta \"" + nombre + "\" creada correctamente en el servidor FTP.");
            } else {
                // Intentar detectar si ya existe listando
                System.out.println("Error: No se pudo crear la carpeta \"" + nombre + "\".");
                System.out.println("       Posible causa: ya existe o no tiene permisos.");
            }
        } catch (IOException e) {
            System.out.println("Error: Fallo al crear la carpeta.");
            System.out.println("       Detalle: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  4. CREAR FICHERO EN EL SERVIDOR
    // ══════════════════════════════════════════════════════════════════════════
    private static void crearFichero(Scanner scanner) {
        System.out.print("\nIngrese el nombre del archivo a crear (ejemplo: prueba.txt): ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty() || nombre.contains("/") || nombre.contains("\\")) {
            System.out.println("Error: Nombre de archivo inválido.");
            System.out.println("       No puede estar vacío ni contener '/' o '\\'.");
            return;
        }

        System.out.print("Escriba el contenido del archivo (pulse ENTER para finalizar): ");
        String contenido = scanner.nextLine();

        System.out.println("Intentando crear el archivo \"" + nombre + "\"...");
        try {
            byte[] bytes = contenido.getBytes("UTF-8");
            InputStream inputStream = new ByteArrayInputStream(bytes);
            boolean exito = ftpClient.storeFile(nombre, inputStream);
            inputStream.close();

            if (exito) {
                System.out.println("(OK) El archivo \"" + nombre + "\" ha sido creado con éxito en el servidor FTP.");
            } else {
                System.out.println("Error: No se pudo crear el archivo en el servidor.");
                System.out.println("       Verifique los permisos o el nombre del archivo.");
            }
        } catch (IOException e) {
            System.out.println("Error: Fallo al subir el archivo.");
            System.out.println("       Detalle: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  5. DESCARGAR FICHERO
    // ══════════════════════════════════════════════════════════════════════════
    private static void descargarFichero(Scanner scanner) {
        System.out.print("\nIngrese el nombre del archivo a descargar: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("(ATENCIÓN)  El nombre no puede estar vacío.");
            return;
        }

        String rutaLocal = CARPETA_DESCARGAS + nombre;
        System.out.println("Intentando descargar \"" + nombre + "\" desde el servidor FTP...");
        System.out.println("Descargando archivo...");

        try (FileOutputStream outputStream = new FileOutputStream(rutaLocal)) {
            boolean exito = ftpClient.retrieveFile(nombre, outputStream);

            if (exito) {
                System.out.println("(OK) Archivo \"" + nombre + "\" guardado correctamente en \"" + rutaLocal + "\".");
            } else {
                // Eliminar el fichero vacío que pudo haber creado
                new File(rutaLocal).delete();
                System.out.println("Error: El archivo \"" + nombre + "\" no existe en el servidor.");
                System.out.println("       Posible causa: nombre incorrecto o archivo eliminado.");
            }
        } catch (IOException e) {
            System.out.println("Error: Fallo al descargar el archivo.");
            System.out.println("       Detalle: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  6. SALIR
    // ══════════════════════════════════════════════════════════════════════════
    private static void salir() {
        System.out.println("\nCerrando conexión FTP...");
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            System.out.println("Aviso: Hubo un problema al cerrar la conexión: " + e.getMessage());
        }
        System.out.println("Conexión finalizada.");
        System.out.println("¡Gracias por usar el cliente FTP en Java!");
    }
}