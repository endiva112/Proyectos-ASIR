package Ejercicio_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Clase que maneja la comunicación con un cliente específico
class ManejadorCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombre;
    private String salaActual;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Configurar flujos
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre del usuario
            salida.println("╔═══════════════════════════════════════╗");
            salida.println("║   BIENVENIDO AL CHAT MULTI-SALAS     ║");
            salida.println("╚═══════════════════════════════════════╝");
            salida.println("Por favor, ingresa tu nombre:");
            salida.println("PEDIR_NOMBRE");

            nombre = entrada.readLine();
            if (nombre == null || nombre.trim().isEmpty()) {
                nombre = "Usuario" + System.currentTimeMillis();
            }

            System.out.println("Nuevo usuario: " + nombre);

            // Mostrar salas disponibles y solicitar elección
            mostrarSalasDisponibles();

            // CORREGIDO: Leer la primera sala y unirse automáticamente
            String primeraSala = entrada.readLine();
            if (primeraSala != null && !primeraSala.trim().isEmpty()) {
                cambiarSala(primeraSala.trim());
            }

            // Bucle principal de comunicación
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {

                // Comando para cambiar de sala
                if (mensaje.startsWith("/cambiar ")) {
                    String nuevaSala = mensaje.substring(9).trim();
                    cambiarSala(nuevaSala);

                    // Comando para listar salas
                } else if (mensaje.equals("/salas")) {
                    mostrarSalasDisponibles();

                    // Comando para ver usuarios en sala actual
                } else if (mensaje.equals("/usuarios")) {
                    int numUsuarios = ServidorChatMultiSalas.getNumeroUsuarios(salaActual);
                    enviarMensaje("[SERVIDOR] Hay " + numUsuarios + " usuarios en esta sala");

                    // Comando para salir
                } else if (mensaje.equals("/salir")) {
                    break;

                    // Comando de ayuda
                } else if (mensaje.equals("/ayuda")) {
                    mostrarAyuda();

                    // Mensaje normal
                } else {
                    if (salaActual != null) {
                        ServidorChatMultiSalas.broadcast(salaActual, nombre + ": " + mensaje, this);
                    } else {
                        enviarMensaje("[ERROR] Primero debes unirte a una sala");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error con el cliente " + nombre + ": " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    private void mostrarSalasDisponibles() {
        salida.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("         SALAS DISPONIBLES");
        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        String[] salas = ServidorChatMultiSalas.getSalasDisponibles();
        for (int i = 0; i < salas.length; i++) {
            int numUsuarios = ServidorChatMultiSalas.getNumeroUsuarios(salas[i]);
            salida.println((i + 1) + ") " + salas[i] + " (" + numUsuarios + " usuarios)");
        }

        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("Escribe el nombre de la sala o su número:");
        salida.println("PEDIR_SALA");
    }

    private void cambiarSala(String nuevaSala) {
        // Validar que la sala exista
        String[] salasDisponibles = ServidorChatMultiSalas.getSalasDisponibles();
        boolean salaValida = false;
        String salaFinal = nuevaSala;

        // Verificar si es un número
        try {
            int numero = Integer.parseInt(nuevaSala);
            if (numero >= 1 && numero <= salasDisponibles.length) {
                salaFinal = salasDisponibles[numero - 1];
                salaValida = true;
            }
        } catch (NumberFormatException e) {
            // No es un número, verificar si el nombre coincide
            for (String sala : salasDisponibles) {
                if (sala.equalsIgnoreCase(nuevaSala)) {
                    salaFinal = sala;
                    salaValida = true;
                    break;
                }
            }
        }

        if (!salaValida) {
            enviarMensaje("[ERROR] Sala no válida. Usa /salas para ver las disponibles");
            return;
        }

        // Salir de la sala actual si existe
        if (salaActual != null) {
            ServidorChatMultiSalas.salirDeSala(salaActual, this);
        }

        // Unirse a la nueva sala
        salaActual = salaFinal;
        ServidorChatMultiSalas.unirseASala(salaActual, this);

        enviarMensaje("[SERVIDOR] Te has unido a la sala: " + salaActual);
        enviarMensaje("Comandos: /cambiar [sala], /salas, /usuarios, /ayuda, /salir");
        enviarMensaje("SALA_CAMBIADA");
    }

    private void mostrarAyuda() {
        salida.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("           COMANDOS DISPONIBLES");
        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("/cambiar [sala] - Cambiar a otra sala");
        salida.println("/salas          - Ver salas disponibles");
        salida.println("/usuarios       - Ver usuarios en sala actual");
        salida.println("/ayuda          - Mostrar esta ayuda");
        salida.println("/salir          - Salir del chat");
        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public String getNombre() {
        return nombre;
    }

    private void desconectar() {
        try {
            // Salir de la sala actual
            if (salaActual != null) {
                ServidorChatMultiSalas.salirDeSala(salaActual, this);
            }

            socket.close();
            System.out.println("Usuario " + nombre + " desconectado\n");

        } catch (IOException e) {
            System.err.println("Error al cerrar socket: " + e.getMessage());
        }
    }
}