package Matrices;

/*
A la clase de “Programación” del profesor Manolo asiste un grupo numeroso de
alumnos. El profesor Manolo es muy exigente y aplica cuatro exámenes durante
el semestre. Escribe un programa que resuelva lo siguiente:
a. El promedio de calificaciones de cada alumno.
b. El promedio del grupo en cada examen.
c. El examen que tuvo el mayor promedio de calificación.
Dato: ALU [N, 4] (donde ALU es un array bidimensional de tipo real de N filas
y cuatro columnas que almacena calificaciones de alumnos, 1 ≤ N ≤ 20).
*/

public class Act3 {
    private int[][] ALU;
    private int numAlumnos;

    public Act3(int[][] ALU) {
        this.ALU = ALU;
        this.numAlumnos = ALU.length;
    }

    public void calcularPromedios() {
        double[] promediosAlumnos = new double[numAlumnos];
        double[] promediosExamenes = new double[4];
        double mayorPromedio = 0;
        int examenMayorPromedio = -1;

        // Calcular el promedio de cada alumno
        for (int i = 0; i < numAlumnos; i++) {
            double suma = 0;
            for (int j = 0; j < 4; j++) {
                suma += ALU[i][j];
            }
            promediosAlumnos[i] = suma / 4;
        }

        // Calcular el promedio de cada examen
        for (int j = 0; j < 4; j++) {
            double suma = 0;
            for (int i = 0; i < numAlumnos; i++) {
                suma += ALU[i][j];
            }
            promediosExamenes[j] = suma / numAlumnos;

            // Verificar si es el mayor promedio
            if (promediosExamenes[j] > mayorPromedio) {
                mayorPromedio = promediosExamenes[j];
                examenMayorPromedio = j + 1; // +1 para que sea 1, 2, 3 o 4
            }
        }

        // Imprimir resultados
        System.out.println("Promedios de calificaciones de cada alumno:");
        for (int i = 0; i < numAlumnos; i++) {
            System.out.printf("Alumno %d: %.2f\n", i + 1, promediosAlumnos[i]);
        }

        System.out.println("\nPromedios del grupo en cada examen:");
        for (int j = 0; j < 4; j++) {
            System.out.printf("Examen %d: %.2f\n", j + 1, promediosExamenes[j]);
        }

        System.out.printf("\nEl examen con el mayor promedio es el Examen %d con un promedio de %.2f\n", examenMayorPromedio, mayorPromedio);
    }
}
