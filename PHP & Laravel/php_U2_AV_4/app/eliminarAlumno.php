<?php
    require "../resources/Alumno.php";
    require "../resources/BD.php";

    $miPath = realpath("../files/conexionBD.ini");
    $conexion = new BD($miPath);
    $pdo = $conexion -> conectar();

    $alumnoParaEliminar = new Alumno($pdo);
    $alumnoParaEliminar->eliminar($_POST["idAlumno"]);
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alumno eliminado</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container py-5">

        <div class="card shadow-sm mx-auto" style="max-width: 500px;">
            <div class="card-body text-center">
                <h2 class="text-success mb-4">Registro eliminado correctamente</h2>
                <a href="listadoAlumnos.php" class="btn btn-primary mt-3">Volver al listado</a>
            </div>
        </div>

    </div>

</body>
</html>
