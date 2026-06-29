package Arrays;

import java.util.Random;

/*
En un array unidimensional de tipo real se almacenan las calificaciones de un
grupo de 20 alumnos que presentaron un examen de admisión a una universidad.
Escribe un programa que calcule e imprima lo siguiente:
a. El promedio general del grupo.
b. El porcentaje de alumnos aprobados (todos aquellos alumnos cuyo puntaje
supere los 5 puntos).
c. El número de alumnos cuya calificación sea mayor o igual a 7.
*/
public class Act6 {
    private double[] calificaciones;

    public Act6() {
        this.calificaciones = new double[20];
    }

    public void agregarCalificaciones() {
        Random rand = new Random();
        for (int i = 0; i < this.calificaciones.length; i++) {
            this.calificaciones[i] = rand.nextInt(0, 11); // Genera calificaciones aleatorias entre 0 y 10
        }
    }

    public void mostrarCalificaciones() {
        System.out.println("Calificaciones:");
        for (int i = 0; i < this.calificaciones.length; i++) {
            System.out.println("Alumno " + (i + 1) + ": " + this.calificaciones[i]);
        }
    }

    public void calcularPromedio() {
        double suma = 0;
        for (double calificacion : this.calificaciones) {
            suma += calificacion;
        }
        System.out.println("El promedio del grupo es de: " + suma / this.calificaciones.length);
    }

    public void calcularPorcentajeAprobados() {
        int aprobados = 0;
        for (double calificacion : this.calificaciones) {
            if (calificacion > 5) {
                aprobados++;
            }
        }
        System.out.println("El porcentaje de alumons aprobados es del " + (((double) aprobados / this.calificaciones.length) * 100) + "%");;
    }

    public void calcularAlumnosSiete() {
        int alumnosSiete = 0;
        for (double calificacion : this.calificaciones) {
            if (calificacion >= 7) {
                alumnosSiete++;
            }
        }
        System.out.println("Hay " + alumnosSiete + " alumnos con calificación mayor o igual a 7.");
    }
}
