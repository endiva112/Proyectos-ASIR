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
    <title>Actualizar alumno</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container py-5">

        <a href="../index.php" class="btn btn-secondary mb-4">Volver al inicio</a>

        <h1 class="text-center mb-4">Actualizar Alumno</h1>

        <div class="card shadow-sm">
            <div class="card-body">

                <form action="actualizarAlumno.php" method="POST"><!--Es otro php que se llama parecido a este, para facilitarme la lógica-->
                    <?php cargarFormulario($pdo)?>
                    <button class="btn btn-primary w-100" type="submit">Actualizar alumno</button>
                </form>

            </div>
        </div>

    </div>

</body>
</html>

<?php

function cargarFormulario($pdo) {
    $alumnoParaActualizar = new Alumno($pdo);
    $datosDelAlumno = $alumnoParaActualizar->listarUno($_POST["idAlumno"]);

    echo "<!-- Ejemplo de ID del alumno (tú lo harás hidden) -->
                    <input type='hidden' name='id' value='".$datosDelAlumno["id"]."'>

                    <div class='mb-3'>
                        <label class='form-label'>Nombre</label>
                        <input type='text' class='form-control' name='nombre' value='".$datosDelAlumno["nombre"]."' required>
                    </div>

                    <div class='mb-3'>
                        <label class='form-label'>Apellidos</label>
                        <input type='text' class='form-control' name='apellidos' value='".$datosDelAlumno["apellidos"]."' required>
                    </div>

                    <div class='mb-3'>
                        <label class='form-label'>Email</label>
                        <input type='email' class='form-control' name='email' value='".$datosDelAlumno["email"]."' required>
                    </div>

                    <div class='mb-3'>
                        <label class='form-label'>Dirección</label>
                        <input type='text' class='form-control' name='direccion' value='".$datosDelAlumno["direccion"]."' required>
                    </div>

                    <div class='mb-3'>
                        <label class='form-label'>Teléfono</label>
                        <input type='tel' class='form-control' name='telefono' value='".$datosDelAlumno["telefono"]."' pattern='[0-9]{9}' required>
                    </div>";
}
?>
