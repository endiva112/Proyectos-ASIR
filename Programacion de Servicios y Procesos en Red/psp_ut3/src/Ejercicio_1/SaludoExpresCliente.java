package Ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SaludoExpresCliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //creo el socket por el que se conectará mi cliente
        try (Socket servidor = new Socket("127.0.0.1", 50001)) {
            System.out.println("Conectado al servidor");

            //pedir mensaje al usuario
            System.out.print("Escribe un mensaje para el servidor: ");
            String mensajeDelUsuario = sc.nextLine();

            //enviar mensaje al servidor
            PrintWriter transmisionSalida = new PrintWriter(servidor.getOutputStream(), true);
            transmisionSalida.println(mensajeDelUsuario);

            //captar info enviada por el servidor
            BufferedReader transmisionEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

            //leer | interpretar transmision
            String mensajeEntrante = transmisionEntrada.readLine();
            System.out.println("Respuesta del servidor: " + mensajeEntrante);

            //cerrar recursos
            servidor.close();
            sc.close();
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
