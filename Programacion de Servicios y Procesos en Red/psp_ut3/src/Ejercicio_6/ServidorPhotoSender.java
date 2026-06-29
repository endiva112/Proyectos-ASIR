package Ejercicio_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorPhotoSender {
    //carpeta donde están los archivos disponibles
    private static final String CARPETA_ARCHIVOS = "archivos_servidor/";

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(50006)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50006...");
            System.out.println("Carpeta de archivos: " + CARPETA_ARCHIVOS);
            System.out.println("Esperando clientes...");

            while (true) {
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado!");

                //crear un hilo para manejar este cliente
                Thread hiloCliente = new Thread(() -> atenderCliente(cliente));
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void atenderCliente(Socket cliente) {
        try {
            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(cliente.getOutputStream(), true);
            OutputStream salidaBinaria = cliente.getOutputStream();

            enviarListaArchivos(flujoSalida);

            //leer nombre del archivo solicitado
            String nombreArchivo = flujoEntrada.readLine();

            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                flujoSalida.println("ERROR:Nombre de archivo vacío");
                cliente.close();
                return;
            }

            System.out.println("Cliente solicitó: " + nombreArchivo);

            //construir ruta completa del archivo
            File archivo = new File(CARPETA_ARCHIVOS + nombreArchivo);

            //verificar si el archivo existe
            if (!archivo.exists() || !archivo.isFile()) {
                System.out.println("Archivo no encontrado: " + nombreArchivo);
                flujoSalida.println("ERROR:Archivo no encontrado");
                cliente.close();
                return;
            }

            //enviar confirmación con el tamaño del archivo
            long tamano = archivo.length();
            flujoSalida.println("OK:" + tamano);

            System.out.println("Enviando archivo: " + nombreArchivo + " (" + tamano + " bytes)");

            //leer archivo y enviarlo en binario
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo))) {

                byte[] buffer = new byte[4096]; // Buffer de 4KB
                int bytesLeidos;
                long totalEnviado = 0;

                while ((bytesLeidos = bis.read(buffer)) != -1) {
                    salidaBinaria.write(buffer, 0, bytesLeidos);
                    totalEnviado += bytesLeidos;
                }

                salidaBinaria.flush();
                System.out.println("Archivo enviado completamente: " + totalEnviado + " bytes");
            }

            cliente.close();
            System.out.println("Cliente desconectado.\n");

        } catch (IOException e) {
            System.err.println("Error del servidor: " + e.getMessage());
        }
    }

    //envia la lista de archivos disponibles
    private static void enviarListaArchivos(PrintWriter salida) {
        File carpeta = new File(CARPETA_ARCHIVOS);

        salida.println("=== ARCHIVOS DISPONIBLES ===");

        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles();

            if (archivos != null && archivos.length > 0) {
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        long tamanoKB = archivo.length() / 1024;
                        salida.println("📄 " + archivo.getName() + " (" + tamanoKB + " KB)");
                    }
                }
            } else {
                salida.println("(No hay archivos disponibles)");
            }
        } else {
            salida.println("(Carpeta no encontrada)");
        }

        salida.println("============================");
        salida.println("FIN_LISTA"); // Marcador de fin de lista
    }
}
