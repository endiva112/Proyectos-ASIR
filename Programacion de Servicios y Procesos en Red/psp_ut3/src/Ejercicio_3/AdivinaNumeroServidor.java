package Ejercicio_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class AdivinaNumeroServidor {
    public static void main(String[] args) {
        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50003)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50003...");
            System.out.println("Esperando jugadores...");

            while (true) {//bucle infinito para que esté siempre escuchando peticiones
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                System.out.println("¡Jugador conectado!");

                //generar número secreto entre 1 y 100
                Random rand = new Random();
                int numeroSecreto = rand.nextInt(100) + 1;
                System.out.println("Número secreto generado: " + numeroSecreto);

                //enviar mensaje de bienvenida
                PrintWriter transmisionSalida = new PrintWriter(cliente.getOutputStream(), true);
                transmisionSalida.println("¡Bienvenido al juego! Adivina el número entre 1 y 100");

                //captar info enviada por el cliente
                BufferedReader transmisionEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                //variables necesarias para el juego
                int intentos = 0;
                boolean acerto = false;

                //bucle de juego: leer intentos hasta que acierte
                while (!acerto) {
                    try {
                        //leer | interpretar transmisión
                        String mensajeEntrante = transmisionEntrada.readLine();

                        //si el cliente se desconecta, readLine() devuelve null
                        if (mensajeEntrante == null) {
                            System.out.println("Cliente desconectado abruptamente.");
                            break;
                        }

                        intentos++;
                        int intento = Integer.parseInt(mensajeEntrante);
                        System.out.println("Intento #" + intentos + ": " + intento);

                        // Comparar y responder
                        if (intento < numeroSecreto) {
                            transmisionSalida.println("Mayor");
                        } else if (intento > numeroSecreto) {
                            transmisionSalida.println("Menor");
                        } else {
                            transmisionSalida.println("¡Acertaste! Lo lograste en " + intentos + " intentos.");
                            acerto = true;
                            System.out.println("¡El jugador acertó en " + intentos + " intentos!\n");
                        }
                    } catch (NumberFormatException e) {
                        transmisionSalida.println("Error: Debes enviar un número válido");
                        System.out.println("El cliente envió algo que no es un número");
                    }
                }
                //cerrar conexión con el cliente
                cliente.close();
                System.out.println("Jugador desconectado.\n");
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}