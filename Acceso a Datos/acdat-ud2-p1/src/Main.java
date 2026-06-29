import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    //URL del proyecto en GitHub -> https://github.com/endiva112/acdat-ud2-p1/tree/main
    public static void main(String[] args) {
        String ruta = ".";
        if (args.length >= 1) ruta = args[0];

        File fich = new File(ruta);

        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio (" + ruta + ").");
        } else {
            if (fich.isFile()) {
                System.out.println(ruta + " es un fichero.");
                tamano(fich);
            } else {
                System.out.println(ruta + " es un directorio. Contenidos:");
                File[] ficheros = fich.listFiles(); // Ojo, ficheros o directorios
                for (File f : ficheros) {
                    String textoDescr;
                    if (f.isDirectory()) {//Es directorio
                        textoDescr = "/";

                        System.out.println(permisos(f) + "(" + textoDescr + ") " + f.getName() + "  " + fecha(f));
                    } else {//Es fichero
                        textoDescr = " ";

                        System.out.println(permisos(f) + "(" + textoDescr + ") " + f.getName() + ", " + tamano(f) + " bytes  " + fecha(f));
                    }

                    if (textoDescr.equals(" ")) {
                        tamano(f);
                    }
                }
            }
        }
    }

    /**
     * Este método te muestra los permisos de los directorios y ficheros si pueden leerse r , si puede
     * escribirse w y si puede ejecutarse x
     * @param f Fichero o directorio a analizar
     * @return String - Cadena con este formato, ejemplo: "rwx"
     */
    public static String permisos (File f) {
        String read = f.canRead() ? "r" : "-";
        String write = f.canWrite() ? "w" : "-";
        String execute = f.canExecute() ? "x" : "-";
        return "\""+ read + write + execute +"\"";
    }

    /**
     * Este método retorna la última fecha de modificación del archivo
     * @param f Fichero o directorio a analizar
     * @return String - Fecha de última modifiación de un fichero o directorio
     */
    public static String fecha (File f) {
        long ultimaModificacion = f.lastModified();

        LocalDateTime fecha = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(ultimaModificacion),
                ZoneId.systemDefault()
        );

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formato.format(fecha);
    }

    /**
     * Este método retorna el tamaño en bytes de los ficheros
     * @param f Fichero o directorio a analizar
     * @return String - Tamaño en bytes
     */
    public static String tamano (File f) {
        return String.valueOf(f.length());
    }
}