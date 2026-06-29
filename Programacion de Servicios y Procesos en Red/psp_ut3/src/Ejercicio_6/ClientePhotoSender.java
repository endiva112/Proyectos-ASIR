package Ejercicio_6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientePhotoSender {
    //carpeta donde se guardarán los archivos descargados
    private static final String CARPETA_DESCARGAS = "descargas/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //crear carpeta de descargas si no existe
        File carpetaDescargas = new File(CARPETA_DESCARGAS);
        if (!carpetaDescargas.exists()) {
            carpetaDescargas.mkdir();
        }

        try (Socket servidor = new Socket("127.0.0.1", 50006)) {
            System.out.println("Cliente conectado exitosamente al servidor Photo Sender");

            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(servidor.getOutputStream(), true);
            InputStream entradaBinaria = servidor.getInputStream();

            //leer y mostrar lista de archivos disponibles
            String mensajeRecibido;
            while ((mensajeRecibido = flujoEntrada.readLine()) != null) {
                if (mensajeRecibido.equals("FIN_LISTA")) {
                    break;
                } else {
                    System.out.println(mensajeRecibido);
                }
            }

            //pedir nombre del archivo
            System.out.print("\nEscribe el nombre del archivo que deseas descargar: ");
            String nombreArchivo = sc.nextLine();

            //enviar solicitud al servidor
            flujoSalida.println(nombreArchivo);

            //leer respuesta del servidor
            String respuesta = flujoEntrada.readLine();

            if (respuesta == null) {
                System.out.println("Error: No se recibió respuesta del servidor");
                servidor.close();
                return;
            }

            if (respuesta.startsWith("ERROR:")) {
                System.out.println(respuesta);
                servidor.close();
                return;
            }

            //extraer tamaño del archivo
            if (respuesta.startsWith("OK:")) {
                long tamanoArchivo = Long.parseLong(respuesta.substring(3));
                System.out.println("Archivo encontrado (" + tamanoArchivo + " bytes)");
                System.out.println("Comenzando descarga...");

                // Recibir archivo en binario
                File archivoDestino = new File(CARPETA_DESCARGAS + nombreArchivo);

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivoDestino))) {

                    byte[] buffer = new byte[4096];
                    int bytesLeidos;
                    long totalRecibido = 0;

                    //leer bytes mientras haya datos
                    while ((bytesLeidos = entradaBinaria.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesLeidos);
                        totalRecibido += bytesLeidos;

                        //mostrar progreso
                        int porcentaje = (int) ((totalRecibido * 100) / tamanoArchivo);
                        System.out.print("\rProgreso: " + porcentaje + "% (" +
                                totalRecibido + "/" + tamanoArchivo + " bytes)");
                    }

                    System.out.println("\n\n✅ Archivo descargado exitosamente!");
                    System.out.println("📁 Guardado en: " + archivoDestino.getAbsolutePath());
                }
            }

            servidor.close();
            sc.close();

        } catch (IOException e) {
            System.err.println("Error del cliente: " + e.getMessage());
        }
    }
}