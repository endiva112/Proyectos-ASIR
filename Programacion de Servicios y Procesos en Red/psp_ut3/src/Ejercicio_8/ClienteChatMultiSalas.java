package Ejercicio_8;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteChatMultiSalas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Socket servidor = new Socket("127.0.0.1", 50008)) {
            System.out.println("Cliente conectado exitosamente al servidor");

            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(servidor.getOutputStream(), true);

            //hilo para recibir mensajes del servidor
            Thread hiloReceptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = flujoEntrada.readLine()) != null) {

                        // Marcadores especiales
                        if (mensaje.equals("PEDIR_NOMBRE")) {
                            System.out.print("> ");

                        } else if (mensaje.equals("PEDIR_SALA")) {
                            System.out.print("> ");

                        } else if (mensaje.equals("SALA_CAMBIADA")) {
                            // No mostrar este marcador

                        } else {
                            System.out.println(mensaje);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("\n[Desconectado del servidor]");
                }
            });
            hiloReceptor.start();

            //hilo principal: enviar mensajes
            String mensaje;
            while (true) {
                mensaje = sc.nextLine();
                flujoSalida.println(mensaje);

                if (mensaje.equals("/salir")) {
                    System.out.println("Saliendo del chat...");
                    break;
                }
            }

            servidor.close();
            sc.close();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
