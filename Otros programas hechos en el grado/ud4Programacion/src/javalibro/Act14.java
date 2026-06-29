package javalibro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
Ejercicio 14
Un supermercado de productos ecológicos nos ha pedido hacer un programa
para vender su mercancía. En esta primera versión del programa se tendrán
en cuenta los productos que se indican en la tabla junto con su precio. Los
productos se venden en bote, brick, etc. Cuando se realiza la compra, hay que
indicar el producto y el número de unidades que se compran, por ejemplo
“guisantes” si se quiere comprar un bote de guisantes y la cantidad, por
ejemplo “3” si se quieren comprar 3 botes. La compra se termina con la palabra
fin. Suponemos que el usuario no va a intentar comprar un producto que
no existe. Utiliza un diccionario para almacenar los nombres y precios de los
productos y una o varias listas para almacenar la compra que realiza el usuario.
*/
public class Act14 {
    private HashMap<String, Float> productos = new HashMap<>();
    private ArrayList<Act14_Compra> productosComprados = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Act14() {
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
                productosComprados.add(new Act14_Compra(auxProducto, auxCantidad));
            }
        } while (true);

        System.out.print("Producto | Precio | Cantidad | Subtotal\n" +
                "---------------------------------------\n");
        float subtotal;
        float total = 0;
        for (Act14_Compra compra : productosComprados) {
            subtotal = productos.get(compra.getProducto()) * compra.getCantidad();
            System.out.printf("%-8s | %6.2f | %8d | %7.2f\n",
                    compra.getProducto(),                   //Me da nombre del producto
                    productos.get(compra.getProducto()),    //Me da el precio de dicho producto
                    compra.getCantidad(),                   // Me da la cantidad
                    subtotal                                // Me calcula el subtotal
            );
            total += subtotal;
        }
        System.out.print("---------------------------------------\n" +
                "Total: " + total);
    }
}
