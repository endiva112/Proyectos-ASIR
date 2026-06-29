<?php

class Espacio {
    private PDO $pdo;

    public function __construct($pdo){
        $this->pdo = $pdo;
    }

    public function GetEspacios() : array{
        $querySQL = "SELECT * FROM espacios";
        $resultado = ($this->pdo)->query($querySQL);
        $espacios = $resultado->fetchAll();
        return $espacios;
    }
}
?>