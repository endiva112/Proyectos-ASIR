package Matrices;

/*
Escribe un programa que, al recibir dos arrays bidimensionales MA y MB de
20x20 elementos cada uno, calcule la suma de ambos arrays, almacene el
resultado en otro array bidimensional e imprima, además, el resultado obtenido.
*/

public class Act1 {
    private int[][] MA;
    private int[][] MB;
    private int[][] arraySuma;

    public Act1(int[][] MA, int[][] MB) {
        this.MA = MA;
        this.MB = MB;
        this.arraySuma = new int[MA.length][MA[0].length];
    }

    public void calcularSuma() {
        for (int i = 0; i < MA.length; i++) {
            for (int j = 0; j < MA[0].length; j++) {
                this.arraySuma[i][j] = arraySuma[i][j] + MA[i][j];
            }
        }
    }

    public void imprimirResultado() {
        System.out.println("La suma de las matrices es:");
        for (int i = 0; i < arraySuma.length; i++) {
            for (int j = 0; j < arraySuma[0].length; j++) {
                System.out.print(arraySuma[i][j] + " ");
            }
            System.out.println();
        }
    }
}
