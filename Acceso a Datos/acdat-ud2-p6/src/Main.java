import java.io.*;        // ObjectOutputStream / ObjectInputStream
import java.nio.file.*;  // Paths y Files
import java.util.*;      // List y ArrayList

public class Main {
    // Fichero binario donde guardar/leer la lista serializada
    private static final Path RUTA = Paths.get("src", "personas.dat");
    private static final String FICHERO = RUTA.toString();

    static void main(String[] args) {
        // Realiza las siguientes tareas, agrupa en funciones cuando veas oportuno
        //TODO: 1) Crear una lista de personas
        ArrayList<Persona> personas = new ArrayList<>();

        //TODO: 2) Añadir personas
        Persona persona1 = new Persona("Enrique", "Díaz", 22, "enrique@gmail.com", "222333444");
        Persona persona2 = new Persona("Gabriel", "Cordero", 21, "gabriel@gmail.com", "098763674");
        Persona persona3 = new Persona("David", "Vázquez", 19, "david@gmail.com", "888999333");
        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);

        //TODO: 3) Serializar la lista al fichero
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO: 4) Deserializar la lista desde el fichero
        ObjectInputStream ois;
        ArrayList<Persona> personasLeidas;
        try {
            ois = new ObjectInputStream(new FileInputStream(FICHERO));
            Object tmp =  ois.readObject();
            personasLeidas = (ArrayList<Persona>) tmp;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fichero no encontrado. " + e);
        } catch (IOException e) {
            throw new RuntimeException("" + e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO: 5) Imprimir por consola las personas deserializadas
        for (Persona persona : personasLeidas) {
            System.out.println(persona);
        }
    }
}