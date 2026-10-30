# Prueba-POO-Grupo-7 — Inventario con búsqueda predictiva

**Universidad Técnica de Ambato** · Facultad de Ingeniería en Sistemas, Electrónica e Industrial ·
Asignatura: Algoritmos y Lógica de Programación · Prueba Práctica Integradora: POO en Java

## Integrantes

| N.º | Nombre | Usuario GitHub |
|---|---|---|
| 1 | _Francis Bonifaz_ | _francisbonifaz7-7_ |
| 2 | _Sebastian Navas_ | _sebas3g_ |
| 3 | _Kevin Punina_ | _KdavidPM_ |
| 4 | _Dixon Prado_ | _dixonp10_ |

## Objetivo

Construir una aplicación de consola en Java que permita a un almacén gestionar sus existencias (registro de productos, entradas y salidas de stock) y localizar productos por código o por el inicio de su nombre (búsqueda predictiva), mostrando alertas cuando el stock es menor que el mínimo. Se aplican clases, objetos, atributos privados, constructores, métodos con parámetros, validaciones e integración entre clases.

## Clases y métodos

| Clase | Responsabilidad | Métodos principales |
|---|---|---|
| `Producto` | Representa un producto (código, nombre, stock, mínimo) y apunta al siguiente producto | `registrarEntrada(cantidad)`, `registrarSalida(cantidad)`, `tieneStockBajo()`, `unidadesFaltantes()`, `mostrarDatos()`, getters, `getSiguiente()`, `setSiguiente(siguiente)` |
| `Inventario` | Guarda el primer producto de la cadena y aplica las reglas del negocio | `registrarProducto(...)`, `existeCodigo(codigo)`, `buscarPorCodigo(codigo)`, `insertarOrdenado(nuevo)` (privado), `registrarEntrada(codigo, cantidad)`, `registrarSalida(codigo, cantidad)`, `listarProductos()`, `contarStockBajo()`, `mostrarAlertas()` |
| `Buscador` | Búsqueda por código y búsqueda predictiva por prefijo sobre un `Inventario` | `normalizar(texto)`, `empiezaCon(palabra, prefijo)`, `buscarPorCodigo(codigo)`, `contarCoincidencias(prefijo)`, `buscarPorPrefijo(prefijo)` |
| `Main` | Menú con `Scanner`, `switch` y ciclo `do-while`; solo pide datos y llama a los métodos | `mostrarMenu()`, `leerEntero(sc, mensaje)`, `leerTexto(sc, mensaje)` |

**Relaciones.** `Inventario` guarda una referencia al primer `Producto` y cada `Producto` apunta al siguiente (atributo `siguiente`), formando una cadena de objetos; así se administran varios registros sin arreglos ni ArrayList. `Buscador` recibe el `Inventario` en su constructor y recorre esa misma cadena (asociación). `Main` crea ambos objetos y los usa. Diagrama: 


## Menú del programa

```
===== INVENTARIO CON BUSQUEDA PREDICTIVA =====
1. Registrar producto
2. Buscar producto por codigo
3. Buscar productos por prefijo de nombre
4. Registrar entrada de stock
5. Registrar salida de stock
6. Listar productos
7. Mostrar alertas de stock bajo
0. Salir
```

## Capturas

Caso de referencia (stock 8, mínimo 10 → alerta de existencias bajas):


Búsqueda predictiva por prefijo:


Validaciones de salidas (stock insuficiente, cantidad 0 y negativa):


Validación de datos de registro y del menú:


Entradas, salidas y búsqueda por código:


Listado en orden alfabético (inserción ordenada):


## Distribución de responsabilidades

| Integrante | Responsabilidad | Archivos | Commit sugerido |
|---|---|---|---|
| Integrante 1 | Clase `Producto`: atributos, constructor, getters/setter, reglas de entrada, salida y stock bajo | `src/Producto.java` | `feat: clase Producto con validacion de stock` |
| Integrante 2 | Clase `Inventario`: cadena de productos, inserción ordenada, duplicados, movimientos y alertas | `src/Inventario.java` | `feat: clase Inventario con registro, movimientos y alertas` |
| Integrante 3 | Clase `Buscador`: normalización, búsqueda por código y búsqueda predictiva por prefijo | `src/Buscador.java` | `feat: Buscador con busqueda predictiva por prefijo` |
| Integrante 4 | Clase `Main` (menú y lectura segura), pruebas y documentación | `src/Main.java`, `docs/`, `README.md` | `feat: menu principal` y `docs: casos de prueba y analisis` |
