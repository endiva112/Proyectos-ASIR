package Ejercicio_9;

import java.io.PrintWriter;
import java.net.Socket;

class ClienteTurno {
    private Socket socket;
    private PrintWriter salida;
    private int turno;
    private boolean atendido;
    private boolean terminado;

    public ClienteTurno(Socket socket, PrintWriter salida, int turno) {
        this.socket = socket;
        this.salida = salida;
        this.turno = turno;
        this.atendido = false;
        this.terminado = false;
    }

    public Socket getSocket() { return socket; }
    public PrintWriter getSalida() { return salida; }
    public int getTurno() { return turno; }
    public boolean isAtendido() { return atendido; }
    public boolean isTerminado() { return terminado; }

    public void setAtendido(boolean atendido) { this.atendido = atendido; }
    public void setTerminado(boolean terminado) { this.terminado = terminado; }
}