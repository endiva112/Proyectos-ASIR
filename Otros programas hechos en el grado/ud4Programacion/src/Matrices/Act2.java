package Matrices;

/*
Escribe un programa que, al recibir como dato un array bidimensional (matriz)
cuadrado, determine si el mismo es simétrico. Recuerda que se considera
simétrico si cumple la siguiente condición: A[I][J] = A[J][I], siendo “I”=20 y
“J”=20.
*/

public class Act2 {
    private int[][] matriz;
    private boolean esSimetrica = true;

    public Act2(int[][] matriz) {
        this.matriz = matriz;
    }

    public void comprobarSimetria() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = i + 1; j < matriz[i].length; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    esSimetrica = false;
                    break;
                }
            }
        }
        if (esSimetrica) {
            System.out.println("La matriz es simétrica.");
        } else {
            System.out.println("La matriz no es simétrica.");
        }
    }
}
