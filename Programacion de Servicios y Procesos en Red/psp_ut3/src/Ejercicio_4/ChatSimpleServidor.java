package Ejercicio_4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatSimpleServidor {
    // Lista compartida de clientes conectados (sus PrintWriters)
    private static List<PrintWriter> listaClientes = new ArrayList<>();
    private static int contadorClientes = 0;


    public static void main(String[] args) {
        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50004)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50004...");
            System.out.println("Esperando clientes...");

            while (true) {
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                contadorClientes++;
                System.out.println("Cliente #" + contadorClientes + " conectado desde: " + cliente.getInetAddress());

                //crear un hilo para manejar este cliente
                Thread hiloCliente = new Thread(new ManejadorCliente(cliente, contadorClientes));
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    //envia un mensaje a TODOS los clientes (broadcast)
    public static void broadcast(String mensaje, PrintWriter remitente) {
        synchronized (listaClientes) {
            for (PrintWriter cliente : listaClientes) {
                cliente.println(mensaje);
            }
        }
    }

    //agrega un cliente a la lista
    public static void agregarCliente(PrintWriter cliente) {
        synchronized (listaClientes) {
            listaClientes.add(cliente);
        }
    }

    //elimina un cliente de la lista
    public static void eliminarCliente(PrintWriter cliente) {
        synchronized (listaClientes) {
            listaClientes.remove(cliente);
        }
    }
}