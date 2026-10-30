/**
 * Clase Inventario
 * Administra los productos del almacen sin usar arreglos:
 * guarda solo el primer producto y cada producto apunta al siguiente.
 * Los productos se mantienen ordenados alfabeticamente por nombre.
 */
public class Inventario {

    // Atributos privados
    private Producto primero;       // primer producto de la cadena
    private int cantidadProductos;  // cuantos productos hay registrados
    private int capacidadMaxima;    // limite de productos permitidos

    // Constructor: recibe la capacidad maxima como parametro
    public Inventario(int capacidadMaxima) {
        this.primero = null;
        this.cantidadProductos = 0;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Producto getPrimero() {
        return primero;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    /**
     * Busca un producto por su codigo exacto recorriendo la cadena.
     * @return el producto encontrado o null si no existe
     */
    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        String codigoBuscado = codigo.trim().toUpperCase();
        Producto actual = primero;
        while (actual != null) {
            if (actual.getCodigo().equals(codigoBuscado)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Revisa si ya existe un producto con ese codigo.
     */
    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    /**
     * Inserta el producto en la posicion que le corresponde
     * segun el orden alfabetico de su nombre.
     */
    private void insertarOrdenado(Producto nuevo) {
        // Caso 1: la cadena esta vacia o el nuevo va antes del primero
        if (primero == null || nuevo.getNombre().compareTo(primero.getNombre()) < 0) {
            nuevo.setSiguiente(primero);
            primero = nuevo;
            return;
        }
        // Caso 2: se busca el producto despues del cual se debe insertar
        Producto actual = primero;
        while (actual.getSiguiente() != null
                && actual.getSiguiente().getNombre().compareTo(nuevo.getNombre()) <= 0) {
            actual = actual.getSiguiente();
        }
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    /**
     * Registra un nuevo producto validando todos los datos.
     * @return mensaje con el resultado de la operacion
     */
    public String registrarProducto(String codigo, String nombre, int stock, int minimo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR: el codigo no puede estar vacio.";
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return "ERROR: el nombre no puede estar vacio.";
        }
        if (stock < 0) {
            return "ERROR: el stock inicial no puede ser negativo.";
        }
        if (minimo < 0) {
            return "ERROR: el stock minimo no puede ser negativo.";
        }
        if (existeCodigo(codigo)) {
            return "ERROR: ya existe un producto con el codigo " + codigo.trim().toUpperCase() + ".";
        }
        if (cantidadProductos >= capacidadMaxima) {
            return "ERROR: el inventario esta lleno (maximo " + capacidadMaxima + " productos).";
        }

        // Codigo y nombre se guardan sin espacios extra y en mayusculas
        String codigoLimpio = codigo.trim().toUpperCase();
        String nombreLimpio = nombre.trim().toUpperCase();

        Producto nuevo = new Producto(codigoLimpio, nombreLimpio, stock, minimo);
        insertarOrdenado(nuevo);
        cantidadProductos++;

        String mensaje = "Producto " + codigoLimpio + " registrado correctamente.";
        if (nuevo.tieneStockBajo()) {
            mensaje = mensaje + "\nALERTA: " + nombreLimpio + " tiene existencias bajas (stock "
                    + stock + ", minimo " + minimo + ").";
        }
        return mensaje;
    }

    /**
     * Registra una entrada (reposicion) de un producto.
     */
    public String registrarEntrada(String codigo, int cantidad) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            return "ERROR: no existe un producto con el codigo " + codigo + ".";
        }
        if (!p.registrarEntrada(cantidad)) {
            return "ERROR: la cantidad de entrada debe ser mayor que cero.";
        }
        return "Entrada registrada. Nuevo stock de " + p.getNombre() + ": " + p.getStock();
    }

    /**
     * Registra una salida (venta o despacho) de un producto.
     * No permite que el stock quede negativo.
     */
    public String registrarSalida(String codigo, int cantidad) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            return "ERROR: no existe un producto con el codigo " + codigo + ".";
        }
        if (cantidad <= 0) {
            return "ERROR: la cantidad de salida debe ser mayor que cero.";
        }
        if (!p.registrarSalida(cantidad)) {
            return "ERROR: stock insuficiente. Disponible: " + p.getStock()
                    + ", solicitado: " + cantidad + ".";
        }
        String mensaje = "Salida registrada. Nuevo stock de " + p.getNombre() + ": " + p.getStock();
        if (p.tieneStockBajo()) {
            mensaje = mensaje + "\nALERTA: " + p.getNombre() + " tiene existencias bajas (stock "
                    + p.getStock() + ", minimo " + p.getMinimo() + ").";
        }
        return mensaje;
    }

    /**
     * Devuelve el listado de todos los productos (orden alfabetico).
     */
    public String listarProductos() {
        if (primero == null) {
            return "No hay productos registrados.";
        }
        String texto = "";
        int numero = 1;
        Producto actual = primero;
        while (actual != null) {
            texto = texto + numero + ". " + actual.mostrarDatos() + "\n";
            numero++;
            actual = actual.getSiguiente();
        }
        return texto;
    }

    /**
     * Cuenta cuantos productos tienen stock menor que el minimo.
     */
    public int contarStockBajo() {
        int contador = 0;
        Producto actual = primero;
        while (actual != null) {
            if (actual.tieneStockBajo()) {
                contador++;
            }
            actual = actual.getSiguiente();
        }
        return contador;
    }

    /**
     * Genera el reporte de alertas de existencias bajas.
     */
    public String mostrarAlertas() {
        int total = contarStockBajo();
        if (total == 0) {
            return "Sin alertas: todos los productos estan sobre su stock minimo.";
        }
        String texto = "ALERTAS DE EXISTENCIAS BAJAS (" + total + "):\n";
        Producto actual = primero;
        while (actual != null) {
            if (actual.tieneStockBajo()) {
                texto = texto + "- " + actual.getCodigo() + " " + actual.getNombre()
                        + ": stock " + actual.getStock() + ", minimo " + actual.getMinimo()
                        + " (faltan " + actual.unidadesFaltantes() + " unidades)\n";
            }
            actual = actual.getSiguiente();
        }
        return texto;
    }
}
