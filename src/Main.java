import java.util.Scanner;

// Clase principal, coordina el menu, pide datos y llama métodos de inventario y buscador

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creacion e integracion de objetos
        Inventario inventario = new Inventario(50);
        Buscador buscador = new Buscador(inventario);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    System.out.println("--- REGISTRAR PRODUCTO ---");
                    String codigo = leerTexto(sc, "Codigo: ");
                    String nombre = leerTexto(sc, "Nombre: ");
                    int stock = leerEntero(sc, "Stock inicial: ");
                    int minimo = leerEntero(sc, "Stock minimo: ");
                    System.out.println(inventario.registrarProducto(codigo, nombre, stock, minimo));
                    break;

                case 2:
                    System.out.println("--- BUSCAR POR CODIGO ---");
                    String codBuscar = leerTexto(sc, "Codigo a buscar: ");
                    System.out.println(buscador.buscarPorCodigo(codBuscar));
                    break;

                case 3:
                    System.out.println("--- BUSQUEDA PREDICTIVA POR PREFIJO ---");
                    String prefijo = leerTexto(sc, "Escriba el inicio del nombre: ");
                    System.out.println(buscador.buscarPorPrefijo(prefijo));
                    break;

                case 4:
                    System.out.println("--- REGISTRAR ENTRADA ---");
                    String codEntrada = leerTexto(sc, "Codigo del producto: ");
                    int cantEntrada = leerEntero(sc, "Cantidad que ingresa: ");
                    System.out.println(inventario.registrarEntrada(codEntrada, cantEntrada));
                    break;

                case 5:
                    System.out.println("--- REGISTRAR SALIDA ---");
                    String codSalida = leerTexto(sc, "Codigo del producto: ");
                    int cantSalida = leerEntero(sc, "Cantidad que sale: ");
                    System.out.println(inventario.registrarSalida(codSalida, cantSalida));
                    break;

                case 6:
                    System.out.println("--- LISTA DE PRODUCTOS ---");
                    System.out.println(inventario.listarProductos());
                    break;

                case 7:
                    System.out.println("--- ALERTAS DE STOCK ---");
                    System.out.println(inventario.mostrarAlertas());
                    break;

                case 0:
                    System.out.println("Programa finalizado. Hasta pronto.");
                    break;

                default:
                    System.out.println("ERROR: opcion no valida, intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 0);

        sc.close();
    }

    // Muestra las opciones del menu
    public static void mostrarMenu() {
        System.out.println("===== INVENTARIO CON BUSQUEDA PREDICTIVA =====");
        System.out.println("1. Registrar producto");
        System.out.println("2. Buscar producto por codigo");
        System.out.println("3. Buscar productos por prefijo de nombre");
        System.out.println("4. Registrar entrada de stock");
        System.out.println("5. Registrar salida de stock");
        System.out.println("6. Listar productos");
        System.out.println("7. Mostrar alertas de stock bajo");
        System.out.println("0. Salir");
    }

    // Lee un numero entero y vuelve a pedirlo si el usuario escribe letras
    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe ingresar un numero entero.");
            }
        }
    }

    // Lee un texto y no acepta que quede vacio
    public static String leerTexto(Scanner sc, String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("ERROR: el dato no puede estar vacio.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}
