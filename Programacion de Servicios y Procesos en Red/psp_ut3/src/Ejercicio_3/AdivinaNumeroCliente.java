package Ejercicio_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AdivinaNumeroCliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //creo el socket por el que se conectará mi cliente
        try (Socket servidor = new Socket("127.0.0.1", 50003)) {
            System.out.println("Conectado al servidor");

            //captar info enviada por el servidor
            BufferedReader transmisionEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

            //leer | interpretar transmision
            String mensajeEntrante = transmisionEntrada.readLine();
            System.out.println(mensajeEntrante);
            System.out.println("------------------------------------\n");

            //variables necesarias para el juego
            boolean juegoActivo = true;
            PrintWriter transmisionSalida = new PrintWriter(servidor.getOutputStream(), true);

            //bucle de juego: enviar intentos hasta acertar
            while (juegoActivo) {
                try {
                    //pedir número al usuario
                    System.out.print("Tu intento: ");
                    String intento = sc.nextLine();

                    // Validar que sea un número antes de enviarlo
                    Integer.parseInt(intento); // Solo para validar

                    // Enviar intento al servidor
                    transmisionSalida.println(intento);

                    // Leer respuesta del servidor
                    String respuesta = transmisionEntrada.readLine();

                    if (respuesta == null) {
                        System.out.println("Error: El servidor cerró la conexión.");
                        break;
                    }

                    System.out.println("Servidor: " + respuesta);

                    //en caso de acertar, terminar el juego
                    if (respuesta.startsWith("¡Acertaste!")) {
                        juegoActivo = false;
                        System.out.println("\n¡Felicidades! Has ganado.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error: Debes ingresar un número válido.\n");
                }
            }

            //cerrar recursos
            servidor.close();
            sc.close();
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}