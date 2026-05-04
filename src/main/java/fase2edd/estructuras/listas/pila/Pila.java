package fase2edd.estructuras.listas.pila;

import fase2edd.model.Producto;



public class Pila {
    private NodoPila tope;
    private int tamanio;

    public Pila() {
        tope = null;
        tamanio = 0;
    }


    public void push(Producto p) {
        NodoPila nuevo = new NodoPila(p);
        nuevo.setAbajo(tope);
        tope = nuevo;
        tamanio++;
    }

    
    public Producto pop() {
        if (tope == null) return null;
        Producto dato = tope.getDato();
        tope = tope.getAbajo();
        tamanio--;
        return dato;
    }

    public Producto peek() {
        return (tope != null) ? tope.getDato() : null;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}