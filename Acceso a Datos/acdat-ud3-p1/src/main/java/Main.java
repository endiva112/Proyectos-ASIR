import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Crear la conexión
        Connection connection = ConexionSingleton.getConexion();

        // Secuencia de acciones de la app
        // Sentencias DDL (definition)
        Definition definition = new Definition(connection);
        definition.crearTabla();

        // Sentencias DML (Modification)
        Modification modification = new Modification(connection);
        // Inserts
        modification.insertarDatos("Lavar", "Limpiar ropa para evento", false, 1);
        modification.insertarDatos("Tender", "Secar ropa para evento", false, 2);
        modification.insertarDatos("Planchar", "Preparar ropa para evento", false, 3);

        // Consulta
        Select select = new Select(connection);
        List<Map<String, Object>> consulta1 = select.getAll();
        System.out.println(consulta1);

        // Update
        modification.actualizarDatos(1,"Lavar", "Limpiar ropa para evento", true, 1);

        // Consulta del update
        Map<String, Object> consulta2 = select.getById(1);
        System.out.println(consulta2);

        // Cerrar la conexión
        ConexionSingleton.cerrarConexion();
    }
}