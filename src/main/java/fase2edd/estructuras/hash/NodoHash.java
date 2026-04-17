package fase2edd.estructuras.hash;

import fase2edd.model.Producto;


// Nodo para la lista enlazada de cada cubeta
public class NodoHash {
    private Producto dato;
    private NodoHash siguiente;

    public NodoHash(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Producto getDato() {
        return dato;
    }

    public void setDato(Producto dato) {
        this.dato = dato;
    }

    public NodoHash getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoHash siguiente) {
        this.siguiente = siguiente;
    }
}