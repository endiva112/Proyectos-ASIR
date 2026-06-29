package com.enrique_gabriel.empresa_api.entity;

import jakarta.persistence.*;
/*
public class Empresa {
    private String nombre;
    private String direccion;
    private String telefono;
    private String nit;
    private Empleado empleado;

    public Empresa(String nombre, String direccion, String telefono, String nit, Empleado empleado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
        this.empleado = empleado;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}*/

//Esta es la versión JPA de empresa
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nombre;

    private String direccion;
    private String telefono;

    @Column(unique=true)
    private String nit;

    public Empresa() { }

    public Empresa(String nombre, String direccion, String telefono, String nit) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
}