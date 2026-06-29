<?php

class Usuario {
    private PDO $pdo;

    public function __construct(PDO $pdo){
        $this->pdo = $pdo;
    }

    public function GetUsuarios() : array{
        $querySQL = "SELECT * FROM usuarios";
        $resultado = ($this->pdo)->query($querySQL);
        $usuarios = $resultado->fetchAll();
        return $usuarios;
    }
}
?>