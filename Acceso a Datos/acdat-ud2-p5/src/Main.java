import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Persona> personas = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static File miArchivo = new File("src/personas.csv");

    public static void main(String[] args) {
        try {
            int opcion;
            do {
                mostrarMenu();
                opcion = sc.nextInt();
                sc.nextLine();//limpiar buffer
                switch (opcion) {
                    case 1:
                        System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                        System.out.println("Estas son las personas almacenadas en el CSV:");
                        System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                        personas = cargarPersonas();

                        for (Persona persona : personas) {
                            System.out.println(persona.toString());
                        }
                        break;
                    case 2://No chequeo que metan los datos bien, si la cagan, el programa semi-explota
                            agregarPersona();
                        break;
                    case 3:
                        System.out.println("Gracias por usar el programa.");
                        break;
                    default:
                        System.out.println("Opción inválida\n");
                }
            } while (opcion != 3);
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }


        /*
        TODO:
        (Opcional) Divide las funcionalidades del main() en métodos y crea un método que pueda pedir datos por
        terminal y almacenarlos en el fichero CSV.
         */
    }

    /**
     *  Muestra un menu por terminal
     */
    public static void mostrarMenu() {
        System.out.println("- - - - - - - - - - - - - - - - ");
        System.out.println("Programa para gestionar personas");
        System.out.println("- - - - - - - - - - - - - - - - ");
        System.out.println("1. Listar personas");
        System.out.println("2. Agregar persona");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    /**
     * Función que lee un csv y retorna un arraylist de las personas en dicho fichero
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Persona> cargarPersonas() throws FileNotFoundException, IOException {
        ArrayList<Persona> personasEnCSV = new ArrayList<>();

        //System.out.println(miArchivo.getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(miArchivo));

        String linea;
        boolean cabeceraPasada = false;
        while((linea = br.readLine()) != null) {
            if (cabeceraPasada) {
                Persona persona = new Persona(linea);
                personasEnCSV.add(persona);
            } else {
                cabeceraPasada = true;
            }
        }
        br.close();
        return personasEnCSV;
    }

    public static void agregarPersona() throws FileNotFoundException, IOException {
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("Va a almacenar a una nueva persona en el CSV.");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
        System.out.print("Introduzca el nombre de la persona: ");
        String nombre = sc.nextLine();
        System.out.print("Introduzca el apellido de la persona: ");
        String apellido = sc.nextLine();
        System.out.print("Introduzca la edad de la persona: ");
        int edad = sc.nextInt();
        sc.nextLine();//limpiar buffer
        System.out.print("Introduzca el email de la persona: ");
        String email = sc.nextLine();
        System.out.print("Introduzca el teléfono de la persona: ");
        String telefono = sc.nextLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter(miArchivo, true));

        bw.write(nombre + "," +  apellido + "," + edad + "," + email + "," + telefono);
        bw.newLine();
        bw.close();
    }
}