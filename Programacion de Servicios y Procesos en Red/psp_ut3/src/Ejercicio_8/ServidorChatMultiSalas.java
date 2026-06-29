package Ejercicio_8;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorChatMultiSalas {
    // Estructura para almacenar salas y sus clientes
    // ConcurrentHashMap es thread-safe
    private static Map<String, List<ManejadorCliente>> salas = new ConcurrentHashMap<>();

    // Salas predefinidas
    private static final String[] SALAS_DISPONIBLES = {
            "Matemáticas", "Historia", "Inglés", "Programación", "General"
    };

    static {
        // Inicializar salas vacías
        for (String sala : SALAS_DISPONIBLES) {
            salas.put(sala, Collections.synchronizedList(new ArrayList<>()));
        }
    }

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(50008)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50008...");
            System.out.println("Esperando clientes...");
            System.out.println("Salas disponibles: " + Arrays.toString(SALAS_DISPONIBLES));
            System.out.println();

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Nuevo cliente conectado desde: " + cliente.getInetAddress());

                // Crear hilo para manejar al cliente
                ManejadorCliente manejador = new ManejadorCliente(cliente);
                Thread hilo = new Thread(manejador);
                hilo.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    //envia mensaje a todos en una sala
    public static void broadcast(String sala, String mensaje, ManejadorCliente remitente) {
        List<ManejadorCliente> clientes = salas.get(sala);

        if (clientes != null) {
            synchronized (clientes) {
                for (ManejadorCliente cliente : clientes) {
                    cliente.enviarMensaje(mensaje);
                }
            }
        }
    }

    //agrega cliente a una sala
    public static void unirseASala(String sala, ManejadorCliente cliente) {
        List<ManejadorCliente> clientes = salas.get(sala);

        if (clientes != null) {
            synchronized (clientes) {
                clientes.add(cliente);
            }

            System.out.println(cliente.getNombre() + " se unió a la sala: " + sala);

            // Notificar a todos en la sala
            broadcast(sala, "[SERVIDOR] " + cliente.getNombre() + " se ha unido a la sala", cliente);
        }
    }

    //eliminar cliente de una sala
    public static void salirDeSala(String sala, ManejadorCliente cliente) {
        List<ManejadorCliente> clientes = salas.get(sala);

        if (clientes != null) {
            synchronized (clientes) {
                clientes.remove(cliente);
            }

            System.out.println(cliente.getNombre() + " salió de la sala: " + sala);

            // Notificar a todos en la sala
            broadcast(sala, "[SERVIDOR] " + cliente.getNombre() + " ha salido de la sala", cliente);
        }
    }

    // Obtener lista de salas disponibles
    public static String[] getSalasDisponibles() {
        return SALAS_DISPONIBLES;
    }

    // Obtener número de usuarios en una sala
    public static int getNumeroUsuarios(String sala) {
        List<ManejadorCliente> clientes = salas.get(sala);
        return clientes != null ? clientes.size() : 0;
    }
}