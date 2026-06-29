import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton {
    private final static Dotenv puntoenv = Dotenv.load();
    private static final String servidor = puntoenv.get("URL");
    private static final String user = puntoenv.get("USER");
    private static final String password = puntoenv.get("PASSWORD");
    private static final String db = puntoenv.get("DB");

    private static Connection conexion;

    private ConexionSingleton() {
        //Esto me permite que no se pueda instanciar este objeto
    }

    private static void conectar() throws SQLException {
        conexion = DriverManager.getConnection(servidor+db, user, password);
    }

    public static Connection getConexion() {
        try {
            if  (conexion == null || conexion.isClosed()) {
                conectar();
            }
            return conexion;
        } catch (SQLException e) {
            System.out.println("erro1");
            throw new RuntimeException(e);
        }
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
            }
        } catch (SQLException e) {
            System.out.println("erro2");
            throw new RuntimeException(e);
        }
    }
}


