package Arrays;

/*
Escribe un programa que reciba como entrada un array unidimensional de N
enteros y obtenga como salida ese mismo array, pero sin los elementos repetidos.
Dato: VEC[N] (array unidimensional de tipo entero de N elementos, 1 ≤ N ≤ 20).
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Act4 {
    private int[] arrayUnidimensional;

    public Act4(int[] array) {
        if (array.length < 1 || array.length > 20) {
            System.out.println("No puedes cargar el array");
        } else {
            this.arrayUnidimensional = array;
        }
    }

    public void eliminarRepetidos() {
        if (arrayUnidimensional.length >= 1 && arrayUnidimensional.length <= 20) {
            ArrayList<Integer> numerosUnicos = new ArrayList<>();
            for (int i = 0; i < arrayUnidimensional.length; i++) {
                if (!numerosUnicos.contains(arrayUnidimensional[i])) {
                    numerosUnicos.add(arrayUnidimensional[i]);
                }
            }

            int[] arraySinRepetidos = new int[numerosUnicos.size()];
            for (int i = 0; i < arraySinRepetidos.length; i++) {
                arraySinRepetidos[i] = numerosUnicos.get(i);
            }

            System.out.print("Resultados:" +
                    "\nArray introducirdo: " + Arrays.toString(this.arrayUnidimensional) +
                    "\nArray sin repetidos: " + Arrays.toString(arraySinRepetidos));
        }
    }
}
