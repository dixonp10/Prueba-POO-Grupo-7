public class Buscador {

    // El buscador conoce al inventario donde va a buscar
    private Inventario inventario;

    // Constructor: recibe el inventario como parametro
    public Buscador(Inventario inventario) {
        this.inventario = inventario;
    }

    public String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String limpio = texto.trim().toUpperCase();
        while (limpio.contains("  ")) {
            limpio = limpio.replace("  ", " ");
        }
        return limpio;
    }

    public boolean empiezaCon(String palabra, String prefijo) {
        if (prefijo.length() > palabra.length()) {
            return false;
        }
        for (int i = 0; i < prefijo.length(); i++) {
            if (palabra.charAt(i) != prefijo.charAt(i)) {
                return false; // una letra no coincide
            }
        }
        return true; // todas las letras coincidieron
    }

    /**
     * Busca un producto por su codigo usando el inventario.
     */
    public String buscarPorCodigo(String codigo) {
        Producto p = inventario.buscarPorCodigo(normalizar(codigo));
        if (p == null) {
            return "No se encontro ningun producto con el codigo " + normalizar(codigo) + ".";
        }
        return "Producto encontrado:\n" + p.mostrarDatos();
    }

    public int contarCoincidencias(String prefijo) {
        String pref = normalizar(prefijo);
        int contador = 0;
        Producto actual = inventario.getPrimero();
        while (actual != null) {
            if (empiezaCon(actual.getNombre(), pref)) {
                contador++;
            }
            actual = actual.getSiguiente();
        }
        return contador;
    }

    public String buscarPorPrefijo(String prefijo) {
        String pref = normalizar(prefijo);
        if (pref.isEmpty()) {
            return "ERROR: el prefijo no puede estar vacio.";
        }
        int total = contarCoincidencias(pref);
        if (total == 0) {
            return "No hay productos que empiecen con \"" + pref + "\".";
        }
        String texto = "Productos que empiezan con \"" + pref + "\" (" + total + "):\n";
        Producto actual = inventario.getPrimero();
        while (actual != null) {
            if (empiezaCon(actual.getNombre(), pref)) {
                texto = texto + "- " + actual.mostrarDatos() + "\n";
            }
            actual = actual.getSiguiente();
        }
        return texto;
    }
}
