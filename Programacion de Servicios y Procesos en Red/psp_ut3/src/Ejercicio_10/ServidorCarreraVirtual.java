package Ejercicio_10;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorCarreraVirtual {
    // Posiciones de los jugadores: ID -> Posición
    private static Map<Integer, Jugador> jugadores = new ConcurrentHashMap<>();

    // Contador de IDs
    private static AtomicInteger contadorIDs = new AtomicInteger(1);

    // Configuración de la carrera
    private static final int META = 100; // metros
    private static final int AVANCE_POR_MOVIMIENTO = 5; // metros por comando

    // Estado del juego
    private static volatile boolean juegoActivo = true;
    private static volatile Integer ganadorID = null;

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║    CARRERA VIRTUAL DE COCHES          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Puerto: 50010");
        System.out.println("Meta: " + META + " metros");
        System.out.println("Avance por movimiento: " + AVANCE_POR_MOVIMIENTO + " metros\n");

        try (ServerSocket servidor = new ServerSocket(50010)) {

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("Nuevo jugador conectado");

                // Crear hilo para manejar al jugador
                Thread hiloJugador = new Thread(() -> manejarJugador(clienteSocket));
                hiloJugador.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void manejarJugador(Socket socket) {
        BufferedReader entrada = null;
        PrintWriter salida = null;
        int jugadorID = 0;

        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Asignar ID al jugador
            jugadorID = contadorIDs.getAndIncrement();

            // Verificar si el juego ya terminó
            if (!juegoActivo) {
                salida.println("ERROR:La carrera ya finalizó. Espera la próxima.");
                socket.close();
                return;
            }

            // Crear jugador
            Jugador jugador = new Jugador(jugadorID, socket, salida);
            jugadores.put(jugadorID, jugador);

            System.out.println("Jugador #" + jugadorID + " unido a la carrera (" + jugadores.size() + " jugadores)");

            // Enviar bienvenida
            salida.println("╔═══════════════════════════════════════╗");
            salida.println("║    BIENVENIDO A LA CARRERA            ║");
            salida.println("╚═══════════════════════════════════════╝");
            salida.println("Eres el Jugador #" + jugadorID);
            salida.println("Meta: " + META + " metros");
            salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            salida.println("Comandos:");
            salida.println("  ADELANTE - Avanzar " + AVANCE_POR_MOVIMIENTO + " metros");
            salida.println("  TURBO    - Avanzar " + (AVANCE_POR_MOVIMIENTO * 2) + " metros (cooldown 5s)");
            salida.println("  ESTADO   - Ver posiciones");
            salida.println("  SALIR    - Abandonar carrera");
            salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            salida.println("¡La carrera comienza AHORA!");
            salida.println("INICIO");

            // Broadcast inicial
            broadcastEstado();

            // Bucle de comandos
            String comando;
            while ((comando = entrada.readLine()) != null && juegoActivo) {

                comando = comando.trim().toUpperCase();

                switch (comando) {
                    case "ADELANTE":
                    case "A":
                        moverJugador(jugador, AVANCE_POR_MOVIMIENTO);
                        break;

                    case "TURBO":
                    case "T":
                        if (jugador.puedeUsarTurbo()) {
                            moverJugador(jugador, AVANCE_POR_MOVIMIENTO * 2);
                            jugador.usarTurbo();
                            salida.println("🚀 ¡TURBO activado!");
                        } else {
                            long tiempoRestante = jugador.getTiempoRestanteTurbo();
                            salida.println("⏳ Turbo en cooldown (" + tiempoRestante + "s restantes)");
                        }
                        break;

                    case "ESTADO":
                    case "E":
                        enviarEstado(salida);
                        break;

                    case "SALIR":
                    case "S":
                        salida.println("Has abandonado la carrera.");
                        salida.println("FIN");
                        break;

                    default:
                        salida.println("Comando no reconocido. Usa: ADELANTE, TURBO, ESTADO, SALIR");
                }

                if (comando.equals("SALIR") || comando.equals("S")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error con jugador #" + jugadorID + ": " + e.getMessage());
        } finally {
            // Limpiar jugador
            if (jugadorID > 0) {
                jugadores.remove(jugadorID);
                System.out.println("Jugador #" + jugadorID + " desconectado");

                if (juegoActivo) {
                    broadcastMensaje("[SERVIDOR] Jugador #" + jugadorID + " abandonó la carrera");
                    broadcastEstado();
                }
            }

            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                socket.close();
            } catch (IOException e) {
                // Ignorar
            }
        }
    }

    private static void moverJugador(Jugador jugador, int distancia) {
        int nuevaPosicion = jugador.getPosicion() + distancia;
        jugador.setPosicion(nuevaPosicion);

        System.out.println("Jugador #" + jugador.getId() + " avanzó a " + nuevaPosicion + "m");

        // Verificar si ganó
        if (nuevaPosicion >= META && juegoActivo) {
            juegoActivo = false;
            ganadorID = jugador.getId();

            System.out.println("\n¡Jugador #" + ganadorID + " GANÓ la carrera!\n");

            // Anunciar ganador a todos
            for (Jugador j : jugadores.values()) {
                if (j.getId() == ganadorID) {
                    j.getSalida().println("\n╔═══════════════════════════════════════╗");
                    j.getSalida().println("║         🏆 ¡GANASTE! 🏆               ║");
                    j.getSalida().println("╚═══════════════════════════════════════╝");
                    j.getSalida().println("¡Llegaste a la meta en primer lugar!");
                } else {
                    j.getSalida().println("\n╔═══════════════════════════════════════╗");
                    j.getSalida().println("║         CARRERA FINALIZADA            ║");
                    j.getSalida().println("╚═══════════════════════════════════════╝");
                    j.getSalida().println("El Jugador #" + ganadorID + " ganó la carrera");
                    j.getSalida().println("Tu posición final: " + j.getPosicion() + "m");
                }
                j.getSalida().println("FIN");
            }
        } else {
            // Broadcast del nuevo estado
            broadcastEstado();
        }
    }

    private static void broadcastEstado() {
        StringBuilder estado = new StringBuilder();
        estado.append("\n🏁 POSICIONES:\n");

        // Ordenar jugadores por posición
        List<Jugador> jugadoresOrdenados = new ArrayList<>(jugadores.values());
        jugadoresOrdenados.sort((j1, j2) -> Integer.compare(j2.getPosicion(), j1.getPosicion()));

        int posicion = 1;
        for (Jugador j : jugadoresOrdenados) {
            estado.append(posicion).append(") ");
            estado.append("Jugador #").append(j.getId());
            estado.append(": ").append(j.getPosicion()).append("m");

            // Barra de progreso
            int porcentaje = (j.getPosicion() * 100) / META;
            estado.append(" [").append(crearBarraProgreso(porcentaje)).append("] ");
            estado.append(porcentaje).append("%\n");

            posicion++;
        }

        broadcastMensaje(estado.toString());
    }

    private static String crearBarraProgreso(int porcentaje) {
        int barras = porcentaje / 5; // 20 barras máximo (100/5)
        StringBuilder barra = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            if (i < barras) {
                barra.append("█");
            } else {
                barra.append("░");
            }
        }

        return barra.toString();
    }

    private static void enviarEstado(PrintWriter salida) {
        salida.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("         ESTADO DE LA CARRERA");
        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("Jugadores activos: " + jugadores.size());
        salida.println("Meta: " + META + " metros");

        List<Jugador> jugadoresOrdenados = new ArrayList<>(jugadores.values());
        jugadoresOrdenados.sort((j1, j2) -> Integer.compare(j2.getPosicion(), j1.getPosicion()));

        int pos = 1;
        for (Jugador j : jugadoresOrdenados) {
            salida.println(pos + ") Jugador #" + j.getId() + ": " + j.getPosicion() + "m");
            pos++;
        }

        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private static void broadcastMensaje(String mensaje) {
        for (Jugador jugador : jugadores.values()) {
            jugador.getSalida().println(mensaje);
        }
    }
}