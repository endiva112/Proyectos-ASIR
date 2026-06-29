package Arrays;

/*
Escribe un programa que almacene en un array unidimensional los primeros 30
números perfectos. Un número se considera perfecto si la suma de los divisores,
excepto él mismo, es igual al propio número. El 6, por ejemplo, es un número
perfecto.
*/
// el 6 es un número perfecto porque 1 + 2 + 3 = 6
// el 28 es un número perfecto porque 1 + 2 + 4 + 7 + 14 = 28

public class Act8 {
    private int[] numerosPerfectos;

    public Act8() {
        this.numerosPerfectos = new int[6];
    }

    public void calcularNumerosPerfectos() {
        int contador = 0;
        int numero = 1;

        while (contador < numerosPerfectos.length) {
            if (esNumeroPerfecto(numero)) {
                numerosPerfectos[contador] = numero;
                contador++;
            }
            numero++;
        }
    }

    private boolean esNumeroPerfecto(int numero) {
        int sumaDivisores = 0;

        for (int i = 1; i <= numero / 2; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }

        return sumaDivisores == numero;
    }

    public void imprimirNumerosPerfectos() {
        System.out.println("Los primeros 30 números perfectos son:");
        for (int i = 0; i < numerosPerfectos.length; i++) {
            System.out.print(numerosPerfectos[i] + " ");
        }
        System.out.println();
    }
}
