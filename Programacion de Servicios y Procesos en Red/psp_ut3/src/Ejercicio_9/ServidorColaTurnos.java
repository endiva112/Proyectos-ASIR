package Ejercicio_9;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorColaTurnos {
    // Contador atómico para asignar turnos
    private static AtomicInteger contadorTurnos = new AtomicInteger(1);

    // Turno que se está atendiendo actualmente
    private static AtomicInteger turnoActual = new AtomicInteger(0);

    // Cola de clientes esperando (ordenada por turno)
    private static Map<Integer, ClienteTurno> clientesEnEspera = new ConcurrentHashMap<>();

    // Tiempo de atención por turno (en segundos)
    private static final int TIEMPO_ATENCION = 5;

    // Número de puestos de atención
    private static final int NUM_PUESTOS = 2;

    public static void main(String[] args) {

        //iniciar hilos de atención
        for (int i = 1; i <= NUM_PUESTOS; i++) {
            int numeroPuesto = i;
            Thread hiloPuesto = new Thread(() -> procesarTurnos(numeroPuesto));
            hiloPuesto.setDaemon(true);
            hiloPuesto.start();
        }

        //hilo para actualizar estado a todos los clientes
        Thread hiloActualizador = new Thread(() -> actualizarEstadoClientes());
        hiloActualizador.setDaemon(true);
        hiloActualizador.start();

        //aceptar conexiones de clientes
        try (ServerSocket servidor = new ServerSocket(50009)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50009...");
            System.out.println("Puestos de atención: " + NUM_PUESTOS);
            System.out.println("Tiempo de atención: " + TIEMPO_ATENCION + " segundos\n");
            System.out.println("Esperando clientes...");

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("Nuevo cliente conectado");

                //crear hilo para manejar al cliente
                Thread hiloCliente = new Thread(() -> atenderCliente(clienteSocket));
                hiloCliente.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void atenderCliente(Socket socket) {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Asignar turno
            int turno = contadorTurnos.getAndIncrement();

            // Crear objeto cliente
            ClienteTurno cliente = new ClienteTurno(socket, salida, turno);
            clientesEnEspera.put(turno, cliente);

            System.out.println("Turno #" + turno + " asignado");

            // Enviar información inicial
            salida.println("╔═══════════════════════════════════════╗");
            salida.println("║       SISTEMA DE TURNOS               ║");
            salida.println("╚═══════════════════════════════════════╝");
            salida.println("Tu turno es: #" + turno);
            salida.println("Turno actual en atención: #" + turnoActual.get());
            salida.println("Clientes esperando: " + (turno - turnoActual.get() - 1));
            salida.println("\nEspera tu turno...");
            salida.println("Escribe '/estado' para ver tu posición");
            salida.println("Escribe '/salir' para abandonar la cola");
            salida.println("ESPERANDO");

            // Bucle para manejar comandos del cliente mientras espera
            String mensaje;
            while ((mensaje = entrada.readLine()) != null && !cliente.isAtendido()) {

                if (mensaje.equals("/estado")) {
                    enviarEstado(cliente);

                } else if (mensaje.equals("/salir")) {
                    salida.println("Has abandonado la cola.");
                    salida.println("FIN");
                    clientesEnEspera.remove(turno);
                    System.out.println("Turno #" + turno + " abandonó la cola");
                    break;

                } else {
                    salida.println("Comandos: /estado, /salir");
                }
            }

            // Esperar a que termine la atención si ya fue llamado
            while (!cliente.isTerminado()) {
                Thread.sleep(100);
            }

            socket.close();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error atendiendo cliente: " + e.getMessage());
        }
    }

    // Hilo que simula los puestos de atención
    private static void procesarTurnos(int numeroPuesto) {
        System.out.println("Puesto #" + numeroPuesto + " iniciado");

        while (true) {
            try {
                // Obtener siguiente turno disponible
                int siguienteTurno = turnoActual.incrementAndGet();

                ClienteTurno cliente = clientesEnEspera.get(siguienteTurno);

                if (cliente != null && !cliente.isAtendido()) {
                    // Llamar al cliente
                    System.out.println("Puesto #" + numeroPuesto + " atendiendo turno #" + siguienteTurno);

                    cliente.getSalida().println("\n╔═══════════════════════════════════════╗");
                    cliente.getSalida().println("║      ¡ES TU TURNO!                    ║");
                    cliente.getSalida().println("╚═══════════════════════════════════════╝");
                    cliente.getSalida().println("Dirígete al Puesto #" + numeroPuesto);
                    cliente.getSalida().println("LLAMADO");

                    cliente.setAtendido(true);

                    // Simular tiempo de atención
                    for (int i = TIEMPO_ATENCION; i > 0; i--) {
                        Thread.sleep(1000);
                        cliente.getSalida().println("Atención en progreso... " + i + "s restantes");
                    }

                    // Finalizar atención
                    cliente.getSalida().println("\n✅ Atención finalizada. ¡Gracias por tu espera!");
                    cliente.getSalida().println("FIN");
                    cliente.setTerminado(true);

                    clientesEnEspera.remove(siguienteTurno);
                    System.out.println("Puesto #" + numeroPuesto + " finalizó turno #" + siguienteTurno);

                } else {
                    // No hay cliente para este turno, retroceder contador
                    turnoActual.decrementAndGet();
                    Thread.sleep(1000); // Esperar antes de reintentar
                }

            } catch (InterruptedException e) {
                System.err.println("Error en puesto #" + numeroPuesto + ": " + e.getMessage());
            }
        }
    }

    // Hilo que actualiza el estado a todos los clientes esperando
    private static void actualizarEstadoClientes() {
        while (true) {
            try {
                Thread.sleep(3000); // Actualizar cada 3 segundos

                int turnoEnAtencion = turnoActual.get();

                for (ClienteTurno cliente : clientesEnEspera.values()) {
                    if (!cliente.isAtendido() && !cliente.isTerminado()) {
                        int posicion = cliente.getTurno() - turnoEnAtencion - 1;
                        if (posicion >= 0) {
                            cliente.getSalida().println("[Actualización] Turno actual: #" + turnoEnAtencion +
                                    " | Tu turno: #" + cliente.getTurno() +
                                    " | Clientes delante: " + posicion);
                        }
                    }
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static void enviarEstado(ClienteTurno cliente) {
        int turnoEnAtencion = turnoActual.get();
        int posicion = cliente.getTurno() - turnoEnAtencion - 1;

        cliente.getSalida().println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        cliente.getSalida().println("         ESTADO DE TU TURNO");
        cliente.getSalida().println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        cliente.getSalida().println("Tu turno: #" + cliente.getTurno());
        cliente.getSalida().println("Turno en atención: #" + turnoEnAtencion);
        cliente.getSalida().println("Clientes delante de ti: " + Math.max(0, posicion));
        cliente.getSalida().println("Tiempo estimado: ~" + (posicion * TIEMPO_ATENCION / NUM_PUESTOS) + " segundos");
        cliente.getSalida().println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}