<?php

class Reserva {

    private PDO $pdo;

    private string $idUsuario;
    private string $idEspacio;
    private string $fechaReserva;
    private string $horaInicio;
    private string $horaFin;

    public function __construct($pdo){
        $this->pdo = $pdo;
    }

    public function SetReserva($idUsuario, $idEspacio, $fechaReserva, $horaInicio, $horaFin){
        $this->idUsuario = $idUsuario;
        $this->idEspacio = $idEspacio;
        $this->fechaReserva = $fechaReserva;
        $this->horaInicio = $horaInicio;
        $this->horaFin = $horaFin;
    }

    public function InsertaReserva() {
        $querySQL = "INSERT INTO reservas (usuario_id, espacio_id, fecha_reserva, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";
        $insercion = ($this->pdo)->prepare($querySQL);
        $insercion->execute([
            $this->idUsuario,
            $this->idEspacio,
            $this->fechaReserva,
            $this->horaInicio,
            $this->horaFin
        ]);
    }

    public function ListadoReservas() : array{
        $querySQL = "SELECT * FROM reservas";
        $resultado = ($this->pdo)->query($querySQL);
        $reservas = $resultado->fetchAll();
        return $reservas;
    }
}
?>