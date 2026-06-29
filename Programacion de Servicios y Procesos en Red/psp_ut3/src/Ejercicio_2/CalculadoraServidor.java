package Ejercicio_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServidor {
    public static void main(String[] args) {
        //creo el socket para mi servidor
        try (ServerSocket servidor = new ServerSocket(50002)) {
            System.out.println("Servidor iniciado y a la escucha en el puerto 50002...");
            System.out.println("Esperando clientes...");

            while (true) {//bucle infinito para que esté siempre escuchando peticiones
                //accept() se BLOQUEA aquí hasta que llegue un cliente
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado!");

                //captar info enviada por el cliente
                BufferedReader transmisionEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                //leer | interpretar transmision
                String mensajeEntrante = transmisionEntrada.readLine();
                System.out.println("Comando recibido: " + mensajeEntrante);

                //procesar el comando y obtener resultado
                String resultado = procesarComando(mensajeEntrante);

                //crear respuesta
                String mensajeSaliente = "Resultado: " + resultado;

                //enviar respuesta
                PrintWriter transmisionSalida = new PrintWriter(cliente.getOutputStream(), true);
                transmisionSalida.println(mensajeSaliente);

                //cerrar conexión con el cliente
                cliente.close();
                System.out.println("Cliente desconectado.\n");
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    //procesa el mensaje del cliente y devuelve el resultado de la cuenta como cadena de texto
    private static String procesarComando(String comando) {
        String resultado = "";
        try {
            //dividir el comando en partes: "SUMA 4 5" -> ["SUMA", "4", "5"]
            String[] partes = comando.split(" ");

            //validar que haya 3 partes
            if (partes.length != 3) {
                resultado = "Error: Formato incorrecto. Use: OPERACION NUM1 NUM2";
            }

            //segmentar el mensaje en sus partes
            String operacion = partes[0].toUpperCase();
            double num1 = Double.parseDouble(partes[1]);
            double num2 = Double.parseDouble(partes[2]);

            switch (operacion) {
                case "SUMA":
                    resultado = "" + (num1 + num2);
                    break;
                case "RESTA":
                    resultado = "" + (num1 - num2);
                    break;
                case "MULTIPLICACION":
                    resultado = "" + (num1 * num2);
                    break;
                case "DIVISION":
                    resultado = "" + (num1 / num2);
                    break;
                default:
                    return "Error: Operación desconocida. Use SUMA, RESTA, MULTIPLICACION o DIVISION";
            }
        } catch (NumberFormatException e) {
            resultado = "Error: Los operandos deben ser números válidos";
        } catch (ArithmeticException e) {
            resultado = "Error al realizar la operación: " + e.getMessage();
        } catch (Exception e) {
            resultado =  "Error desconocido: " + e.getMessage();
        }
        return resultado;
    }
}
