package javalibro;

/*
Ejercicio 1
Crea un ArrayList con los nombres de 6 compañeros de clase. A continuación,
muestra esos nombres por pantalla. Utiliza para ello un bucle for que recorra
todo el ArrayList sin usar ningún índice.
*/

import java.util.ArrayList;

public class Act1 {
    private ArrayList<String> compas = new ArrayList<>();

    /**
     * Agregar compas de 1 en 1, cada vez que se usa se agrega solo 1.
     * @param compa
     */
    public void agregarCompa(String compa) {
        this.compas.add(compa);
    }

    /**
     * Mostrar compas
     */
    public void mostrarCompas() {
        for (String compa : this.compas) {
            System.out.println(compa);
        }
    }
}
