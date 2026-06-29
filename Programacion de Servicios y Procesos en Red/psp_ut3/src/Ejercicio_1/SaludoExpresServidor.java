package Ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SaludoExpresServidor {
    public static void main(String[] args) {
        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50001)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50001...");
            System.out.println("Esperando clientes...");

            while (true) {//bucle infinito para que esté siempre escuchando peticiones
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado!");

                //captar info enviada por el cliente
                BufferedReader transmisionEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                //leer | interpretar transmision
                String mensajeEntrante = transmisionEntrada.readLine();
                System.out.println("Mensaje del cliente: " + mensajeEntrante);

                //crear respuesta
                String mensajeSaliente = "Hola, recibí tu mensaje: " + mensajeEntrante;

                //enviar respuesta
                PrintWriter transmisionSalida = new PrintWriter(cliente.getOutputStream(), true);
                transmisionSalida.println(mensajeSaliente);

                //cerrar conexión con el cliente
                cliente.close();
                System.out.println("Cliente desconectado.\n");
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
