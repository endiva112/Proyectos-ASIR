package org.rafandco.model;

import java.time.LocalDate;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private boolean completada;
    private LocalDate fechaCreacion;

    //region Constructores
    //Constructor para nuevas tareas
    public Tarea(String titulo, String descripcion, LocalDate fechaCreacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.completada = false;
    }

    //Constructor completo (incluyendo id), útil para reconstruir objetos desde la base de datos
    public Tarea(int id, String titulo, String descripcion, boolean completada, LocalDate fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
        this.fechaCreacion = fechaCreacion;
    }
    //endregion

    //region Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        //Validación básica
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        } else {
            this.titulo = titulo;
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return  "o Tarea " + id + " > " + titulo + "\n" +
                "\t↳ " + descripcion + "\n" +
                "\tCompletada: " + (completada ? "Sí" : "No") + "\n" +
                "\tFecha de creación: " + fechaCreacion + "\n";
    }
    //endregion
}
