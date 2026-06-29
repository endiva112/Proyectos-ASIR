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
    <title>Dar de alta alumno</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container py-5">

        <a href="../index.php" class="btn btn-secondary mb-4">Volver al inicio</a>

        <h1 class="mb-4 text-center">Alta de Alumno</h1>

        <!-- Formulario de alta -->
        <div class="card shadow-sm">
            <div class="card-body">

                <form action="altaAlumno.php" method="POST">

                    <div class="mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Apellidos</label>
                        <input type="text" name="apellidos" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Dirección</label>
                        <input type="text" name="direccion" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Teléfono</label>
                        <input type="tel" name="telefono" class="form-control" pattern="[0-9]{9}" required>
                        <small class="text-muted">Formato esperado: 9 dígitos</small>
                    </div>

                    <button type="submit" class="btn btn-primary w-100" name="formularioEnviado">Dar de alta</button>

                </form>

            </div>
        </div>

    </div>

    <?php
        if (isset($_POST["formularioEnviado"])) {
            $nombre = $_POST["nombre"];
            $apellidos = $_POST["apellidos"];
            $email = $_POST["email"];
            $direccion = $_POST["direccion"];
            $telefono = $_POST["telefono"];

            $nuevoAlumno = new Alumno($pdo);
            $nuevoAlumno->insertar($nombre, $apellidos, $email, $direccion, $telefono);

            echo "se ha logrado";
            header("Location: " . "listadoAlumnos.php");
        }
    ?>

</body>
</html>