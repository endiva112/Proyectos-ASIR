package javalibro;

/*
Realiza una nueva versión del ejercicio anterior con las siguientes mejoras: Si
algún producto se repite en diferentes líneas, se deben agrupar en una sola.
Por ejemplo, si se pide primero 1 bote de tomate y luego 3 botes de tomate, en
el extracto se debe mostrar que se han pedido 4 botes de tomate. Después de
teclear “fin”, el programa pide un código de descuento. Si el usuario introduce
el código “ECODTO”, se aplica un 10% de descuento en la compra.
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Act15 {
    private HashMap<String, Float> productos = new HashMap<>();
    private ArrayList<Act15_Compra> productosComprados = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Act15() {
        productos.put("avena", 2.21f);
        productos.put("garbanzos", 2.39f);
        productos.put("tomate", 1.59f);
        productos.put("jengibre", 3.13f);
        productos.put("quinoa", 4.50f);
        productos.put("guisantes", 1.60f);
    }

    private void mostrarProductos() {
        System.out.println("Bienvenido a nuestra tienda, estos son nuestros productos:");
        System.out.println(productos);
    }

    public void comprar() {
        String auxProducto;
        int auxCantidad;
        boolean tieneDescuento = false;

        mostrarProductos();
        do {
            System.out.print("Producto: ");
            auxProducto = sc.nextLine();
            if (auxProducto.equals("fin")) {
                break;
            }
            System.out.print("Cantidad: ");
            auxCantidad = sc.nextInt();
            sc.nextLine();

            //Si el producto existe se crea un nuevo objeto de tipo compra y se agrega a mi lista de cosas compradas
            if (productos.containsKey(auxProducto)) {
                boolean productoYaComprado = false;
                //Si ya se ha comprado ese producto no creamos otro objeto compra solo modificamos el que ya tenemos
                //Para ello recorremos nuestra lista en busqueda del item que podria estar repetido
                for (Act15_Compra compra : productosComprados) {
                    if (compra.getProducto().equals(auxProducto)) {
                        //Le paso cuantos productos extras se compraron
                        compra.aumentarCantidad(auxCantidad);
                        productoYaComprado = true;
                        break; // Ya lo encontramos, no necesitamos seguir
                    }
                }
                if (!productoYaComprado) {
                    productosComprados.add(new Act15_Compra(auxProducto, auxCantidad));
                }
            }
        } while (true);
        System.out.print("Introduzca código de descuento (INTRO si no tiene ninguno): ");
        auxProducto = sc.nextLine();

        if (auxProducto.equals("ECODTO")) {
            tieneDescuento = true;
        }

        System.out.print("Producto | Precio | Cantidad | Subtotal\n" +
                "---------------------------------------\n");
        float subtotal;
        float total = 0;
        for (Act15_Compra compra : productosComprados) {
            subtotal = productos.get(compra.getProducto()) * compra.getCantidad();
            System.out.printf("%-8s | %6.2f | %8d | %7.2f\n",
                    compra.getProducto(),                   //Me da nombre del producto
                    productos.get(compra.getProducto()),    //Me da el precio de dicho producto
                    compra.getCantidad(),                   // Me da la cantidad
                    subtotal                                // Me calcula el subtotal
            );
            total += subtotal;
        }
        if (tieneDescuento) {
            float descuento = total * 0.1f;
            System.out.print("---------------------------------------\n" +
                    "Descuento: " + descuento + "\n" +
                    "---------------------------------------\n" +
                    "Total: " + (total - descuento)
            );
        } else {
            System.out.print("---------------------------------------\n" +
                    "Total: " + total);
        }
    }
}
