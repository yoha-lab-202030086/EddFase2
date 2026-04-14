package fase2edd.estructuras.cola;

import fase2edd.model.Producto;


public class Cola {
    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    // Encola un producto (al final)
    public void encolar(Producto p) {
        NodoCola nuevo = new NodoCola(p);
        if (fin == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    // Desencola y devuelve el producto del frente
    public Producto desencolar() {
        if (frente == null) return null;
        Producto dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) fin = null;
        tamanio--;
        return dato;
    }

    // Consulta el frente sin desencolar
    public Producto verFrente() {
        return (frente != null) ? frente.getDato() : null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}

