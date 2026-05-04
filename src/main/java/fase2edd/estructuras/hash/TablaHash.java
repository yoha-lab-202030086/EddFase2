package fase2edd.estructuras.hash;

import fase2edd.model.Producto;


public class TablaHash {
    private NodoHash[] tabla;
    private int capacidad;
    private int ocupados;       
    private double factorCargaMaximo = 0.75;

    public TablaHash() {
        // Tamaño inicial primo
        this.capacidad = 101;
        this.tabla = new NodoHash[capacidad];
        this.ocupados = 0;
    }

    
    private int hash(String codigo) {
        int h = 0;
        for (int i = 0; i < codigo.length(); i++) {
            h = (h * 31 + codigo.charAt(i)) % capacidad;
        }
        return h;
    }

    
    public void insertar(Producto p) {
        // Si ya existe, actualizar
        int indice = hash(p.getCodigoBarra());
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.getDato().getCodigoBarra().equals(p.getCodigoBarra())) {
                actual.setDato(p);
                return;
            }
            actual = actual.getSiguiente();
        }

       
        NodoHash nuevo = new NodoHash(p);
        nuevo.setSiguiente(tabla[indice]);
        tabla[indice] = nuevo;
        ocupados++;

       
        if ((double) ocupados / capacidad > factorCargaMaximo) {
            rehash();
        }
    }

    
    public Producto buscar(String codigo) {
        int indice = hash(codigo);
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.getDato().getCodigoBarra().equals(codigo)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

   
    public Producto eliminar(String codigo) {
        int indice = hash(codigo);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            if (actual.getDato().getCodigoBarra().equals(codigo)) {
                if (anterior == null) {
                    tabla[indice] = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                ocupados--;
                return actual.getDato();
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

   
    private void rehash() {
        int nuevaCapacidad = capacidad * 2 + 1;
        NodoHash[] vieja = tabla;
        tabla = new NodoHash[nuevaCapacidad];
        int viejaCapacidad = capacidad;
        capacidad = nuevaCapacidad;
        ocupados = 0;

        for (int i = 0; i < viejaCapacidad; i++) {
            NodoHash actual = vieja[i];
            while (actual != null) {
                insertar(actual.getDato());
                actual = actual.getSiguiente();
            }
        }
    }

    public boolean contiene(String codigo) {
        return buscar(codigo) != null;
    }

    public int getOcupados() {
        return ocupados;
    }

    public int getCapacidad() {
        return capacidad;
    }

    // Para visualización: devuelve la tabla completa
    public NodoHash[] getTabla() {
        return tabla;
    }
}