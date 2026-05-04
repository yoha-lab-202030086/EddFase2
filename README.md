# Fase 2 - Sistema de Gestión de Catálogo de Productos Distribuido

**Universidad San Carlos de Guatemala**  
**Centro Universitario de Occidente**  
**Ingeniería en Ciencias y Sistemas**  
**Laboratorio de Estructura de Datos**  
**Fecha de entrega: 4 de mayo de 2026**

---

## 1. Descripción General

Sistema avanzado para la gestión de un catálogo de productos de supermercado distribuido en múltiples sucursales interconectadas.  
Integra las siguientes estructuras de datos implementadas desde cero: listas enlazadas, pilas, colas, árbol AVL, árbol B, árbol B+, tabla hash y grafos.  
Optimiza operaciones de almacenamiento, búsqueda y transferencia de productos entre sucursales, con simulación de flujo mediante colas y cálculo de rutas óptimas mediante el algoritmo de Dijkstra.

---

## 2. Requisitos del Sistema

| Requisito | Especificación |
|-----------|---------------|
| **Java** | JDK 17 o superior |
| **Maven** | 3.6+ (incluido en NetBeans) |
| **IDE recomendado** | Apache NetBeans 20+ |
| **Sistema operativo** | Windows, Linux, macOS |
| **RAM mínima** | 512 MB |
| **Espacio en disco** | 50 MB |

---

## 3. Estructura del Proyecto
Fase2Edd/
├── README.md
├── pom.xml
├── src/main/java/fase2edd/
│ ├── Fase2Edd.java
│ ├── modelo/
│ │ ├── Producto.java
│ │ ├── Sucursal.java
│ │ ├── Conexion.java
│ │ ├── EstadoProducto.java
│ │ └── ResultadoOperacion.java
│ ├── estructuras/
│ │ ├── lista/ (NodoLista, ListaEnlazada)
│ │ ├── pila/ (NodoPila, Pila)
│ │ ├── cola/ (NodoCola, Cola)
│ │ ├── arbolavl/ (NodoAVL, ArbolAVL)
│ │ ├── hash/ (NodoHash, TablaHash)
│ │ ├── btree/ (NodoB, ArbolB)
│ │ ├── bplustree/ (NodoBPlus, ArbolBPlus)
│ │ └── grafo/ (NodoGrafo, Arista, Grafo)
│ ├── inventario/ (Inventario)
│ ├── control/ (ControladorGlobal, ControladorInventario,
│ │ ControladorSucursales, ControladorTransferencias)
│ ├── servicios/ (ServicioCSV, ServicioMedicion, ServicioLog)
│ ├── simulacion/ (SimuladorDespacho)
│ ├── vista/ (VentanaPrincipal, VisualizadorGraphviz,
│ │ PanelSucursalesHelper, PanelRedSucursalesHelper,
│ │ PanelTransferenciasHelper, PanelMedicionRendimiento)
│ └── util/ (Rutas, Validaciones)
└── archivos/
├── sucursales.csv
├── conexiones.csv
└── productos.csv
---
## 4. Instrucciones de Compilación y Ejecución

### 4.1 Desde NetBeans (recomendado)
1. Abrir Apache NetBeans.
2. **File → Open Project** y seleccionar la carpeta `Fase2Edd`.
3. Clic derecho en el proyecto → **Clean and Build**.
4. Clic derecho en el proyecto → **Run** (F6).

### 4.2 Desde línea de comandos (Maven)
```bash
# Compilar
mvn clean package

# Ejecutar (usar el JAR con dependencias)
java -jar target/Fase2Edd-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## 5. Formatos de Archivos CSV
### 5.1 sucursales.csv
ID,Nombre,Ubicacion,t_ingreso,t_traspaso,t_despacho
1,Sucursal Central Xela,Quetzaltenango,10,20,15
2,Sucursal Norte Huehuetenango,Huehuetenango,12,25,18
### 5.2 conexiones.csv
OrigenID,DestinoID,Tiempo,Costo
1,2,45,35
2,1,45,35
### 5.3 productos.csv
SucursalID,Nombre,CodigoBarra,Categoria,FechaCaducidad,Marca,Precio,Stock
1,Agua con gas 500ml,7400000000420,Bebidas,2027-07-29,Nestle,5.76,82
2,Arena para gato 4kg,7400000001183,Mascotas,2027-03-21,Xela Gold,44.06,401

Nota: Todos los campos deben estar sin comillas. Las fechas en formato AAAA-MM-DD.
Los errores de carga se registran en errores.log.

# 6. Estructuras de Datos y Complejidad

| Estructura | Índice | Búsqueda | Inserción | Eliminación |
| :--- | :--- | :--- | :--- | :--- |
| **Lista Enlazada** | Secuencial | O(n) | O(1) | O(n) |
| **Árbol AVL** | Nombre | O(log n) | O(log n) | O(log n) |
| **Árbol B** | Fecha caducidad | O(log n) | O(log n) | O(log n) |
| **Árbol B+** | Categoría | O(log n) | O(log n) | O(log n) |
| **Tabla Hash** | Código de barras | O(1) promedio | O(1) promedio | O(1) promedio |
| **Grafo** | Conexiones | O(V²) Dijkstra | O(1) arista | O(1) arista |
| **Cola** | Flujo productos | O(1) frente | O(1) fin | O(1) frente |
| **Pila** | Rollback | O(1) tope | O(1) tope | O(1) tope |

## 7. Dependencias Externas

| Librería | Versión | Uso |
| :--- | :--- | :--- |
| `guru.nidi:graphviz-java` | `0.18.1` | Visualización gráfica de estructuras (no reemplaza ninguna estructura de datos) |
| `org.slf4j:slf4j-simple` | `2.0.13` | Logging interno de Graphviz |

> **Importante:** Todas las estructuras de datos (listas, pilas, colas, árboles, hash, grafos) están implementadas desde cero sin usar `java.util.*` ni librerías externas.

---

## 8. Autor

* **Estudiante:** Yoha Lopez
* **Carné:** [Su carné]
* **Curso:** Laboratorio de Estructura de Datos
* **Universidad:** Universidad de San Carlos de Guatemala - Centro Universitario de Occidente (CUNOC)
* **Fecha:** 4 de mayo de 2026





























