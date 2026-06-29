package javalibro;

public class Act18_Elemento {
    private String nombreElemento;
    private double valorElemento;
    private int cantidadElementos;
    private double subtotalElemento;

    public Act18_Elemento(String nombreElemento, double valorElemento, int cantidadElementos) {
        this.nombreElemento = nombreElemento;
        this.valorElemento = valorElemento;
        this.cantidadElementos = cantidadElementos;
        this.subtotalElemento = valorElemento * cantidadElementos;
    }

    public int getCantidadElementos() {
        return cantidadElementos;
    }

    public void modificarCantidad(int nuevaCantidadElementos) {
        this.cantidadElementos += nuevaCantidadElementos;
        this.subtotalElemento = this.valorElemento * this.cantidadElementos;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public double getSubtotalElemento() {
        return subtotalElemento;
    }

    @Override
    public String toString() {
        return nombreElemento + " PVP: " + valorElemento + " Unidades: " + cantidadElementos + " Subtotal: " + subtotalElemento;
    }
}
