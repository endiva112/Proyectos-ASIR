package org.rafandco.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * SingletonConnection proporciona una única conexión JDBC reutilizable.
 */
public class SingletonConnection {

    /** Conexión única compartida. */
    private static Connection connection;

    /** Constructor privado para evitar instanciación. */
    private SingletonConnection() {}

    /**
     * Obtiene la conexión JDBC única de la aplicación.
     * Si la conexión no existe o está cerrada, crea una nueva.
     *
     * @return la conexión activa, o {@code null} si no fue posible crearla
     */
    public static synchronized Connection getConnection() {
        Map<String, String> env = Utils.getEnv();
        System.out.println(env);
        try {
            if (connection == null || connection.isClosed()) {
                // TODO: externalizar la URL, el usuario y la contraseña
                connection = DriverManager.getConnection(
                        env.get("URL"),
                        env.get("USER"),
                        env.get("PASS"));
            }
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            // TODO: reemplazar por un logger
            System.err.println("Error al obtener conexión: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Cierra la conexión compartida si está activa.
     * Después de cerrar la conexión, se establece en {@code null}
     * para permitir que se vuelva a crear si se invoca {@link #getConnection()}.
     */
    public static synchronized void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            // TODO: reemplazar por un logger
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}