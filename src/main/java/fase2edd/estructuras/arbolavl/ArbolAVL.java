package fase2edd.estructuras.arbolavl;

import fase2edd.model.Producto;


public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        raiz = null;
    }

    //INSERCIÓN PÚBLICA 
    public void insertar(Producto p) {
        raiz = insertarRec(raiz, p);
    }

    private NodoAVL insertarRec(NodoAVL nodo, Producto p) {
        if (nodo == null) return new NodoAVL(p);

        // Comparación por nombre
        if (p.getNombre().compareToIgnoreCase(nodo.getDato().getNombre()) < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), p));
        } else if (p.getNombre().compareToIgnoreCase(nodo.getDato().getNombre()) > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), p));
        } else {
            // Nombre duplicado: actualiza el producto (o puedes lanzar excepción)
            nodo.setDato(p);
            return nodo;
        }

        // Actualizar altura
        nodo.setAltura(1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));

        // Balancear
        int balance = factorBalance(nodo);

        // Rotaciones
        if (balance > 1 && p.getNombre().compareToIgnoreCase(nodo.getIzquierdo().getDato().getNombre()) < 0)
            return rotacionDerecha(nodo);
        if (balance < -1 && p.getNombre().compareToIgnoreCase(nodo.getDerecho().getDato().getNombre()) > 0)
            return rotacionIzquierda(nodo);
        if (balance > 1 && p.getNombre().compareToIgnoreCase(nodo.getIzquierdo().getDato().getNombre()) > 0) {
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && p.getNombre().compareToIgnoreCase(nodo.getDerecho().getDato().getNombre()) < 0) {
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // ========== BÚSQUEDA POR NOMBRE ==========
    public Producto buscarPorNombre(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Producto buscarRec(NodoAVL nodo, String nombre) {
        if (nodo == null) return null;
        int cmp = nombre.compareToIgnoreCase(nodo.getDato().getNombre());
        if (cmp == 0) return nodo.getDato();
        if (cmp < 0) return buscarRec(nodo.getIzquierdo(), nombre);
        return buscarRec(nodo.getDerecho(), nombre);
    }

    // ========== ELIMINACIÓN POR NOMBRE ==========
    public void eliminar(String nombre) {
        raiz = eliminarRec(raiz, nombre);
    }

    private NodoAVL eliminarRec(NodoAVL nodo, String nombre) {
        if (nodo == null) return null;

        int cmp = nombre.compareToIgnoreCase(nodo.getDato().getNombre());
        if (cmp < 0)
            nodo.setIzquierdo(eliminarRec(nodo.getIzquierdo(), nombre));
        else if (cmp > 0)
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), nombre));
        else {
            // Nodo encontrado
            if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
                NodoAVL temp = (nodo.getIzquierdo() != null) ? nodo.getIzquierdo() : nodo.getDerecho();
                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else
                    nodo = temp;
            } else {
                NodoAVL sucesor = nodoMinimo(nodo.getDerecho());
                nodo.setDato(sucesor.getDato());
                nodo.setDerecho(eliminarRec(nodo.getDerecho(), sucesor.getDato().getNombre()));
            }
        }

        if (nodo == null) return null;

        nodo.setAltura(1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));
        int balance = factorBalance(nodo);

        // Rotaciones
        if (balance > 1 && factorBalance(nodo.getIzquierdo()) >= 0)
            return rotacionDerecha(nodo);
        if (balance > 1 && factorBalance(nodo.getIzquierdo()) < 0) {
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && factorBalance(nodo.getDerecho()) <= 0)
            return rotacionIzquierda(nodo);
        if (balance < -1 && factorBalance(nodo.getDerecho()) > 0) {
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // ========== RECORRIDO IN-ORDER (ordenado por nombre) ==========
    public Producto[] inOrden() {
        Producto[] arr = new Producto[contarNodos(raiz)];
        int[] idx = {0};
        inOrdenRec(raiz, arr, idx);
        return arr;
    }

    private void inOrdenRec(NodoAVL nodo, Producto[] arr, int[] idx) {
        if (nodo != null) {
            inOrdenRec(nodo.getIzquierdo(), arr, idx);
            arr[idx[0]++] = nodo.getDato();
            inOrdenRec(nodo.getDerecho(), arr, idx);
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private int factorBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.getIzquierdo()) - altura(nodo.getDerecho());
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierdo();
        NodoAVL T2 = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(T2);
        y.setAltura(Math.max(altura(y.getIzquierdo()), altura(y.getDerecho())) + 1);
        x.setAltura(Math.max(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecho();
        NodoAVL T2 = y.getIzquierdo();
        y.setIzquierdo(x);
        x.setDerecho(T2);
        x.setAltura(Math.max(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);
        y.setAltura(Math.max(altura(y.getIzquierdo()), altura(y.getDerecho())) + 1);
        return y;
    }

    private NodoAVL nodoMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.getIzquierdo() != null)
            actual = actual.getIzquierdo();
        return actual;
    }

    private int contarNodos(NodoAVL nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    // Para verificar si está vacío
    public boolean estaVacio() {
        return raiz == null;
    }
}