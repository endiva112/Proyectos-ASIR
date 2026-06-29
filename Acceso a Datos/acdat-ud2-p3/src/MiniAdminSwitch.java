import java.io.File;
import java.io.IOException;

public class MiniAdminSwitch {

    public static void main(String[] args) {
        if (args.length < 3) {
            imprimirUso();
            return;
        }

        File base = new File(args[0]);
        if (!base.exists() || !base.isDirectory()) {
            System.err.println("La ruta base debe existir y ser un directorio.");
            return;
        }

        String operacion = args[1];

        try {
            switch (operacion) {
                case "borrar":
                    String rutaRel = args[2];
                    borrarSeguro(base, rutaRel);
                    break;

                case "renombrar":
                    if (args.length < 4) {
                        System.err.println("Faltan <origenRel> <destinoRel>.");
                        return;
                    }
                    String origenRel = args[2];
                    String destinoRel = args[3];
                    renombrarSeguro(base, origenRel, destinoRel);
                    break;
                
                case "permiso":
                    if (args.length < 5) {
                        System.err.println("Faltan <rutaRel> <permiso> <on|off>.");
                        return;
                    }
                    String ruta = args[2];
                    String permiso = args[3].toLowerCase();
                    String valor = args[4].toLowerCase();
                    boolean activar;

                    switch (valor) {
                        case "on":
                        case "true":
                            activar = true;
                            break;
                        case "off":
                        case "false":
                            activar = false;
                            break;
                        default:
                            System.err.println("El valor debe ser on|off o true|false.");
                            return;
                    }

                    cambiarPermiso(base, ruta, permiso, activar); // TODO implementar
                    break;

                default:
                    System.err.println("Operación no reconocida: " + operacion);
                    imprimirUso();
            }
        } catch (IOException e) {
            System.err.println("[main] IOException: " + e.getMessage());
        }
    }

    private static void imprimirUso() {
        System.err.println("Uso:");
        System.err.println("  java MiniAdminSwitch <rutaBase> borrar <rutaRel>");
        System.err.println("  java MiniAdminSwitch <rutaBase> renombrar <origenRel> <destinoRel>");
        System.err.println("  java MiniAdminSwitch <rutaBase> permiso <rutaRel> <lectura|escritura|ejecucion> <on|off>");
    }

    /**
     * Se le debe pasar la ruta donde trabajará y la ruta del item a borrar, sea carpeta o fichero
     * @param base ruta del directorio donde trabajará
     * @param rutaRel ruta del item a borrar
     * @throws IOException
     */
    private static void borrarSeguro(File base, String rutaRel) throws IOException {
        File objetivo = new File(base, rutaRel);
        try {
            if (objetivo.exists()) {
                if (objetivo.isDirectory()) {//DIRECTORIO
                    String[] items = objetivo.list();
                    if (items != null) {
                        if (items.length > 0) {
                            for (String item : items) {
                                System.out.println(" - " + item);
                            }
                            throw new IOException("Directorio no vacío");
                        } else {
                            if (objetivo.delete()) {
                                System.out.println("El directorio " + objetivo.getName() + " ha sido eliminado.");
                            }
                        }
                    } else {
                        throw new IOException("No se pudo listar");
                    }
                } else {//FICHERO
                    if (objetivo.delete()) {
                        System.out.println("El fichero " + objetivo.getName() + " ha sido eliminado.");
                    } else {
                        throw new IOException("No se pudo borrar");
                    }
                }
            } else {
                throw new IOException("No existe");
            }
        } finally {
            System.out.println("[borrar] Fin");
        }
    }

    /**
     * Se le debe pasar la ruta donde trabajará, la ruta del archivo a renombrar y la ruta de a donde lo moverás con un nombre nuevo
     * @param base ruta del directorio donde trabajará
     * @param origenRel ruta del item a renombrar
     * @param destinoRel ruta o nombre al que se renombra
     */
    private static void renombrarSeguro(File base, String origenRel, String destinoRel) {
        try {
            File origen = new File(base, origenRel);
            File destino = new File(base, destinoRel);

            if (!origen.exists()) {
                System.err.println("El archivo que intentas renombrar no existe");
            } else if (destino.exists()) {//Existe
                System.err.println("Ya existe un archivo con dicho nombre donde desea renombrar, elija otro nombre");
            } else {
                File padre = destino.getParentFile();
                /* Si padre es = a null, quiere decir que el usuario pasó como argumento la carpeta actual, el campo solo
                tiene valor si destino tiene realmente una ruta padre
                Dicho de otra forma -> (padre == null) significa que destino no tendría directorio padre*/
                if (padre != null && !padre.exists()) {//compruebo que destino TIENE directorio padre Y que este NO exista
                    System.err.println("El directorio de destino no existe");
                }
                if (origen.renameTo(destino)) {
                    System.out.println("Se ha renombrado: " + origen.getName() + " a " + destino.getName() + " con éxito.");
                } else {
                    System.err.println("No se ha podido renombrar");
                }
            }
        } catch (SecurityException e) {
            System.err.println("Error de permisos al renombrar");
        } finally {
            System.out.println("[renombrar] Fin");
        }
    }

    /**
     * Se le debe pasar la ruta donde trabajará, la ruta del item cuyos permisos se modificarán, el permiso a editar y si lo activa o desactiva
     * @param base ruta del directorio donde trabajará
     * @param rutaRel ruta del item cuyos permisos serán modificados
     * @param permiso permiso a modificar
     * @param activar on o off, on = permiso concedido, of = permiso revocado
     */
    private static void cambiarPermiso(File base, String rutaRel, String permiso, boolean activar) {
        try {
            File f = new File(base, rutaRel);
            if (!f.exists()) {
                System.err.println("El archivo que intentas eliminar no existe");
            } else {
                switch (permiso) {
                    case "lectura":
                        f.setReadable(activar);
                        break;
                    case "escritura":
                        f.setWritable(activar);
                        break;
                    case "ejecucion":
                        f.setExecutable(activar);
                        break;
                    default:
                        System.err.println("Permiso no válido");
                }
            }
        } catch (SecurityException e) {
            System.err.println("No se ha permitido eliminar el archivo");
        } finally {
            System.out.println("cambiarPermiso (" + permiso + " -> " + (activar ? "on" : "off") + ")");
            System.out.println("[cambiarPermiso] Fin");
        }
    }
}