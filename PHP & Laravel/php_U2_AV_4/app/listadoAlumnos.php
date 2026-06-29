<?php
    require "../resources/Alumno.php";
    require "../resources/BD.php";

    $miPath = realpath("../files/conexionBD.ini");
    $conexion = new BD($miPath);
    $pdo = $conexion -> conectar();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de alumnos</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container py-5">

        <a href="../index.php" class="btn btn-secondary mb-4">Volver al inicio</a>

        <h1 class="text-center mb-4">Listado de Alumnos</h1>

        <div class="card shadow-sm">
            <div class="card-body">

                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Email</th>
                            <th>Dirección</th>
                            <th>Teléfono</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php mostrarAlumnos($pdo) ?>
                    </tbody>
                </table>

            </div>
        </div>

    </div>

</body>
</html>

<?php

function mostrarAlumnos($pdo) {
$misAlumnos = new Alumno($pdo);
$listaAlumnos = $misAlumnos->listado();

foreach ($listaAlumnos as $alumno) {
    echo "<tr>
            <td>".$alumno["nombre"]."</td>
            <td>".$alumno["apellidos"]."</td>
            <td>".$alumno["email"]."</td>
            <td>".$alumno["direccion"]."</td>
            <td>".$alumno["telefono"]."</td>
            <td>
                <form action='actualizaAlumno.php' method='post' class='d-inline'>
                    <input type='hidden' name='idAlumno' value='".$alumno["id"]."'>

                    <button type='submit' name='accion' value='actualizar' class='btn btn-sm btn-primary'>
                        Actualizar
                    </button>
                </form>

                <form action='eliminarAlumno.php' method='post' class='d-inline'>
                    <input type='hidden' name='idAlumno' value='".$alumno["id"]."'>

                    <button type='submit' name='accion' value='eliminar' class='btn btn-sm btn-danger'>
                        Eliminar
                    </button>
                </form>
            </td>
        </tr>";
}
}
?>
