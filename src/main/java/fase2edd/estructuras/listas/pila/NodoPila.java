package fase2edd.estructuras.listas.pila;

import fase2edd.model.Producto;



public class NodoPila {
    private Producto dato;
    private NodoPila abajo;  // apunta al nodo inferior

    public NodoPila(Producto dato) {
        this.dato = dato;
        this.abajo = null;
    }

    public Producto getDato() {
        return dato;
    }

    public void setDato(Producto dato) {
        this.dato = dato;
    }

    public NodoPila getAbajo() {
        return abajo;
    }

    public void setAbajo(NodoPila abajo) {
        this.abajo = abajo;
    }
}