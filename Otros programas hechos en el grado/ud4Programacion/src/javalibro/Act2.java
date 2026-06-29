package javalibro;

/*
Ejercicio 2
Realiza un programa que introduzca valores aleatorios (entre 0 y 100) en un
ArrayList y que luego calcule la suma, la media, el máximo y el mínimo de esos
números. El tamaño de la lista también será aleatorio y podrá oscilar entre 10
y 20 elementos ambos inclusive.
*/


import java.util.ArrayList;
import java.util.Random;

public class Act2 {
    private ArrayList<Integer> listaNumeros = new ArrayList<>();
    Random random = new Random();

    /**
     * Introduce numeros (entre 0 y 100) en la lista de forma aleatoria
     */
    public void cargarArrayList() {
        for (int i = 0; i < crearArrayList(); i++) {
            listaNumeros.add(random.nextInt(0, 101));
        }
    }

    /**
     * Muestra el contenido de la lista
     */
    public void mostrarArrayList() {
        for (int numero : listaNumeros) {
            System.out.print(numero + " ");
        }
    }

    /**
     * Muestra el array y todas las cuentas que se piden hacer
     */
    public void hacerCuentas() {
        mostrarArrayList();
        System.out.println();
        System.out.println("La suma de los elementos de la lista es: " + suma());
        System.out.println("La media de los elementos de la lista es: " + media());
        System.out.println("El máximo de los elementos de la lista es: " + maximo());
        System.out.println("El mínimo de los elementos de la lista es: " + minimo());
    }

    /**
     * Hace la suma de todos los elementos de la lista
     * @return
     */
    private int suma() {
        int aux = 0;
        for (int numero : listaNumeros) {
            aux += numero;
        }
        return aux;
    }

    /**
     * Me devuelve la media de los numeros del array
     * @return
     */
    private float media() {
        return (float) (suma() / listaNumeros.size());
    }

    /**
     * Devuelve el numero más pequeño de la lista
     * @return
     */
    private int minimo() {
        int aux = listaNumeros.getFirst();
        for (int numero : listaNumeros) {
            if (numero < aux) {
                aux = numero;
            }
        }
        return aux;
    }

    /**
     * Devuelve el número más grande de la lista
     * @return
     */
    private int maximo() {
        int aux = listaNumeros.getFirst();
        for (int numero : listaNumeros) {
            if (numero > aux) {
                aux = numero;
            }
        }
        return aux;
    }

    /**
     * Me devuelve un número entre 10 y 20 para crear el tamaño del arraylist
     * @return
     */
    private int crearArrayList() {
        return random.nextInt(10, 21);
    }
}
