package javalibro;

public class Act15_Compra {
    private String producto;
    private int cantidad;

    public Act15_Compra(String producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void aumentarCantidad(int nuevaCantidad) {
        this.cantidad = this.cantidad + nuevaCantidad;
    }
}
