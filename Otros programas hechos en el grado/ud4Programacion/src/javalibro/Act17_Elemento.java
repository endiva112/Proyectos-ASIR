package javalibro;

public class Act17_Elemento {
    private String nombreElemento;
    private double valorElemento;
    private int cantidadElementos;
    private double subtotalElemento;

    public Act17_Elemento(String nombreElemento, double valorElemento, int cantidadElementos) {
        this.nombreElemento = nombreElemento;
        this.valorElemento = valorElemento;
        this.cantidadElementos = cantidadElementos;
        this.subtotalElemento = valorElemento * cantidadElementos;
    }

    public double getSubtotalElemento() {
        return subtotalElemento;
    }

    @Override
    public String toString() {
        return nombreElemento + " PVP: " + valorElemento + " Unidades: " + cantidadElementos + " Subtotal: " + subtotalElemento;
    }
}
