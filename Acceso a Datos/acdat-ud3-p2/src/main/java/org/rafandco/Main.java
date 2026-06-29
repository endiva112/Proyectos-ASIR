package org.rafandco;

import org.rafandco.db.Definition;
import org.rafandco.db.SingletonConnection;
import org.rafandco.dao.TareaDAO;
import org.rafandco.model.Tarea;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Crear la conexión
        Connection connection = SingletonConnection.getConnection();

        // Sentencias DDL (definition)
        Definition definition = new Definition(connection);
        //definition.eliminarTablaTareas();
        definition.crearTablaTareas();

        int opcion;
        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer
            switch (opcion) {
                case 1:
                    crearTarea(sc, connection);
                    break;
                case 2:
                    listarTodasLasTareas(connection);
                    break;
                case 3:
                    buscarPorId(sc, connection);
                    break;
                case 4:
                    marcarComoCompletada(sc, connection);
                    break;
                case 5:
                    eliminarTarea(sc, connection);
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 6);

        // Cerrar la conexión
        SingletonConnection.closeConnection();
    }

    private static void mostrarMenu() {
        System.out.println("\n\nMenú para gestionar tareas");
        System.out.println("==============================");
        System.out.println("1. Crear nueva tarea");
        System.out.println("2. Listar todas las tareas");
        System.out.println("3. Buscar tarea por ID");
        System.out.println("4. Marcar tarea como completada");
        System.out.println("5. Eliminar tarea");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void crearTarea(Scanner sc, Connection connection) {
        System.out.println("CREANDO NUEVA TAREA");
        System.out.println("====================");

        System.out.print("Nombre de la tarea: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción de la tarea: ");
        String descripcion = sc.nextLine();

        LocalDate fechaActual = java.time.LocalDate.now();

        Tarea nuevaTarea = new Tarea(nombre, descripcion, fechaActual);
        TareaDAO tareaDAO = new TareaDAO(connection);
        tareaDAO.insertar(nuevaTarea);
    }

    private static void listarTodasLasTareas(Connection connection) {
        System.out.println("LISTANDO TODAS LAS TAREAS");
        System.out.println("====================");

        TareaDAO tareaDAO = new TareaDAO(connection);
        List<Tarea> listaDeTareas = tareaDAO.listarTodas();

        for (Tarea tarea : listaDeTareas) {
            System.out.println(tarea);
        }
    }

    private static void buscarPorId(Scanner sc, Connection connection) {
        System.out.println("BUSCAR TAREA POR ID");
        System.out.println("====================");

        System.out.print("Indica el ID de la tarea a buscar: ");
        int idBuscado = sc.nextInt();
        sc.nextLine();

        TareaDAO tareaDAO = new TareaDAO(connection);
        Tarea tareaEncontrada = tareaDAO.buscarPorId(idBuscado);

        if (tareaEncontrada == null) {
            System.out.println("No se ha encontrado ninguna tarea con el ID indicado.");
        } else {
            System.out.println("Tarea encontrada: ");
            System.out.println(tareaEncontrada);
        }
    }

    private static void marcarComoCompletada(Scanner sc, Connection connection) {
        System.out.println("MARCAR TAREA COMO COMPLETADA");
        System.out.println("====================");

        System.out.print("Indica el ID de la tarea a marcar como completada: ");
        int idBuscado = sc.nextInt();
        sc.nextLine();

        TareaDAO tareaDAO = new TareaDAO(connection);
        Tarea tareaEncontrada = tareaDAO.buscarPorId(idBuscado);

        if (tareaEncontrada == null) {
            System.out.println("No se ha encontrado ninguna tarea con el ID indicado.");
        } else {
            tareaEncontrada.setCompletada(true);
            tareaDAO.actualizar(tareaEncontrada);
            System.out.println("Tarea marcada como completada.");
        }
    }

    private static void eliminarTarea(Scanner sc, Connection connection) {
        System.out.println("ELIMINAR TAREA");
        System.out.println("====================");

        System.out.print("Indica el ID de la tarea a eliminar: ");
        int idBuscado = sc.nextInt();
        sc.nextLine();

        TareaDAO tareaDAO = new TareaDAO(connection);
        Tarea tareaEncontrada = tareaDAO.buscarPorId(idBuscado);

        if (tareaEncontrada == null) {
            System.out.println("No se ha encontrado ninguna tarea con el ID indicado.");
        } else {
            tareaDAO.eliminar(idBuscado);
            System.out.println("Tarea eliminada correctamente.");
        }
    }
}
