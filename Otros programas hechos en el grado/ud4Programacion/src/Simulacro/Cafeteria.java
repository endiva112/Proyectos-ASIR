package Simulacro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cafeteria {
    private static int contadorHashMap = 0;
    private ArrayList<Producto> productos;
    private HashMap<Integer, Cliente> clientes;
    private ArrayList<Pedido> pedidos;

    public Cafeteria() {
        this.productos = new ArrayList<Producto>();
        this.clientes = new HashMap<Integer, Cliente>();
        this.pedidos = new ArrayList<Pedido>();
    }

    /**
     * Metodo void, permite dar de alta un producto en mi array
     */
    public void altaProducto() {
        Scanner sc = new Scanner(System.in);
        String nombreProducto;
        float precioProducto;
        //pido al user que meta datos para crear producto y meterlo en el array
        System.out.print("Ingrese el nombre del producto: ");
        nombreProducto = sc.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        precioProducto = sc.nextFloat();
        sc.nextLine();

        //producto agregado a mi array
        this.productos.add(new Producto(nombreProducto, precioProducto));
    }

    /**
     * Metodo void, permite eliminar un producto de nuestro array
     */
    public void bajaProducto() {
        Scanner sc = new Scanner(System.in);
        int idProducto;
        listarProductos();
        System.out.print("Ingrese el id del producto que desea eliminar: ");
        idProducto = sc.nextInt();
        sc.nextLine();

        for (Producto producto : this.productos) {
            if (producto.getId() == idProducto) {
                this.productos.remove(producto);
                System.out.print("Producto borrado\n");
                sc.nextLine();
                break;
            }
        }
    }

    /**
     * Metodo void privado, imprime el contenido del arrayList de productos
     */
    public void listarProductos() {
        System.out.println("Codigo Nombre Precio");
        for (Producto producto : this.productos) {
            System.out.print(producto + "\n");
        }
    }

    /**
     * Metodo void, pide al user un id y modifica dicho producto
     */
    public void modificarProducto() {
        Scanner sc = new Scanner(System.in);
        String nombreProducto = "";
        int idProducto;
        float precioProducto = -1;
        listarProductos();
        System.out.print("Ingrese el id del producto que desea modificar: ");
        idProducto = sc.nextInt();
        sc.nextLine();

        for (Producto producto : this.productos) {
            if (producto.getId() == idProducto) {
                System.out.print("Introduzca nombre nuevo: ");
                nombreProducto = sc.nextLine();
                System.out.print("Introduzca precio nuevo: ");
                precioProducto = sc.nextFloat();
                sc.nextLine();
                producto.setNombre(nombreProducto);
                producto.setPrecio(precioProducto);
                break;
            }
        }
    }

    /**
     * Metodo para dar de alta a un cliente en el sistema.
     */
    public void altaCliente() {
        Scanner sc = new Scanner(System.in);
        String nifCliente = "";
        String nombreCliente = "";


        //pido al user que meta datos para crear cliente y meterlo en el array
        System.out.print("Ingrese el NIF del cliente: ");
        nifCliente = sc.nextLine();
        while (!comprobarNifCliente(nifCliente)) {
            System.out.print("Formato icorrecto NN.NNN.NNN-L: ");
            nifCliente = sc.nextLine();
        }

        System.out.print("Ingrese el nombre del cliente: ");
        nombreCliente = sc.nextLine();

        this.clientes.put(++contadorHashMap, new Cliente(nifCliente, nombreCliente));
        System.out.println("El cliente se ha registrado correctamente con la ID: " + contadorHashMap);
        sc.nextLine();
    }

    /**
     * metodo para dar de baja a un cliente en el sistema.
     */
    public void bajaCliente() {
        Scanner sc = new Scanner(System.in);
        int idCliente;
        String res = "N";

        System.out.println("ingrese la ID del cliente que desea eliminar: ");
        idCliente = sc.nextInt();
        sc.nextLine();
        if(clientes.containsKey(idCliente)) {
            System.out.println(this.clientes.get(idCliente));
            System.out.print("¿Deseas eliminar a este cliente...S/N?: ");
            res = sc.nextLine();
            if (res.equalsIgnoreCase("S")) {
                this.clientes.remove(idCliente);
                System.out.println("El cliente se ha eliminado correctamente del sistema.");
                sc.nextLine();
            }
        }else{
            System.out.println("El cliente no existe");
        }

    }

    /**
     * Metodo para modificar por ID, el dni y nombre completo del cliente
     */
    public void modificarCliente() {
        Scanner sc = new Scanner(System.in);
        String nuevoNombre;
        String dniCliente;
        int idCliente;

        System.out.println("Ingrese el ID del cliente que desea modificar: ");
        idCliente = sc.nextInt();
        sc.nextLine();
        if (this.clientes.containsKey(idCliente)) {
            System.out.println("Informacion del cliente a modificar: " + this.clientes.get(idCliente));
            System.out.print("Introduzca el nuevo dni del cliente: ");
            dniCliente = sc.nextLine();
            while (!comprobarNifCliente(dniCliente)) {
                System.out.println("Formato icorrecto NN.NNN.NNN-L: ");
                System.out.print("Introduzca el nuevo dni del cliente: ");
                dniCliente = sc.nextLine();
            }
            System.out.print("ingrese el nuevo nombre y apellidos del cliente: ");
            nuevoNombre = sc.nextLine();

            //this.clientes.put(idCliente, new Cliente(dniCliente, nuevoNombre));

            this.clientes.get(idCliente).setDni(dniCliente);
            this.clientes.get(idCliente).setNombre_apellidos(nuevoNombre);
        } else {
            System.out.println("la id no corresponde con ningun cliente");
        }

    }

    /**
     * metodo que sirve para comprobar el DNI del cliente
     *
     * @param nifCliente
     * @return
     */
    private boolean comprobarNifCliente(String nifCliente) {
        String regex = "^[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}+-+[A-Z]$";
        Pattern pattern = Pattern.compile(regex);

        return (pattern.matcher(nifCliente).matches());
    }

    /**
     * Método para dar de alta un pedido.
     */
    public void altaPedidos() {
        Scanner sc = new Scanner(System.in);
        int codcliente;
        System.out.println("Ingrese el codigo del cliente: ");
        codcliente = sc.nextInt();
        sc.nextLine();
        if (this.clientes.containsKey(codcliente)) {
            this.pedidos.add(new Pedido(codcliente, this.clientes.get(codcliente)));
            System.out.println("El pedido ha sido creado con exito.");
        } else {
            System.out.println("El cliente no existe");
        }
        sc.nextLine();
    }

    /**
     * Método para agregar productos al pedido.
     */
    public void agregarProductosPedido(){
        Scanner sc = new Scanner(System.in);
        String codpedido;
        String res = "N";
        int codprod;
        System.out.println("Ingrese el codigo del pedido: ");
        codpedido = sc.nextLine();
        sc.nextLine();
        for (Pedido pedido : this.pedidos) {
            if(codpedido.equals(pedido.getId())){
                if(pedido.getEstado() == Estado.PENDIENTE){
                    listarProductos();
                    do{
                        System.out.print("Ingresa el codigo del producto: ");
                        codprod = sc.nextInt();
                        sc.nextLine();
                        for(Producto producto : this.productos){
                            if(codprod == producto.getId()){
                                pedido.setProducto(producto);
                            }
                        }
                        System.out.print("Desea añadir otro producto.. S/N?");
                        res = sc.nextLine();
                    }while(!res.equalsIgnoreCase("N"));
                    pedido.setEstado(Estado.SERVIDO);
                }
            }
        }
    }

    /**
     * Método para cobrar pedido.
     */
    public void cobrarPedido(){
        Scanner sc = new Scanner(System.in);
        String codpedido;
        boolean cobrado = false;
        System.out.println("Ingrese el codigo del pedido a cobrar: ");
        codpedido = sc.nextLine();
        sc.nextLine();
        for (Pedido pedido : this.pedidos) {
            if(codpedido.equals(pedido.getId())){
                System.out.println("Total a pagar: " + pedido.getTotal());
                pedido.setEstado(Estado.COBRADO);
                cobrado = true;
            }
        }
        if(!cobrado){
            System.out.println("El pedido no existe");
        }
    }
}
