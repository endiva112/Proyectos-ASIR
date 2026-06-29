package Ejercicio_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ServidorAhorcado {
    //lista de palabras posibles
    private static final String[] PALABRAS = {
            "PROGRAMACION", "JAVA", "SOCKET", "SERVIDOR", "CLIENTE",
            "INTERNET", "COMPUTADORA", "TECLADO", "PANTALLA", "INTERFAZ",
            "ALGORITMO", "VARIABLE", "FUNCION", "CLASE", "OBJETO"
    };

    private static final int INTENTOS_MAXIMOS = 6;

    public static void main(String[] args) {
        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50007)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50007...");
            System.out.println("Esperando clientes...");

            while (true) {
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();

                //crear un hilo para manejar este cliente
                Thread hiloCliente = new Thread(() -> atenderCliente(cliente));
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void atenderCliente(Socket cliente) {
        try {
            //declarar flujos de comunicación
            BufferedReader flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter flujoSalida = new PrintWriter(cliente.getOutputStream(), true);

            //elegir palabra aleatoria
            Random rand = new Random();
            String palabraSecreta = PALABRAS[rand.nextInt(PALABRAS.length)];

            System.out.println("Nueva partida - Palabra: " + palabraSecreta);

            //estado del juego
            char[] progreso = new char[palabraSecreta.length()];
            Arrays.fill(progreso, '_');

            Set<Character> letrasUsadas = new HashSet<>();
            int intentosRestantes = INTENTOS_MAXIMOS;

            //enviar mensaje de bienvenida
            flujoSalida.println("╔═══════════════════════════════════╗");
            flujoSalida.println("║    BIENVENIDO AL AHORCADO         ║");
            flujoSalida.println("╚═══════════════════════════════════╝");
            flujoSalida.println("Tienes " + INTENTOS_MAXIMOS + " intentos para adivinar la palabra.");
            flujoSalida.println("La palabra tiene " + palabraSecreta.length() + " letras.");
            flujoSalida.println("Escribe '/salir' para abandonar.");
            flujoSalida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            flujoSalida.println("INICIAR"); // Marcador

            //bucle del juego
            boolean juegoActivo = true;

            while (juegoActivo && intentosRestantes > 0) {
                //enviar estado actual
                enviarEstadoJuego(flujoSalida, progreso, intentosRestantes, letrasUsadas);

                //leer letra del cliente
                String input = flujoEntrada.readLine();

                if (input == null || input.equalsIgnoreCase("/salir")) {
                    flujoSalida.println("ABANDONADO");
                    System.out.println("Jugador abandonó la partida");
                    break;
                }

                //validar entrada
                if (input.length() != 1) {
                    flujoSalida.println("ERROR:Debes enviar solo UNA letra");
                    flujoSalida.println("CONTINUAR");
                    continue;
                }

                char letra = input.toUpperCase().charAt(0);

                //verificar si es una letra
                if (!Character.isLetter(letra)) {
                    flujoSalida.println("ERROR:Debes enviar una letra válida");
                    flujoSalida.println("CONTINUAR");
                    continue;
                }

                //verificar si ya fue usada
                if (letrasUsadas.contains(letra)) {
                    flujoSalida.println("ERROR:Ya usaste la letra '" + letra + "'");
                    flujoSalida.println("CONTINUAR");
                    continue;
                }

                //agregar letra a las usadas
                letrasUsadas.add(letra);

                //verificar si la letra está en la palabra
                boolean acierto = false;
                for (int i = 0; i < palabraSecreta.length(); i++) {
                    if (palabraSecreta.charAt(i) == letra) {
                        progreso[i] = letra;
                        acierto = true;
                    }
                }

                if (acierto) {
                    System.out.println("Jugador acertó: " + letra);
                    flujoSalida.println("ACIERTO:¡Bien! La letra '" + letra + "' está en la palabra.");

                    // Verificar si completó la palabra
                    if (palabraCompletada(progreso)) {
                        flujoSalida.println("GANADO:" + palabraSecreta);
                        System.out.println("Jugador ganó la partida");
                        juegoActivo = false;
                    } else {
                        flujoSalida.println("CONTINUAR");
                    }

                } else {
                    intentosRestantes--;
                    System.out.println("Jugador falló: " + letra + " (intentos restantes: " + intentosRestantes + ")");

                    flujoSalida.println("FALLO:La letra '" + letra + "' NO está en la palabra.");

                    if (intentosRestantes == 0) {
                        flujoSalida.println("PERDIDO:" + palabraSecreta);
                        System.out.println("Jugador perdió la partida");
                        juegoActivo = false;
                    } else {
                        flujoSalida.println("CONTINUAR");
                    }
                }
            }

            cliente.close();
            System.out.println("Cliente desconectado.\n");

        } catch (IOException e) {
            System.err.println("Error en la partida: " + e.getMessage());
        }
    }

    private static void enviarEstadoJuego(PrintWriter salida, char[] progreso, int intentos, Set<Character> usadas) {
        salida.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("ESTADO DEL JUEGO");
        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Mostrar palabra con espacios
        StringBuilder palabra = new StringBuilder();
        for (char c : progreso) {
            palabra.append(c).append(" ");
        }
        salida.println("Palabra: " + palabra.toString().trim());

        // Mostrar intentos restantes con dibujo
        salida.println("\nIntentos restantes: " + intentos);
        salida.println(dibujarAhorcado(6 - intentos));

        // Mostrar letras usadas
        if (!usadas.isEmpty()) {
            salida.println("\nLetras usadas: " + usadas);
        }

        salida.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        salida.println("PEDIR_LETRA"); // Marcador
    }

    private static boolean palabraCompletada(char[] progreso) {
        for (char c : progreso) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private static String dibujarAhorcado(int errores) {
        String[] etapas = {
                "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
        };

        return errores < etapas.length ? etapas[errores] : etapas[etapas.length - 1];
    }
}
