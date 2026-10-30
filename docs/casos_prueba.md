# Casos de prueba — Grupo 7

**Datos base** (cargados con la opción 1 antes de cada prueba):

| Código | Nombre | Stock | Mínimo |
|---|---|---|---|
| P001 | Arroz | 20 | 5 |
| P002 | Aceite | 15 | 4 |
| P003 | Azucar | 8 | 10 |
| P004 | Leche | 30 | 6 |

Los resultados obtenidos se copiaron de la ejecución real del programa (`java -cp bin Main`) con las clases de la carpeta `src/`. Las capturas están en `docs/capturas/`.

| N.º | Funcionalidad | Entrada | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|
| 1 | **Caso de referencia:** alerta de stock bajo | Registrar P003 Azucar, stock 8, mínimo 10; luego opción 7 | Mostrar alerta de existencias bajas para AZUCAR | `ALERTA: AZUCAR tiene existencias bajas (stock 8, minimo 10).` y en opción 7: `- P003 AZUCAR: stock 8, minimo 10 (faltan 2 unidades)` | ✅ Correcto |
| 2 | Búsqueda predictiva por prefijo | Opción 3, prefijo `A`; luego prefijo `z` | Listar ACEITE, ARROZ, AZUCAR en orden alfabético; con `z` informar que no hay resultados | `Productos que empiezan con "A" (3):` ACEITE, ARROZ, AZUCAR · `No hay productos que empiecen con "Z".` | ✅ Correcto |
| 3 | Impedir código duplicado | Opción 1, código `p001` (minúsculas), nombre Fideos | Rechazar porque P001 ya existe | `ERROR: ya existe un producto con el codigo P001.` | ✅ Correcto |
| 4 | Impedir stock negativo y cantidades no positivas | Opción 5: P002 cantidad 20 (stock 15); P002 cantidad 0; P002 cantidad -3 | Rechazar las tres salidas; el stock de P002 sigue en 15 | `ERROR: stock insuficiente. Disponible: 15, solicitado: 20.` · `ERROR: la cantidad de salida debe ser mayor que cero.` (×2) | ✅ Correcto |
| 5 | Entradas y salidas con alerta | Opción 4: P003 +5; opción 7; opción 5: P004 −26; opción 7 | AZUCAR sube a 13 y deja de tener alerta; LECHE baja a 4 y genera alerta (mínimo 6) | `Nuevo stock de AZUCAR: 13` · `Sin alertas...` · `Nuevo stock de LECHE: 4` · `ALERTA: LECHE tiene existencias bajas (stock 4, minimo 6).` | ✅ Correcto |
| 6 | Búsqueda por código | Opción 2: `P004`; luego `X99` | Mostrar datos de LECHE; para X99 informar que no existe | `Codigo: P004 \| Nombre: LECHE \| Stock: 4 \| Minimo: 6 <-- STOCK BAJO` · `No se encontro ningun producto con el codigo X99.` | ✅ Correcto |
| 7 | Validación de datos y menú | Opción 1: P005 Pan stock `-4`; menú: `abc`; menú: `9` | Rechazar stock negativo, letras y opción inexistente sin cerrar el programa | `ERROR: el stock inicial no puede ser negativo.` · `ERROR: debe ingresar un numero entero.` · `ERROR: opcion no valida, intente de nuevo.` | ✅ Correcto |
| 8 | Orden alfabético automático | Registrar P1 Zanahoria (5/1), P2 Arroz (5/1), P3 Manzana (0/3); opción 6 | Listar ARROZ, MANZANA, ZANAHORIA aunque se registraron en otro orden; MANZANA con alerta | `1. ... ARROZ` · `2. ... MANZANA ... <-- STOCK BAJO` · `3. ... ZANAHORIA` | ✅ Correcto |

## Relación con las funcionalidades obligatorias

| Funcionalidad del enunciado | Casos que la verifican |
|---|---|
| 1. Registrar productos con código único, nombre, stock y mínimo | 1, 3, 7 |
| 2. Buscar por código y prefijo de nombre | 2, 6, 8 |
| 3. Registrar entradas y salidas | 4, 5 |
| 4. Impedir stock negativo y cantidades de movimiento no positivas | 4, 7 |
| 5. Mostrar alertas cuando stock sea menor que el mínimo | 1, 5 |
