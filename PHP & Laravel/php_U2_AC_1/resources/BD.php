<?php

class BD {

    private string $dbProvider;
    private string $url;
    private string $dbName;
    private string $dbCharset;
    private string $dbUser;
    private string $dbUserPassword;

    private string $ficheroIni;

    public function __construct($ficheroIni){
        $this->ficheroIni = $ficheroIni;
        $parametros = $this->leerArchivo($this->ficheroIni);
        $this->dbProvider = $parametros["PROVIDER"];
        $this->url = $parametros["URL"];
        $this->dbName = $parametros["DBNAME"];
        $this->dbCharset = $parametros["CHARSET"];
        $this->dbUser = $parametros["USER"];
        $this->dbUserPassword = $parametros["PASS"];
    }

    private function leerArchivo(string $ficheroIni): array {
        $parametros = [];

        if (file_exists($ficheroIni)) {
            if (is_readable($ficheroIni)) {
                $contenidoFichero = fopen($ficheroIni, "r");
                while (($linea = fgets($contenidoFichero)) != false) {
                    $linea = trim($linea);
                    $lineaSegmentada = explode("=",$linea,2);
                    $clave = $lineaSegmentada[0];
                    $valor = $lineaSegmentada[1];
                    $parametros[$clave] = $valor;
                }
                fclose($contenidoFichero);
            } else {
                throw new Exception("El fichero existe, pero el programa no cuenta con permisos para leerlo");
            }
        } else {
            throw new Exception("No se encuentra el fichero en la ruta esperada.");
        }
        return $parametros;
    }

    public function conectar(): PDO {
        $dsn = "{$this->dbProvider}:host={$this->url};dbname={$this->dbName};charset={$this->dbCharset}";
        $options = [
            //Lanza excepción si ocurre un error
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            //Dehabilita emulación de sentencias preparadas
            PDO::ATTR_EMULATE_PREPARES => false,
            //Devuelve los resultados como arrays asociativos por defecto
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC
        ];
        return new PDO($dsn, $this->dbUser, $this->dbUserPassword, $options);
    }
}
?>