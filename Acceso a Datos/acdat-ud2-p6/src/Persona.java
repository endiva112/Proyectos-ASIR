import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
    private String telefono;

    public Persona(String nombre, String apellido, int edad, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.email = email;
        this.telefono = telefono;
    }

    public Persona(String cadenaSinProcesar) {
        String[] datos = cadenaSinProcesar.split(",", 5);
        this.nombre = datos[0] != null ? datos[0] : "";
        this.apellido = datos[1] != null ? datos[1] : "";
        this.edad = Integer.parseInt(datos[2] != null ? datos[2] : "-1");
        this.email = datos[3] != null ? datos[3] : "";
        this.telefono = datos[4] != null ? datos[4] : "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}