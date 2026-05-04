package fase2edd.estructuras.listas;

import fase2edd.model.Producto;



public class ListaEnlazada {
    private NodoLista cabeza;
    private int tamanio;

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    public void insertar(Producto p) {
        NodoLista nuevo = new NodoLista(p);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    
    public Producto buscarPorCodigo(String codigo) {
        NodoLista actual = cabeza;
        while (actual != null) {
            if (actual.getDato().getCodigoBarra().equals(codigo)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

   
    public Producto eliminarPorCodigo(String codigo) {
        if (cabeza == null) return null;

        if (cabeza.getDato().getCodigoBarra().equals(codigo)) {
            Producto eliminado = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return eliminado;
        }

        NodoLista actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().getCodigoBarra().equals(codigo)) {
                Producto eliminado = actual.getSiguiente().getDato();
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamanio--;
                return eliminado;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    
    public Producto[] listar() {
        Producto[] arr = new Producto[tamanio];
        NodoLista actual = cabeza;
        int i = 0;
        while (actual != null) {
            arr[i++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        return arr;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}