package javalibro;

/*
Ejercicio 16
Realiza un programa que sepa decir la capital de un país (en caso de conocer
la respuesta) y que, además, sea capaz de aprender nuevas capitales. En
principio, el programa solo conoce las capitales de España, Portugal y Francia.
Estos datos deberán estar almacenados en un diccionario. Los datos sobre
capitales que vaya aprendiendo el programa se deben almacenar en el mismo
diccionario. El usuario sale del programa escribiendo la palabra “salir”.
*/

import java.util.HashMap;
import java.util.Scanner;

public class Act16 {
    private HashMap<String,String> diccionario = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public Act16() {
        diccionario.put("España","Madrid");
        diccionario.put("Portugal", "Lisboa");
        diccionario.put("Francia", "Paris");
    }

    private void agregarCapitales() {

    }

    public void usarDicionario() {
        String aux;
        String aux2;
        do {
            System.out.print("Escribe el nombre de un país y te diré su capital: ");
            aux = sc.nextLine();
            if (aux.equals("salir")) {
                break;
            }
            if (diccionario.containsKey(aux)) { //se encuentra en el diccionario
                System.out.print("La capital de " + aux + " es " + diccionario.get(aux)+"\n");
            } else { //no se encuentra en el diccionario
                System.out.print("No conozco la respuesta ¿cuál es la capital de " + aux + "?: ");
                aux2 = sc.nextLine();
                System.out.print("Gracias por enseñarme nuevas capitales\n");
                //Agregar nueva entrada
                diccionario.put(aux, aux2);
            }
        } while(true);
    }
}
