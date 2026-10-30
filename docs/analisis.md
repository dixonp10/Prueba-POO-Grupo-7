# Análisis — Grupo 7: Inventario con búsqueda predictiva

## 1. Problema

Un almacén necesita controlar sus existencias. Debe poder registrar productos, aumentar o disminuir su stock y encontrar rápidamente un producto escribiendo solo el inicio de su nombre (búsqueda predictiva). Además, el sistema debe avisar cuando un producto tiene menos unidades que el mínimo establecido, para que se realice la reposición a tiempo.

## 2. Entradas

| Dato | Tipo | Regla de validación |
|---|---|---|
| Código del producto | `String` | No vacío; único (sin importar mayúsculas o espacios) |
| Nombre del producto | `String` | No vacío |
| Stock inicial | `int` | Mayor o igual a 0 |
| Stock mínimo | `int` | Mayor o igual a 0 |
| Cantidad de entrada / salida | `int` | Mayor que 0; en salidas, no mayor que el stock disponible |
| Prefijo de búsqueda | `String` | No vacío |
| Opción del menú | `int` | Número entre 0 y 7; si se escriben letras se vuelve a pedir |

## 3. Procesos

1. **Registrar producto** (`Inventario.registrarProducto`): valida los datos, comprueba que el código no exista (`existeCodigo`) y que no se supere la capacidad máxima; normaliza código y nombre a mayúsculas; crea el objeto `Producto` y lo coloca en la cadena con `insertarOrdenado`, que lo ubica según el orden alfabético del nombre. Si el stock inicial ya es menor que el mínimo, muestra la alerta.
2. **Buscar por código** (`Buscador.buscarPorCodigo` → `Inventario.buscarPorCodigo`): recorre la cadena desde `primero` con `getSiguiente()` hasta encontrar el código o llegar a `null`.
3. **Búsqueda predictiva por prefijo** (`Buscador.buscarPorPrefijo`): normaliza el texto (`normalizar`), cuenta las coincidencias (`contarCoincidencias`) y recorre la cadena mostrando los productos cuyo nombre empieza con el prefijo (`empiezaCon`). Como el inventario ya está ordenado, los resultados salen en orden alfabético sin un paso adicional de ordenamiento. `empiezaCon` compara carácter por carácter, adaptando `startsWith` de `ch6/Trie.java`.
4. **Registrar entrada** (`Inventario.registrarEntrada` → `Producto.registrarEntrada`): verifica que el producto exista y que la cantidad sea positiva; suma al stock.
5. **Registrar salida** (`Inventario.registrarSalida` → `Producto.registrarSalida`): verifica que el producto exista, que la cantidad sea positiva y que haya stock suficiente; resta del stock y, si queda por debajo del mínimo, genera la alerta.
6. **Alertas** (`Inventario.mostrarAlertas`): recorre la cadena y muestra los productos que cumplen `tieneStockBajo()` (stock < mínimo), indicando las unidades faltantes (`unidadesFaltantes`).

## 4. Salidas

Mensajes de confirmación de cada operación, mensajes `ERROR:` cuando una validación falla, el listado de productos en orden alfabético, el resultado de las búsquedas (o el mensaje de “sin resultados”) y el reporte de alertas de existencias bajas.

## 5. Decisiones de diseño

- **Sin arreglos ni ArrayList.** Para administrar varios registros, cada `Producto` tiene un atributo privado `siguiente` que apunta al producto que le sigue, e `Inventario` guarda solo el primero (`primero`). Los recorridos se hacen con `while (actual != null) { ... actual = actual.getSiguiente(); }`. Esta idea se adaptó de la clase `vertex` de `ch6/Trie.java`, donde cada nodo guarda referencias a otros nodos. El único `[]` del proyecto es `String[] args` de `main`, que Java exige.
- **Inserción ordenada.** `insertarOrdenado` es privado porque solo `Inventario` debe decidir dónde se coloca cada producto. Tiene dos casos: si la cadena está vacía o el nuevo nombre va antes del primero, el nuevo pasa a ser `primero`; en otro caso se avanza hasta encontrar su posición y se enlaza con `setSiguiente`.
- **Encapsulamiento.** Todos los atributos son `private`. `Producto` no tiene `setStock`: el stock solo cambia mediante `registrarEntrada` y `registrarSalida`, que contienen las validaciones, por lo que nunca queda negativo.
- **Main solo coordina.** `Main` lee los datos y muestra lo que devuelven los métodos. Las reglas (duplicados, cantidades, stock negativo, alertas, búsqueda) están en `Producto`, `Inventario` y `Buscador`.
- **Integración entre clases.** `Buscador` recibe el objeto `Inventario` por parámetro en su constructor y recorre la misma cadena de productos; lo que se registra en el inventario aparece inmediatamente en las búsquedas.
- **Normalización.** Códigos y nombres se guardan en mayúsculas y sin espacios sobrantes; así `p001` y `P001` se consideran el mismo código y el prefijo `ar` encuentra `ARROZ`.
- **Métodos que devuelven `String`.** Las operaciones de `Inventario` y `Buscador` devuelven un mensaje con el resultado (éxito, error o alerta), de modo que el motivo de cada rechazo queda definido en la clase de negocio y no en `Main`.
- **Lectura segura.** `leerEntero` usa `try/catch` con `Integer.parseInt` para que el programa no se detenga si el usuario escribe letras; `leerTexto` no acepta datos vacíos.
