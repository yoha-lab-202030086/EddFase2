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

   
    public Producto[] verTodos() {
        Producto[] arr = new Producto[tamanio];
        NodoCola actual = frente;
        int i = 0;
        while (actual != null) {
            arr[i++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        return arr;
    }

   
    public Producto desencolar() {
        if (frente == null) {
            return null;
        }
        Producto dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return dato;
    }

    
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
