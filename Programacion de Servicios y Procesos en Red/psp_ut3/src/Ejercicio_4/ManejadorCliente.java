package Ejercicio_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//clase que maneja la comunicación con un cliente específico
class ManejadorCliente implements Runnable {
    private Socket socket;
    private int numeroCliente;
    private PrintWriter transmisionSalida;
    private BufferedReader transmisionEntrada;
    private String nombreCliente;

    public ManejadorCliente(Socket socket, int numeroCliente) {
        this.socket = socket;
        this.numeroCliente = numeroCliente;
        this.nombreCliente = "Cliente#" + numeroCliente;
    }

    @Override
    public void run() {
        try {
            //crear flujos de entrada y salida
            transmisionEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            transmisionSalida = new PrintWriter(socket.getOutputStream(), true);

            //agregar este cliente a la lista de clientes conectados
            ChatSimpleServidor.agregarCliente(transmisionSalida);

            //enviar mensaje de bienvenida al nuevo cliente
            transmisionSalida.println("=== Bienvenido al Chat ===");
            transmisionSalida.println("Eres el " + nombreCliente);
            transmisionSalida.println("Escribe /salir para desconectarte");
            transmisionSalida.println("==========================");

            //anunciar a todos que un nuevo cliente se conectó
            ChatSimpleServidor.broadcast(
                    "[SERVIDOR] " + nombreCliente + " se ha unido al chat", transmisionSalida);

            //bucle para leer mensajes del cliente
            String mensaje;
            while ((mensaje = transmisionEntrada.readLine()) != null) {
                // Comando para salir
                if (mensaje.equals("/salir")) {
                    break;
                }
                //mostrar en consola del servidor
                System.out.println(nombreCliente + ": " + mensaje);

                //hacer broadcast del mensaje a todos los clientes
                ChatSimpleServidor.broadcast(nombreCliente + ": " + mensaje, transmisionSalida);
            }
        } catch (IOException e) {
            System.out.println(nombreCliente + " se desconectó abruptamente");
        } finally {
            //liberar recursos cuando el cliente se desconecta
            try {
                // Anunciar que el cliente se fue
                ChatSimpleServidor.broadcast(
                        "[SERVIDOR] " + nombreCliente + " ha salido del chat", transmisionSalida);

                //eliminar de la lista de clientes
                ChatSimpleServidor.eliminarCliente(transmisionSalida);

                //cerrar socket
                socket.close();
                System.out.println(nombreCliente + " desconectado.\n");

            } catch (IOException e) {
                System.err.println("Error al cerrar socket: " + e.getMessage());
            }
        }
    }
}