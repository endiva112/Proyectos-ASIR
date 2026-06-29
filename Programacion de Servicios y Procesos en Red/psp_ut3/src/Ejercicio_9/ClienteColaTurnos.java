package Ejercicio_9;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteColaTurnos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket("127.0.0.1", 50009)) {
            System.out.println("Cliente conectado exitosamente al servidor");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Hilo para recibir mensajes del servidor
            Thread hiloReceptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {

                        if (mensaje.equals("ESPERANDO")) {
                            System.out.println("\n⏳ Esperando tu turno...\n");

                        } else if (mensaje.equals("LLAMADO")) {
                            System.out.println(); // Línea en blanco para separar

                        } else if (mensaje.equals("FIN")) {
                            break;

                        } else {
                            System.out.println(mensaje);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("\n[Desconectado del servidor]");
                }
            });
            hiloReceptor.start();

            // Hilo principal: enviar comandos
            String comando;
            while (hiloReceptor.isAlive()) {
                if (sc.hasNextLine()) {
                    comando = sc.nextLine();
                    salida.println(comando);

                    if (comando.equals("/salir")) {
                        break;
                    }
                }
                Thread.sleep(100);
            }

            socket.close();
            sc.close();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}