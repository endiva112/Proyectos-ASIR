package Arrays;

/*
Construye un programa que, al recibir como datos dos arrays unidimensionales
de tipo entero, desordenados, de N(20) y M(20) elementos respectivamente,
genere un nuevo array unidimensional ordenado en forma descendente de N+M
elementos de tipo entero, mezclando los dos primeros arrays.
*/

import java.util.Random;

public class Act7 {
    private int[] array1;
    private int[] array2;
    private int[] arrayMezclado;
    Random rand = new Random();

    public Act7() {
        this.array1 = new int[20];
        this.array2 = new int[20];
        this.arrayMezclado = new int[40];
    }

    //Que se carguen los arrays con valores con números aleatorios entre 0 y 100
    public void cargarArrays() {
        for (int i = 0; i < array1.length; i++) {
            array1[i] = rand.nextInt(0, 101);
        }
        for (int i = 0; i < array2.length; i++) {
            array2[i] = rand.nextInt(0, 101);
        }
    }

    //Mezcla los dos arrays en uno solo
    private void mezclarArrays() {
        for (int i = 0; i < array1.length - 1; i++) {
            arrayMezclado[i] = array1[i];
        }
        for (int i = 0; i < array2.length; i++) {
            arrayMezclado[array1.length + i] = array2[i];
        }
    }

    //Ordena el array mezclado en forma descendente
    public void ordenarArray() {
        mezclarArrays();
        for (int i = 0; i < arrayMezclado.length - 1; i++) {    //Ese menos 1 es más correcto, pues el número 40 no se puede comparar con el 41, pero no rompe naada
            for (int j = i + 1; j < arrayMezclado.length; j++) {
                if (arrayMezclado[i] < arrayMezclado[j]) {//Pues aquí i = 40 + 1 = 41, pero como 41 es mayor al array.length, no entra en el bucle
                    int aux = arrayMezclado[i];
                    arrayMezclado[i] = arrayMezclado[j];
                    arrayMezclado[j] = aux;
                }
            }
        }
        for (int i = 0; i < arrayMezclado.length; i++) {
            System.out.print(arrayMezclado[i] + " ");
        }
    }
}
