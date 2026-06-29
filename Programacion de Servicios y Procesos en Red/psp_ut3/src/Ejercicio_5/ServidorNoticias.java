package Ejercicio_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorNoticias {
    private static Map<String, List<String>> baseDatosNoticias = new HashMap<>();

    public static void main(String[] args) {
        cargarDatosNoticias();

        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50005)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50005...");
            System.out.println("Esperando clientes...");

            while (true) {
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado!");

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

            flujoSalida.println(
                    "╔═══════════════════════════════════════╗\n" +
                    "║     BIENVENIDO A BREAKING NEWS        ║\n" +
                    "╚═══════════════════════════════════════╝");

            enviarMenu(flujoSalida);

            String opcion = flujoEntrada.readLine();
            switch (opcion.trim()) {
                case "1":
                    enviarNoticias(flujoSalida, "DEPORTES");
                    break;
                case "2":
                    enviarNoticias(flujoSalida, "TECNOLOGIA");
                    break;
                case "3":
                    enviarNoticias(flujoSalida, "ENTRETENIMIENTO");
                    break;
                case "4":
                    enviarNoticias(flujoSalida, "CIENCIA");
                    break;
                case "5":
                    flujoSalida.println("\n¡Gracias por usar Breaking News!");
                    break;
                default:
                    flujoSalida.println("\nOpción inválida");
                    break;
            }

            //cerrar conexión con el cliente
            cliente.close();
            System.out.println("Cliente desconectado.\n");

        } catch (IOException e) {
            System.err.println("Error del servidor: " + e.getMessage());
        }
    }

    public static void cargarDatosNoticias() {
        List<String> deportes = Arrays.asList(
                "⚽ Real Madrid gana la Champions League por 16ª vez",
                "🏀 Lakers ficha a nueva estrella para la próxima temporada",
                "🎾 Nadal anuncia su retiro del tenis profesional",
                "⚾ Yankees rompe récord de jonrones en una temporada"
        );
        baseDatosNoticias.put("DEPORTES", deportes);

        List<String> tecnologia = Arrays.asList(
                "💻 Apple presenta nuevo iPhone con IA integrada",
                "🤖 ChatGPT alcanza 200 millones de usuarios activos",
                "🚀 SpaceX completa primera misión a Marte",
                "⚡ Tesla anuncia batería que dura 1 millón de kilómetros"
        );
        baseDatosNoticias.put("TECNOLOGIA", tecnologia);

        List<String> entretenimiento = Arrays.asList(
                "🎬 Nueva película de Marvel rompe récords de taquilla",
                "🎵 Bad Bunny anuncia gira mundial 2026",
                "📺 Game of Thrones tendrá nueva precuela",
                "🎭 Oscar 2026: lista completa de nominados revelada"
        );
        baseDatosNoticias.put("ENTRETENIMIENTO", entretenimiento);

        List<String> ciencia = Arrays.asList(
                "🔬 Científicos descubren cura para enfermedad rara",
                "🌍 Nuevo estudio revela datos sobre cambio climático",
                "🧬 Avance en edición genética promete revolucionar medicina",
                "🪐 Telescopio James Webb detecta planetas habitables"
        );
        baseDatosNoticias.put("CIENCIA", ciencia);
    }

    private static void enviarMenu(PrintWriter flujoSalida) {
        flujoSalida.println(
                "================================\n" +
                "     CATEGORÍAS DE NOTICIAS     \n" +
                "================================\n" +
                "(1) Deportes       \n" +
                "(2) Tecnología     \n" +
                "(3) Entretenimiento\n" +
                "(4) Ciencia        \n" +
                "(5) Salir          \n" +
                "================================\n" +
                        "FIN");
    }

    private static void enviarNoticias(PrintWriter flujoSalida, String categoria) {
        flujoSalida.println(
                "═════════════════════════════════════════\n" +
                "         NOTICIAS DE " + categoria + "\n" +
                "═════════════════════════════════════════");

        List<String> noticias = baseDatosNoticias.get(categoria);

        for (String noticia : noticias) {
            flujoSalida.println(noticia);
        }

        flujoSalida.println("═════════════════════════════════════════");
        flujoSalida.println("FIN");
    }

}
