package Ejercicio_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAhorcado {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Socket servidor = new Socket("127.0.0.1", 50007)) {
            System.out.println("Cliente conectado exitosamente al servidor de El Ahorcado");

            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(servidor.getOutputStream(), true);

            boolean juegoActivo = true;
            String linea;

            while (juegoActivo) {
                linea = flujoEntrada.readLine();

                if (linea == null) {
                    System.out.println("Error: No se recibió respuesta del servidor");
                    break;
                }

                //procesar marcadores especiales
                if (linea.equals("PEDIR_LETRA")) {
                    // Momento de pedir letra al usuario
                    System.out.print("\nTu letra: ");
                    String letra = sc.nextLine();
                    flujoSalida.println(letra);

                } else if (linea.startsWith("ERROR:")) {
                    System.out.println("\n❌ " + linea.substring(6));

                } else if (linea.startsWith("ACIERTO:")) {
                    System.out.println("\n✅ " + linea.substring(8));

                } else if (linea.startsWith("FALLO:")) {
                    System.out.println("\n❌ " + linea.substring(6));

                } else if (linea.startsWith("GANADO:")) {
                    String palabra = linea.substring(7);
                    System.out.println("\n╔═══════════════════════════════════╗");
                    System.out.println("║        ¡GANASTE!                  ║");
                    System.out.println("╚═══════════════════════════════════╝");
                    System.out.println("La palabra era: " + palabra);
                    juegoActivo = false;

                } else if (linea.startsWith("PERDIDO:")) {
                    String palabra = linea.substring(8);
                    System.out.println("\n╔═══════════════════════════════════╗");
                    System.out.println("║        GAME OVER                  ║");
                    System.out.println("╚═══════════════════════════════════╝");
                    System.out.println("La palabra era: " + palabra);
                    juegoActivo = false;

                } else if (linea.equals("ABANDONADO")) {
                    System.out.println("\nHas abandonado el juego.");
                    juegoActivo = false;

                } else {
                    System.out.println(linea);
                }
            }

            servidor.close();
            sc.close();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}