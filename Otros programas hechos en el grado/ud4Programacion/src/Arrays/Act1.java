package Arrays;

import java.util.Random;

/*
Construye un programa que, al recibir como datos un array unidimensional de
100 elementos de tipo entero y un número entero, determine cuántas veces se
encuentra este número dentro del array.
*/
public class Act1 {
    private int[] listaNumeros = new int[100];
    Random rand = new Random();

    public Act1() {}

    public void cargarLista() {
        for (int i = 0; i < listaNumeros.length; i++) {
            listaNumeros[i] = rand.nextInt(0, 101);
        }
    }

    private void imprimirLista() {
        for (int i = 0; i < listaNumeros.length; i++) {
            System.out.print(listaNumeros[i] + " ");
        }
    }

    public void buscarNumero(int numero) {
        imprimirLista();
        int contador = 0;
        for (int i = 0; i < listaNumeros.length; i++) {
            if (listaNumeros[i] == numero) {
                contador++;
            }
        }
        System.out.print("\nEl número " + numero + " se repite " + contador + " veces dentro de esta lista.");
    }

}
