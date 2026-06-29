package Ejercicio_10;

import java.io.PrintWriter;
import java.net.Socket;

class Jugador {
    private int id;
    private Socket socket;
    private PrintWriter salida;
    private int posicion;
    private long ultimoTurbo;
    private static final long COOLDOWN_TURBO = 5000; // 5 segundos

    public Jugador(int id, Socket socket, PrintWriter salida) {
        this.id = id;
        this.socket = socket;
        this.salida = salida;
        this.posicion = 0;
        this.ultimoTurbo = 0;
    }

    public int getId() { return id; }
    public Socket getSocket() { return socket; }
    public PrintWriter getSalida() { return salida; }
    public int getPosicion() { return posicion; }

    public void setPosicion(int posicion) { this.posicion = posicion; }

    public boolean puedeUsarTurbo() {
        return System.currentTimeMillis() - ultimoTurbo >= COOLDOWN_TURBO;
    }

    public void usarTurbo() {
        ultimoTurbo = System.currentTimeMillis();
    }

    public long getTiempoRestanteTurbo() {
        long transcurrido = System.currentTimeMillis() - ultimoTurbo;
        long restante = COOLDOWN_TURBO - transcurrido;
        return Math.max(0, restante / 1000);
    }
}