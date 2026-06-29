package Ejercicio_4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatSimpleCliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //creo el socket por el que se conectará mi cliente
        try (Socket servidor = new Socket("127.0.0.1", 50004)) {
            System.out.println("Cliente conectado exitosamente al servidor de chat");

            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(servidor.getOutputStream(), true);

            //hilo para recibir mensajes del servidor (escuchar continuamente)
            Thread hiloReceptor = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mensajeRecibido;
                        while ((mensajeRecibido = flujoEntrada.readLine()) != null) {
                            System.out.println(mensajeRecibido);
                        }
                    } catch (IOException e) {
                        System.out.println("\nConexión con el servidor perdida.");
                    }
                }
            });
            hiloReceptor.start();

            //hilo principal: envia mensajes al servidor
            String mensaje;

            while (true) {
                mensaje = sc.nextLine();

                if (!mensaje.isEmpty()) {//solo si el usuario escribe algo se envía
                    flujoSalida.println(mensaje);
                }

                // Si escribe /salir, desconectarse
                if (mensaje.equals("/salir")) {
                    System.out.println("Desconectando...");
                    break;
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
