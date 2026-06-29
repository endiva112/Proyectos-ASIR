package javalibro;

/*
Ejercicio 10
Crea un mini-diccionario español-inglés que contenga, al menos, 20 palabras
(con su correspondiente traducción). Utiliza un objeto de la clase HashMap para
almacenar las parejas de palabras. El programa pedirá una palabra en español
y dará la correspondiente traducción en inglés.
*/

import java.util.HashMap;
import java.util.Scanner;

public class Act10 {
    private HashMap<String,String> diccionario = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public Act10() {
        diccionario.put("coche", "car");
        diccionario.put("perro", "dog");
        diccionario.put("gato", "cat");
        diccionario.put("casa", "house");
        diccionario.put("libro", "book");
        diccionario.put("agua", "water");
        diccionario.put("comida", "food");
        diccionario.put("escuela", "school");
        diccionario.put("mesa", "table");
        diccionario.put("silla", "chair");
        diccionario.put("sol", "sun");
        diccionario.put("luna", "moon");
        diccionario.put("estrella", "star");
        diccionario.put("camino", "road");
        diccionario.put("árbol", "tree");
        diccionario.put("flor", "flower");
        diccionario.put("zapato", "shoe");
        diccionario.put("mano", "hand");
        diccionario.put("ojo", "eye");
        diccionario.put("pan", "bread");
    }

    public void usarDiccionario() {
        String aux;
        aux = sc.nextLine();
        if (diccionario.containsKey(aux)) {
            System.out.println(diccionario.get(aux));
        } else {
            System.out.println("Lo siento, " + aux + " no existe en el diccionario, estas son las palabras que se pueden traducir:");
            System.out.println(diccionario.keySet());
        }
    }
}
