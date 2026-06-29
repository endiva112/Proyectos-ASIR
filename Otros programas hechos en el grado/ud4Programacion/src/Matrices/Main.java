package Matrices;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //Act1
        /*
        // Crear dos matrices de 20x20
        Random rand = new Random();
        int[][] MA = new int[20][20];
        int[][] MB = new int[20][20];
        // Inicializar las matrices con valores aleatorios
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                MA[i][j] = rand.nextInt(0, 101);
                MB[i][j] = rand.nextInt(0, 101);
            }
        }
        Act1 actividad1 = new Act1(MA, MB);
        actividad1.calcularSuma();
        actividad1.imprimirResultado();*/

        //Act2
        /*
        int[][] matriz = new int[3][3];
        matriz[0][0] = 1; matriz[0][1] = 2; matriz[0][2] = 3;
        matriz[1][0] = 2; matriz[1][1] = 2; matriz[1][2] = 3;
        matriz[2][0] = 3; matriz[2][1] = 3; matriz[2][2] = 3;
        Act2 actividad2 = new Act2(matriz);
        actividad2.comprobarSimetria();*/

        //Act3
        // Crear una matriz de 20 alumnos y 4 exámenes
        Random rand = new Random();
        int[][] alumnosMatriz = new int[20][4];
        for (int i = 0; i < alumnosMatriz.length; i++) {
            for (int j = 0; j < alumnosMatriz[i].length; j++) {
                alumnosMatriz[i][j] = rand.nextInt(0, 101); // Calificaciones entre 0 y 100
            }
        }
        Act3 actividad3 = new Act3(alumnosMatriz);
        actividad3.calcularPromedios();
    }
}
