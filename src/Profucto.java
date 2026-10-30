/**
 * Clase Producto
 * Representa un producto del almacen con su codigo, nombre,
 * stock actual y stock minimo.
 *
 * El atributo "siguiente" permite enlazar un producto con otro y asi
 * guardar varios productos sin usar arreglos ni ArrayList
 * (idea tomada de la clase vertex de ch6/Trie.java, donde cada nodo
 * guarda referencias a otros nodos).
 */
public class Producto {

    // Atributos privados (encapsulamiento)
    private String codigo;
    private String nombre;
    private int stock;
    private int minimo;
    private Producto siguiente; // referencia al siguiente producto de la cadena

    // Constructor con parametros
    public Producto(String codigo, String nombre, int stock, int minimo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.minimo = minimo;
        this.siguiente = null;
    }

    // ----- Metodos get y set -----
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public int getMinimo() {
        return minimo;
    }

    public Producto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Producto siguiente) {
        this.siguiente = siguiente;
    }

    // ----- Metodos de negocio -----

    /**
     * Suma la cantidad al stock. Solo acepta cantidades positivas.
     * @param cantidad unidades que ingresan
     * @return true si se registro la entrada
     */
    public boolean registrarEntrada(int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        stock = stock + cantidad;
        return true;
    }

    /**
     * Resta la cantidad del stock. No acepta cantidades no positivas
     * ni permite que el stock quede negativo.
     * @param cantidad unidades que salen
     * @return true si se registro la salida
     */
    public boolean registrarSalida(int cantidad) {
        if (cantidad <= 0 || cantidad > stock) {
            return false;
        }
        stock = stock - cantidad;
        return true;
    }

    /**
     * Indica si el stock actual es menor que el minimo.
     */
    public boolean tieneStockBajo() {
        return stock < minimo;
    }

    /**
     * Devuelve cuantas unidades faltan para llegar al minimo.
     */
    public int unidadesFaltantes() {
        if (tieneStockBajo()) {
            return minimo - stock;
        }
        return 0;
    }

    /**
     * Devuelve los datos del producto en una sola linea.
     */
    public String mostrarDatos() {
        String texto = "Codigo: " + codigo
                + " | Nombre: " + nombre
                + " | Stock: " + stock
                + " | Minimo: " + minimo;
        if (tieneStockBajo()) {
            texto = texto + "  <-- STOCK BAJO";
        }
        return texto;
    }
}
