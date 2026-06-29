package javalibro;

import java.util.Random;

public class Act7_Moneda {
    private final String[] LADOS = {"cara", "cruz"};
    private final String[] VALORES = {"1 céntimo", "2 céntimos", "5 céntimos", "10 céntimos", "25 céntimos", "50 céntimos", "1 euro", "2 euros"};

    private String lado;
    private String valor;

    Random random = new Random();

    public Act7_Moneda() {
        this.lado = LADOS[random.nextInt(LADOS.length)];
        this.valor = VALORES[random.nextInt(VALORES.length)];
    }

    //constructor para metodos
    private Act7_Moneda(String lado, String valor) {
        this.lado = lado;
        this.valor = valor;
    }

    public Act7_Moneda generarConMismoLado(String lado) {
        lado = lado;
        valor = VALORES[random.nextInt(VALORES.length)];
        return new Act7_Moneda(lado, valor);
    }

    public Act7_Moneda generarConMismoValor(String valor) {
        lado = LADOS[random.nextInt(LADOS.length)];
        valor = valor;
        return new Act7_Moneda(lado, valor);
    }

    public String getLado() {
        return lado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor + " - " + lado;
    }
}
