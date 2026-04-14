package fase2edd.estructuras.cola;

import fase2edd.model.Producto;



public class NodoCola {
    private Producto dato;
    private NodoCola siguiente;

    public NodoCola(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Producto getDato() {
        return dato;
    }

    public void setDato(Producto dato) {
        this.dato = dato;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }
}