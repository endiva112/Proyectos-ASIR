package javalibro;

/*
Ejercicio 17
Una empresa de venta por internet de productos electrónicos nos ha encargado
implementar un carrito de la compra. Crea la clase Carrito. Al carrito se le
pueden ir agregando elementos que se guardarán en una lista, por tanto,
deberás crear la clase Elemento. Cada elemento del carrito deberá contener el
nombre del producto, su precio y la cantidad (número de unidades de dicho
producto). A continuación se muestra tanto el contenido del programa principal
como la salida que debe mostrar el programa. Los métodos a implementar se
pueden deducir del main.
*/

import java.util.ArrayList;

public class Act17_Carrito {
    private ArrayList<Act17_Elemento> miCarrito = new ArrayList<>();

    public void agrega(Act17_Elemento elemento) {
        miCarrito.add(elemento);
    }

    public int numeroDeElementos() {
        int aux = 0;
        for (Act17_Elemento elemento : miCarrito) {
            aux++;
        }
        return aux;
    }

    public double importeTotal() {
        double aux = 0;
        for (Act17_Elemento elemento : miCarrito) {
            aux += elemento.getSubtotalElemento();
        }
        return aux;
    }

    @Override
    public String toString() {
        String aux = "Contenido del carrito\n" +
                "=====================\n";
        for (Act17_Elemento elemento : miCarrito) {
            aux += elemento.toString() + "\n";
        }
        return aux;
    }
}
