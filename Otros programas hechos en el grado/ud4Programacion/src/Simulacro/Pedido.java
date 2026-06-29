package Simulacro;

import java.util.ArrayList;

public class Pedido {
    private static int contador;
    private String id;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private Estado estado;
    private float total;


    public Pedido(int codcliente, Cliente cliente) {
        this.productos = new ArrayList<Producto>();
        this.cliente = cliente;
        this.estado = Estado.PENDIENTE;
        this.id = codcliente + "-" + (++contador);
        this.total = 0;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProducto(Producto producto) {
        this.productos.add(producto);
    }

    public float getTotal() {
        calcularTotal();
        return total;
    }

    /**
     * Calcular total del pedido.
     */
    private void calcularTotal(){
        for(Producto producto : this.productos){
            this.total += producto.getPrecio();
        }
    }
}
