package Ejercicio_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteNoticias {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //creo el socket por el que se conectará mi cliente
        try (Socket servidor = new Socket("127.0.0.1", 50005)) {
            System.out.println("Cliente conectado exitosamente al servidor de noticias");

            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(servidor.getOutputStream(), true);

            String mensajeRecibido;

            //escribir lo enviado por el servidor
            while ((mensajeRecibido = flujoEntrada.readLine()) != null) {
                if (mensajeRecibido.equals("FIN")) {
                    break;
                } else {
                    System.out.println(mensajeRecibido);
                }
            }

            System.out.print("Elija una opción: ");
            String opcion = sc.nextLine();

            //enviar opción al servidor
            flujoSalida.println(opcion);

            //escribir lo enviado por el servidor
            while ((mensajeRecibido = flujoEntrada.readLine()) != null) {
                if (mensajeRecibido.equals("FIN")) {
                    break;
                } else {
                    System.out.println(mensajeRecibido);
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
