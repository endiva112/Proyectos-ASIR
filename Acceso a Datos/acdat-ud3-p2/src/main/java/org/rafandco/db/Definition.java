package org.rafandco.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de realizar operaciones de definición de la base de datos.
 * Incluye métodos relacionados con la creación de estructuras como tablas.
 * Utiliza una conexión JDBC proporcionada desde el exterior.
 */
public class Definition {

    private final Connection connection;

    /**
     * Constructor que recibe una conexión ya establecida.
     * @param connection conexión JDBC para ejecutar sentencias DDL.
     */
    public Definition(Connection connection) {
        this.connection = connection;
    }

    public void crearTablaTareas() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS tareas(\n" +
                            "    id SERIAL PRIMARY KEY,\n" +
                            "    titulo VARCHAR(150) NOT NULL,\n" +
                            "    descripcion TEXT,\n" +
                            "    completada BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                            "    fechaCreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
                            ");"
            );
            System.out.println("Tabla 'tareas' creada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public void eliminarTablaTareas() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS tareas;");
            System.out.println("Tabla 'tareas' eliminada.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar la tabla: " + e.getMessage());
        }
    }
}