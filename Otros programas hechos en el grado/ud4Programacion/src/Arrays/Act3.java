package Arrays;

/*
Escribe un programa que, al dar como dato un array unidimensional de números
enteros, determine cuántos de ellos son positivos, cuántos negativos y cuántos
nulos.
Dato: VEC[N] (array unidimensional de tipo entero de N elementos, 1 ≤ N ≤ 100).
*/

public class Act3 {
    private int[] arrayUnidimensional;

    public Act3 (int[] array) {
        if (array.length < 1 || array.length > 100) {
            System.out.println("No puedes cargar el array");
        } else {
            this.arrayUnidimensional = array;
        }
    }

    public void clasificar() {
        int positivos = 0;
        int negativos = 0;
        int nulos = 0;
        if (arrayUnidimensional.length >= 1 && arrayUnidimensional.length <= 100) {
            for (int i = 0; i < arrayUnidimensional.length; i++) {
                if (arrayUnidimensional[i] == 0) {
                    nulos++;
                }
                if (arrayUnidimensional[i] < 0) {
                    negativos++;
                }
                if (arrayUnidimensional[i] > 0) {
                    positivos++;
                }
            }
            System.out.print("Resultados:" +
                    "\nPositivos: " + positivos +
                    "\nNegativos: " + negativos +
                    "\nNulos: " + nulos);
        }
    }
}
