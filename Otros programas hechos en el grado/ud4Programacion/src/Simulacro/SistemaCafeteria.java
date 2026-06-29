package Simulacro;

import java.sql.SQLOutput;
import java.util.Scanner;

public class SistemaCafeteria {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Cafeteria cafeteria = new Cafeteria();
        int opcion = 0;
        do {
            menu();
            System.out.print("Elija una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    cafeteria.altaProducto();
                    break;
                case 2:
                    cafeteria.bajaProducto();
                    break;
                case 3:
                    cafeteria.modificarProducto();
                    break;
                case 4:
                    cafeteria.altaCliente();
                    break;
                case 5:
                    cafeteria.bajaCliente();
                    break;
                case 6:
                    cafeteria.modificarCliente();
                    break;
                case 7:
                    cafeteria.altaPedidos();
                    break;
                case 8:
                    cafeteria.agregarProductosPedido();
                    break;
                case 9:
                    cafeteria.cobrarPedido();
                    break;
                case 10:
                    cafeteria.listarProductos();
                    sc.nextLine();
                    break;
                case 11:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción incorrecta(Entre 1-11)");
            }

        } while (opcion != 11);
    }

    static void menu() {
        System.out.println("""
                1. Alta de productos.
                2. Baja de productos.
                3. Modificación de productos.
                4. Alta de clientes.
                5. Baja de clientes.
                6. Modificación de clientes.
                7. Alta pedidos.
                8. Agregar productos a pedido.
                9. Cobrar pedidos.
                10. Lista de productos
                11. Salir.""");
    }
}
