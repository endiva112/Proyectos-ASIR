package javalibro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
Ejercicio 7
La máquina Eurocoin genera una moneda de curso legal cada vez que se pulsa
un botón siguiendo la siguiente pauta: o bien coincide el valor con la moneda
anteriormente generada - 1 céntimo, 2 céntimos, 5 céntimos, 10 céntimos, 25
céntimos, 50 céntimos, 1 euro o 2 euros - o bien coincide la posición – cara o
cruz. Simula, mediante un programa, la generación de 6 monedas aleatorias
siguiendo la pauta correcta. Cada moneda generada debe ser una instancia de
la clase Moneda y la secuencia se debe ir almacenando en una lista.
 */
public class Act7 {
    private final int N_MONEDAS = 6;
    ArrayList <Act7_Moneda> monedas = new ArrayList<>();  //Aquí guardo mis monedas
    Act7_Moneda monedaaux = new Act7_Moneda();
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();
    boolean auxCaraValor = rand.nextBoolean();


    public void jugar() {
        monedas.add(monedaaux);
        System.out.print(monedas.getFirst());
        for (int i = 0; i < N_MONEDAS - 1; i++) {
            if (auxCaraValor) {
                // Genero con misma cara
                monedas.add(monedaaux.generarConMismoLado(monedas.get(i).getLado()));
            } else {
                //Genero con mismo valor
                monedas.add(monedaaux.generarConMismoValor(monedas.get(i).getValor()));
            }
            sc.nextLine();  //Solo genera moneda al pulsar intro
            System.out.print(monedas.get(i + 1));
        }
        System.out.println("\nASI QUEDARIA EL ARRAY:");
        for (Act7_Moneda m : monedas) {
            System.out.println(m);
        }
    }
}
