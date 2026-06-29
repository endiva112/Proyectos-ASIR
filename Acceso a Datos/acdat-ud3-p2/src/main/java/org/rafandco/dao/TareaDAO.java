package org.rafandco.dao;

import org.rafandco.model.Tarea;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//No debe leer por teclado ni imprimir por pantalla; solo gestiona datos.
public class TareaDAO {

    private static Connection connectionDAO;

    public TareaDAO(Connection connection){
        connectionDAO = connection;
    }

    //region CRUD
    public void insertar(Tarea tarea) {

        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        LocalDate fechaAux = tarea.getFechaCreacion(); //Parsear a dato de tipo Date
        Date fechaCreacion = Date.valueOf(fechaAux);

        String sql = """
                    INSERT INTO tareas (titulo, descripcion, fechaCreacion)
                    VALUES (?, ?, ?)
                """;

        try (PreparedStatement stmt = connectionDAO.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, descripcion);
            stmt.setDate(3, fechaCreacion);
            stmt.executeUpdate();
            System.out.println("Inserción exitosa");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void actualizar(Tarea tarea) {

        int id = tarea.getId();
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        boolean completada = tarea.isCompletada();
        LocalDate fechaAux = tarea.getFechaCreacion(); //Parsear a dato de tipo Date
        Date fechaCreacion = Date.valueOf(fechaAux);

        String sql = """
            UPDATE tareas
            SET titulo = ?, descripcion = ?, completada = ?, fechaCreacion = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = connectionDAO.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, descripcion);
            stmt.setBoolean(3, completada);
            stmt.setDate(4, fechaCreacion);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Actualización exitosa");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void eliminar(int id) {

        String sql = "DELETE FROM tareas WHERE id = ?";

        try (PreparedStatement stmt = connectionDAO.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Eliminación exitosa");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Tarea buscarPorId(int id) {

        Tarea tareaEncontrada = null;

        String sql = "SELECT * FROM tareas WHERE id = ?";

        try (PreparedStatement stmt = connectionDAO.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet resultadoConsulta = stmt.executeQuery();
            if (resultadoConsulta.next()) {
                tareaEncontrada = resultSetToTarea(resultadoConsulta);
            }
            resultadoConsulta.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return tareaEncontrada;
    }

    public List<Tarea> listarTodas() {
        List<Tarea> listaTareas = new ArrayList<>();

        String sql = "SELECT * FROM tareas";

        try (PreparedStatement stmt = connectionDAO.prepareStatement(sql);
             ResultSet resultadoConsulta = stmt.executeQuery()) {

            while (resultadoConsulta.next()) {
                Tarea tareaEncontrada = resultSetToTarea(resultadoConsulta);
                listaTareas.add(tareaEncontrada);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return listaTareas;
    }
    //endregion

    private Tarea resultSetToTarea(ResultSet resultadoConsulta) {
        Tarea tareaEncontrada = null;
        try {
            int id = resultadoConsulta.getInt("id");
            String titulo = resultadoConsulta.getString("titulo");
            String descripcion = resultadoConsulta.getString("descripcion");
            boolean completada = resultadoConsulta.getBoolean("completada");
            Date fechaCreacionSQL = resultadoConsulta.getDate("fechaCreacion");
            LocalDate fechaCreacion = fechaCreacionSQL.toLocalDate();

            tareaEncontrada = new Tarea(id, titulo, descripcion, completada, fechaCreacion);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return tareaEncontrada;
    }
}


