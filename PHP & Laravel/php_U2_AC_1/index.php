<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Programa para gestionar reservas</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <?php
        require "resources/BD.php";
        require "resources/Usuario.php";
        require "resources/Espacio.php";
        require "resources/Reserva.php";

        echo "<h1>Para este ejercicio no se pide un main o similar que controle la lógica</h1>";
        echo "<h2>Inserta datos en la base de datos si quieres ver alguna salida en este index.php</h2>";

        $miPath = realpath(__DIR__ . "/files/conexionBD.ini");
        $a = new BD($miPath);
        $pdo = $a->conectar();

        $b = new Usuario($pdo);
        $usuarios = $b->GetUsuarios();
        foreach ($usuarios as $user) {
            echo $user['nombre'] . " " . $user['email'] . "<br>"; 
        }

        $c = new Espacio($pdo);
        $espacios = $c->GetEspacios();
        foreach ($espacios as $espacio) {
            echo $espacio['nombre'] . " " . $espacio['capacidad'] . "<br>"; 
        }

        $d = new Reserva($pdo);
        $reservas = $d->ListadoReservas();
        foreach ($reservas as $reserva) {
            echo $reserva['usuario_id'] . " " . $reserva['espacio_id'] . " " . $reserva['fecha_reserva'] . " " . $reserva['hora_inicio'] . 
            " " . $reserva['hora_fin'] . "<br>"; 
        }

        $quieresInsertar = 0;                       //0 es no, 1 es si
        if ($quieresInsertar == 1) {
            echo "campo insertado (en teoria) <br>";

            $idUsuario = 1;
            $idEspacio = 1;
            $fechaReserva = "2025-12-09";
            $horaInicio = "02:29:53";
            $horaFin = "06:10:00";
            $insertarReserva = new Reserva($pdo);
            $insertarReserva->SetReserva($idUsuario, $idEspacio, $fechaReserva, $horaInicio, $horaFin);
            $insertarReserva->InsertaReserva();            
        }
        
    ?>
</body>
</html>