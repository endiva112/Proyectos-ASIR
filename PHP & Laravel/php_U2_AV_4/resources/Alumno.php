<?php

class Alumno {
    //region atributos
    private PDO $pdo;

    private int $id;
    private string $nombre;
    private string $apellidos;
    private string $email;
    private string $direccion;
    private int $telefono;
    //endregion

    public function __construct($pdo){
        $this->pdo = $pdo;
    }

    public function insertar($nombre, $apellidos, $email, $direccion, $telefono): void {
        $this->nombre = $nombre;
        $this->apellidos = $apellidos;
        $this->email = $email;
        $this->direccion = $direccion;
        $this->telefono = $telefono;

        $querySQL = "INSERT INTO alumno (nombre, apellidos, email, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
        $insercion = ($this->pdo)->prepare($querySQL);
        $insercion->execute([
            $this->nombre,
            $this->apellidos,
            $this->email,
            $this->direccion,
            $this->telefono
        ]);
    }

    public function eliminar($idABorrar): void {
        $querySQL = "DELETE FROM alumno WHERE id = ?";
        $borrado = ($this->pdo)->prepare($querySQL);
        $borrado->execute([$idABorrar]);
    }

    public function listado(): array {
        $querySQL = "SELECT * FROM alumno";
        $resultado = $this->pdo->prepare($querySQL);
        $resultado->execute();
        $resultado = $resultado->fetchAll();
        return $resultado;
    }

    public function actualiza($nuevoNombre, $nuevoApellidos, $nuevoEmail, $nuevoDireccion, $nuevoTelefono, $id): void {
        $querySQL = "UPDATE alumno SET 
        nombre = ?, 
        apellidos = ?, 
        email = ?, 
        direccion = ?, 
        telefono = ? WHERE id = ?";
        $actualizacion = ($this->pdo)->prepare($querySQL);
        $actualizacion->execute([
            $nuevoNombre,
            $nuevoApellidos,
            $nuevoEmail,
            $nuevoDireccion,
            $nuevoTelefono,
            $id
        ]);
    }

    public function listarUno($id): array {
        $querySQL = "SELECT * FROM alumno WHERE id = ? ";
        $consulta = $this->pdo->prepare($querySQL);
        $consulta->execute([$id]);
        $resultado = $consulta->fetch();    //Para que no devuelva un array de arrays
        return $resultado;
    }
}
?>