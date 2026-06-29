package Ejercicio_10;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteCarreraVirtual {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Socket socket = new Socket("127.0.0.1", 50010);
            System.out.println("Conectado a la carrera virtual.\n");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Hilo para recibir mensajes del servidor
            Thread hiloReceptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {

                        if (mensaje.equals("INICIO")) {
                            System.out.println("\n🏁 ¡LA CARRERA HA COMENZADO! 🏁\n");
                            System.out.print("> ");

                        } else if (mensaje.equals("FIN")) {
                            System.out.println("\nPresiona ENTER para salir...");
                            break;

                        } else if (mensaje.startsWith("ERROR:")) {
                            System.out.println("❌ " + mensaje.substring(6));

                        } else {
                            System.out.println(mensaje);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("\n[Desconectado del servidor]");
                }
            });
            hiloReceptor.start();

            // Esperar un momento para que se muestre el mensaje de bienvenida
            Thread.sleep(500);

            // Hilo principal: enviar comandos
            System.out.println("\nEscribe tus comandos:");
            String comando;
            while (hiloReceptor.isAlive()) {
                System.out.print("> ");
                comando = sc.nextLine().trim();

                if (!comando.isEmpty()) {
                    salida.println(comando);

                    if (comando.equalsIgnoreCase("SALIR") || comando.equalsIgnoreCase("S")) {
                        break;
                    }
                }
            }

            socket.close();
            sc.close();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}