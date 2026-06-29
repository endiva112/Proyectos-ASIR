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

    //Constructor
    public Definition(Connection connection) {
        this.connection = connection;
    }

    /**
     * Crea la tabla tareas en la base de datos si esta no existe.
     * Utiliza una sentencia DDL y un Statement estándar.
     * En caso de error se captura la excepción y se imprime el stack trace.
     */
    public void crearTabla() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tareas(\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    titulo VARCHAR(150) NOT NULL,\n" +
                    "    descripcion TEXT,\n" +
                    "    completada BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                    "    prioridad SMALLINT CHECK (prioridad BETWEEN 1 AND 5)\n" +
                    ");");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}