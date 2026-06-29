import java.sql.*;
import java.util.*;

public class Select {

    private final Connection connection;

    //Constructor
    public Select(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtiene una tarea según su identificador.
     * Devuelve un mapa donde cada clave corresponde al nombre de columna
     * y cada valor al contenido de dicha columna en la fila resultante.
     *
     * @param id identificador de la tarea que se desea consultar.
     * @return mapa con los datos de la tarea encontrada o un mapa vacío si no existe.
     */
    public Map<String, Object> getById(int id)  {
        Map<String, Object> res = new HashMap<>();
        String sql = "SELECT * FROM tareas WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                res = resultSetToMap(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return res;
    }

    /**
     * Obtiene todas las tareas registradas en la base de datos.
     * Cada fila se convierte en un mapa y se agrega a una lista de resultados.
     *
     * @return lista de mapas, donde cada mapa representa una fila de la tabla tareas.
     */
    public List<Map<String, Object>> getAll()  {
        String sql = "SELECT * FROM tareas";

        List<Map<String, Object>> res = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                res.add(resultSetToMap(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return res;
    }

    /**
     * Convierte la fila actual de un ResultSet en un mapa,
     * donde las claves son nombres de columnas y los valores
     * corresponden al contenido de la columna en dicha fila.
     *
     * @param rs ResultSet posicionado sobre una fila válida.
     * @return mapa con los pares columna-valor extraídos del ResultSet.
     * @throws SQLException si ocurre un problema al acceder a los datos.
     */
    private Map<String, Object> resultSetToMap(ResultSet rs) throws SQLException {
        Map<String, Object> fila = new HashMap<>();

        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();

        for (int i = 1; i <= columnas; i++) {
            String nombreColumna = meta.getColumnLabel(i);
            Object valor = rs.getObject(i);
            fila.put(nombreColumna, valor);
        }

        return fila;
    }
}