package Arrays;

/*
Los organizadores de un acto electoral de un país europeo solicitaron un
programa de cómputo para manejar en forma electrónica el conteo de los votos.
En la elección hay cinco candidatos, los cuales se representan con los valores del
1 al 5. Construye un programa que permita obtener el número de votos de cada
candidato. El usuario ingresa los votos de manera desorganizada, tal y como se
obtienen en una elección; el final de datos se representa con un cero. Observa la
siguiente lista de ejemplo: 2 5 5 4 3 4 4 5 1 2 4 3 1 2 4 5 0
Donde: 2 representa un voto para el candidato 2, 5 un voto para el candidato 5, y
así sucesivamente.
*/

import java.util.Scanner;

public class Act2 {
    private final int[] candidatos = new int[5];

    public void introducirVotos() {
        Scanner sc = new Scanner(System.in);
        int voto;
        do {
            System.out.print("Ingrese el candidato que fue votado (1 a 5): ");
            voto = sc.nextInt();
            if (voto > 0 && voto <= 5) {
                candidatos[voto - 1]++;
            } else if (voto == 0) {
                System.out.print("Gracias por usar el programa.");
            } else {
                System.out.println("El candidato que fue votado no existe");
            }
        } while (voto != 0);
    }

    public void imprimirVotos() {
        System.out.print("Este es el resultado de los votos: \n" +
                "Candidato 1: " + candidatos[0] + "\n" +
                "Candidato 2: " + candidatos[1] + "\n" +
                "Candidato 3: " + candidatos[2] + "\n" +
                "Candidato 4: " + candidatos[3] + "\n" +
                "Candidato 5: " + candidatos[4]);
    }
}
