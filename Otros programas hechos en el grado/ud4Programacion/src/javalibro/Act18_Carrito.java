package javalibro;

/*
Ejercicio 18
Mejora el programa anterior (en otro proyecto diferente) de tal forma que al
intentar agregar un elemento al carrito, se compruebe si ya existe el producto
y, en tal caso, se incremente el número de unidades sin añadir un nuevo
elemento. Observa que en el programa anterior, se repetía el producto “Tarjeta
SD 64Gb” dos veces en el carrito. En esta nueva versión ya no sucede esto, si
no que se incrementa el número de unidades del producto que se agrega.
El contenido del main es idéntico al ejercicio anterior.
*/

import java.util.ArrayList;

public class Act18_Carrito {
    private ArrayList<Act18_Elemento> miCarrito = new ArrayList<>();

    public void agrega(Act18_Elemento elemento) {
        boolean existeEnCarrito = false;
        for (Act18_Elemento act18_elemento : miCarrito) {
            if (act18_elemento.getNombreElemento().equals(elemento.getNombreElemento())) {
                //Por cada elemento en mi carrito, compruebo si existe ya un elemento cuyo nombre coincida con el nombre
                //del elemento que quiero añadir, si se da ese caso, simplemente se modifica, no se añade uno nuevo
                act18_elemento.modificarCantidad(elemento.getCantidadElementos());
                existeEnCarrito = true;
                break;
            }
        }
        if (!existeEnCarrito) {
            miCarrito.add(elemento);
        }
    }

    public int numeroDeElementos() {
        int aux = 0;
        for (Act18_Elemento elemento : miCarrito) {
            aux++;
        }
        return aux;
    }

    public double importeTotal() {
        double aux = 0;
        for (Act18_Elemento elemento : miCarrito) {
            aux += elemento.getSubtotalElemento();
        }
        return aux;
    }

    @Override
    public String toString() {
        String aux = "Contenido del carrito\n" +
                "=====================\n";
        for (Act18_Elemento elemento : miCarrito) {
            aux += elemento.toString() + "\n";
        }
        return aux;
    }
}
