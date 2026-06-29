import java.io.*;

public class LimpiarArchivo {

    public static void main(String[] args) {

        if (args.length == 2) {
            File ficheroEntrada = new File(args[0]);
            File ficheroSalida = new File(args[1]);

            if (ficheroEntrada.exists()) {
                if (ficheroEntrada.isFile()) {
                    BufferedReader bufferReader = null;
                    BufferedWriter bufferWriter = null;
                    try {
                        bufferReader = new BufferedReader(new FileReader(ficheroEntrada));
                        bufferWriter = new BufferedWriter(new FileWriter(ficheroSalida, false));
                        String linea;
                        int n = 0; //Contador de línea

                        while ((linea = bufferReader.readLine()) != null) {

                            linea = linea.trim();
                            if (linea.startsWith("#") || linea.isEmpty()) {
                                continue;
                            }
                            n++;
                            bufferWriter.write(n + "\t" + linea);
                            bufferWriter.newLine();
                        }
                        bufferWriter.flush();

                        System.out.println("Se han escrito: " + n + " lineas en: " + ficheroSalida.getAbsolutePath());
                    } catch (IOException e) {
                        throw new RuntimeException("Error de entrada y salida ");
                    } finally {
                        if (bufferWriter != null) {
                            try {
                                bufferWriter.close();
                                bufferReader.close();
                            } catch (IOException e) {
                                System.err.println("Error al cerrar el archivo");
                            }
                        }
                    }
                } else {
                    System.err.println("El primer parámetro debe ser un archivo, no un fichero");
                }
            } else {
                System.err.println("El archivo a comprobar no existe");
            }
        } else {
            System.err.println("El programa necesita 2 argumentos, 1 entrada y 1 salida");
        }
    }
}
