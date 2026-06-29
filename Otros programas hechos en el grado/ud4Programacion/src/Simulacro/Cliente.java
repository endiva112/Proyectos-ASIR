package Simulacro;

public class Cliente {
    private String dni;
    private String nombre_apellidos;

    public Cliente(String dni, String nombre_apellidos) {
        this.dni = dni;
        this.nombre_apellidos = nombre_apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setNombre_apellidos(String nombre_apellidos) {
        this.nombre_apellidos = nombre_apellidos;
    }

    @Override
    public String toString() {
        return (this.dni + " - " + this.nombre_apellidos);
    }
}
