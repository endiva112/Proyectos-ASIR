package Simulacro;

public class Producto {
    private static int contador = 0;
    private int id;
    private String nombre;
    private float precio;


    public Producto(String nombre, float precio) {
        this.id = ++contador;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return (this.id + " " + this.nombre + " " + this.precio);
    }
}
