package fase2edd.inventario;

import fase2edd.estructuras.arbolavl.ArbolAVL;
import fase2edd.estructuras.bplustree.ArbolBPlus;
import fase2edd.estructuras.btree.ArbolB;
import fase2edd.estructuras.hash.TablaHash;
import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.estructuras.listas.pila.Pila;
import fase2edd.model.Producto;
import fase2edd.model.ResultadoOperacion;



public class Inventario {
    // Estructuras que mantienen el inventario de UNA sucursal
    private ArbolAVL avl;           // indexado por nombre
    private TablaHash hash;         // indexado por código de barras
    private ArbolB arbolB;          // indexado por fecha de caducidad (grado 5 por defecto)
    private ArbolBPlus arbolBPlus;  // indexado por categoría (grado 5 por defecto)
    private ListaEnlazada lista;    // lista simple (para búsqueda secuencial y medición)
    private Pila pilaRollback;      // para deshacer inserciones atómicas

    public Inventario() {
        avl = new ArbolAVL();
        hash = new TablaHash();
        arbolB = new ArbolB(5);      // grado mínimo 5, máximo 10 claves por nodo
        arbolBPlus = new ArbolBPlus(5);
        lista = new ListaEnlazada();
        pilaRollback = new Pila();
    }

    // Inserción atómica: o se inserta en todas o se revierte
    public ResultadoOperacion insertarProducto(Producto p) {
        // Verificar duplicado por código de barras
        if (hash.buscar(p.getCodigoBarra()) != null) {
            return new ResultadoOperacion(false, "Código de barra duplicado: " + p.getCodigoBarra());
        }

        // Validar campos obligatorios
        if (p.getNombre() == null || p.getNombre().isEmpty()) 
            return new ResultadoOperacion(false, "El nombre no puede estar vacío");
        if (p.getCodigoBarra() == null || p.getCodigoBarra().isEmpty()) 
            return new ResultadoOperacion(false, "El código de barra no puede estar vacío");
        if (p.getCategoria() == null || p.getCategoria().isEmpty()) 
            return new ResultadoOperacion(false, "La categoría no puede estar vacía");
        if (p.getFechaCaducidad() == null || p.getFechaCaducidad().isEmpty()) 
            return new ResultadoOperacion(false, "La fecha de caducidad no puede estar vacía");

        try {
            // Insertar en cada estructura
            avl.insertar(p);                // 1. AVL (por nombre)
            hash.insertar(p);               // 2. Hash (por código)
            arbolB.insertar(p);             // 3. B (por fecha)
            arbolBPlus.insertar(p);         // 4. B+ (por categoría)
            lista.insertar(p);              // 5. Lista enlazada

            // Si todo fue exitoso, guardamos en pila de rollback (por si luego se necesita deshacer)
            pilaRollback.push(p);
            return new ResultadoOperacion(true, "Producto insertado correctamente");
        } catch (Exception e) {
            // Falló alguna inserción: hay que revertir las que sí se hicieron
            // Intentamos eliminar de las estructuras que ya recibieron el producto
            // Nota: si falla en medio, puede ser que algunas no tengan el producto; los eliminamos por código
            avl.eliminar(p.getNombre());
            hash.eliminar(p.getCodigoBarra());
            arbolB.eliminar(p.getFechaCaducidad(), p.getCodigoBarra());
            arbolBPlus.eliminar(p.getCategoria(), p.getCodigoBarra());
            lista.eliminarPorCodigo(p.getCodigoBarra());
            return new ResultadoOperacion(false, "Error en inserción atómica: " + e.getMessage());
        }
    }

    // Eliminación atómica
    public ResultadoOperacion eliminarProducto(String codigoBarra) {
        Producto p = hash.buscar(codigoBarra);
        if (p == null) {
            return new ResultadoOperacion(false, "Producto no encontrado con código: " + codigoBarra);
        }
        try {
            avl.eliminar(p.getNombre());
            hash.eliminar(codigoBarra);
            arbolB.eliminar(p.getFechaCaducidad(), codigoBarra);
            arbolBPlus.eliminar(p.getCategoria(), codigoBarra);
            lista.eliminarPorCodigo(codigoBarra);
            return new ResultadoOperacion(true, "Producto eliminado");
        } catch (Exception e) {
            return new ResultadoOperacion(false, "Error al eliminar: " + e.getMessage());
        }
    }

    // Búsquedas
    public Producto buscarPorNombre(String nombre) {
        return avl.buscarPorNombre(nombre);
    }

    public Producto buscarPorCodigo(String codigo) {
        return hash.buscar(codigo);
    }

    public ListaEnlazada buscarPorCategoria(String categoria) {
        return arbolBPlus.buscarPorCategoria(categoria);
    }

    public ListaEnlazada buscarPorRangoFechas(String fechaInicio, String fechaFin) {
        return arbolB.buscarPorRango(fechaInicio, fechaFin);
    }

    // Listado ordenado (AVL in-order)
    public Producto[] listarPorNombre() {
        return avl.inOrden();
    }

    public ListaEnlazada getLista() {
        return lista;
    }

    // Para rollback manual (deshacer última inserción)
    public Producto deshacerUltimaInsercion() {
        Producto p = pilaRollback.pop();
        if (p != null) {
            eliminarProducto(p.getCodigoBarra());
            return p;
        }
        return null;
    }

    // Getters de estructuras (para visualización)
    public ArbolAVL getAvl() { return avl; }
    public TablaHash getHash() { return hash; }
    public ArbolB getArbolB() { return arbolB; }
    public ArbolBPlus getArbolBPlus() { return arbolBPlus; }
}