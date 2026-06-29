package com.enrique_gabriel.empresa_api.entity;

public class Empleado {
    private String nombre;
    private String correo;
    private String rol;
    private MovimientoDinero movimiento;

    public Empleado(String nombre, String correo, String rol, MovimientoDinero movimiento) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.movimiento = movimiento;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public MovimientoDinero getMovimiento() { return movimiento; }
    public void setMovimiento(MovimientoDinero movimiento) { this.movimiento = movimiento; }
}