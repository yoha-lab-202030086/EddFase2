package fase2edd.estructuras.btree;

import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.model.Producto;




public class ArbolB {
    private NodoB raiz;
    private int d;  // grado mínimo 

    public ArbolB(int d) {
        this.d = d;
        this.raiz = null;
    }
    
    public NodoB getRaiz() {
    return raiz;
}

    //  INSERCIÓN 
    public void insertar(Producto p) {
        if (raiz == null) {
            raiz = new NodoB(d, true);
            raiz.getClaves()[0] = p;
            raiz.setN(1);
            return;
        }

        if (raiz.getN() == 2 * d) {
            NodoB nuevaRaiz = new NodoB(d, false);
            nuevaRaiz.getHijos()[0] = raiz;
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
        insertarNoLleno(raiz, p);
    }

    private void insertarNoLleno(NodoB nodo, Producto p) {
        int i = nodo.getN() - 1;

        if (nodo.isHoja()) {
            while (i >= 0 && p.getFechaCaducidad().compareTo(nodo.getClaves()[i].getFechaCaducidad()) < 0) {
                nodo.getClaves()[i + 1] = nodo.getClaves()[i];
                i--;
            }
            nodo.getClaves()[i + 1] = p;
            nodo.setN(nodo.getN() + 1);
        } else {
            while (i >= 0 && p.getFechaCaducidad().compareTo(nodo.getClaves()[i].getFechaCaducidad()) < 0) {
                i--;
            }
            i++;
            if (nodo.getHijos()[i].getN() == 2 * d) {
                dividirHijo(nodo, i);
                if (p.getFechaCaducidad().compareTo(nodo.getClaves()[i].getFechaCaducidad()) > 0) {
                    i++;
                }
            }
            insertarNoLleno(nodo.getHijos()[i], p);
        }
    }

    private void dividirHijo(NodoB padre, int indice) {
        NodoB hijo = padre.getHijos()[indice];
        NodoB nuevo = new NodoB(d, hijo.isHoja());

        nuevo.setN(d - 1);
        for (int j = 0; j < d - 1; j++) {
            nuevo.getClaves()[j] = hijo.getClaves()[j + d];
        }
        if (!hijo.isHoja()) {
            for (int j = 0; j < d; j++) {
                nuevo.getHijos()[j] = hijo.getHijos()[j + d];
            }
        }
        hijo.setN(d - 1);

        for (int j = padre.getN(); j >= indice + 1; j--) {
            padre.getHijos()[j + 1] = padre.getHijos()[j];
        }
        padre.getHijos()[indice + 1] = nuevo;

        for (int j = padre.getN() - 1; j >= indice; j--) {
            padre.getClaves()[j + 1] = padre.getClaves()[j];
        }
        padre.getClaves()[indice] = hijo.getClaves()[d - 1];
        padre.setN(padre.getN() + 1);
    }

 
    public ListaEnlazada buscarPorRango(String fechaInicio, String fechaFin) {
        ListaEnlazada resultados = new ListaEnlazada();
        buscarRangoRec(raiz, fechaInicio, fechaFin, resultados);
        return resultados;
    }

    private void buscarRangoRec(NodoB nodo, String ini, String fin, ListaEnlazada lista) {
        if (nodo == null) return;
        int i = 0;
        while (i < nodo.getN() && nodo.getClaves()[i].getFechaCaducidad().compareTo(ini) < 0) {
            i++;
        }
        if (!nodo.isHoja()) {
            for (int j = 0; j <= i; j++) {
                buscarRangoRec(nodo.getHijos()[j], ini, fin, lista);
            }
        }
        while (i < nodo.getN() && nodo.getClaves()[i].getFechaCaducidad().compareTo(fin) <= 0) {
            lista.insertar(nodo.getClaves()[i]);
            if (!nodo.isHoja()) {
                buscarRangoRec(nodo.getHijos()[i + 1], ini, fin, lista);
            }
            i++;
        }
        if (!nodo.isHoja() && i < nodo.getN()) {
            buscarRangoRec(nodo.getHijos()[i], ini, fin, lista);
        }
    }

 
    public void eliminar(String fecha, String codigoBarra) {
        if (raiz == null) return;
        eliminarRec(raiz, fecha, codigoBarra);
        if (raiz.getN() == 0) {
            if (raiz.isHoja())
                raiz = null;
            else
                raiz = raiz.getHijos()[0];
        }
    }

    private void eliminarRec(NodoB nodo, String fecha, String codigo) {
        int idx = encontrarIndice(nodo, fecha, codigo);

        if (idx < nodo.getN() && nodo.getClaves()[idx].getFechaCaducidad().equals(fecha)
                && nodo.getClaves()[idx].getCodigoBarra().equals(codigo)) {
            if (nodo.isHoja()) {
                eliminarDeHoja(nodo, idx);
            } else {
                eliminarDeNoHoja(nodo, idx);
            }
        } else {
            if (nodo.isHoja()) return;
            boolean esUltimoHijo = (idx == nodo.getN());
            if (nodo.getHijos()[idx].getN() < d) {
                rellenar(nodo, idx);
            }
            if (esUltimoHijo && idx > nodo.getN())
                eliminarRec(nodo.getHijos()[idx - 1], fecha, codigo);
            else
                eliminarRec(nodo.getHijos()[idx], fecha, codigo);
        }
    }

    private int encontrarIndice(NodoB nodo, String fecha, String codigo) {
        int i = 0;
        while (i < nodo.getN() && nodo.getClaves()[i].getFechaCaducidad().compareTo(fecha) < 0) {
            i++;
        }
        if (i < nodo.getN() && nodo.getClaves()[i].getFechaCaducidad().equals(fecha)
                && nodo.getClaves()[i].getCodigoBarra().equals(codigo)) {
            return i;
        }
        return i;
    }

    private void eliminarDeHoja(NodoB nodo, int idx) {
        for (int i = idx + 1; i < nodo.getN(); i++) {
            nodo.getClaves()[i - 1] = nodo.getClaves()[i];
        }
        nodo.setN(nodo.getN() - 1);
    }

    private void eliminarDeNoHoja(NodoB nodo, int idx) {
        Producto clave = nodo.getClaves()[idx];
        if (nodo.getHijos()[idx].getN() >= d) {
            Producto pre = obtenerPredecesor(nodo, idx);
            nodo.getClaves()[idx] = pre;
            eliminarRec(nodo.getHijos()[idx], pre.getFechaCaducidad(), pre.getCodigoBarra());
        } else if (nodo.getHijos()[idx + 1].getN() >= d) {
            Producto suc = obtenerSucesor(nodo, idx);
            nodo.getClaves()[idx] = suc;
            eliminarRec(nodo.getHijos()[idx + 1], suc.getFechaCaducidad(), suc.getCodigoBarra());
        } else {
            fusionar(nodo, idx);
            eliminarRec(nodo.getHijos()[idx], clave.getFechaCaducidad(), clave.getCodigoBarra());
        }
    }

    private Producto obtenerPredecesor(NodoB nodo, int idx) {
        NodoB actual = nodo.getHijos()[idx];
        while (!actual.isHoja()) {
            actual = actual.getHijos()[actual.getN()];
        }
        return actual.getClaves()[actual.getN() - 1];
    }

    private Producto obtenerSucesor(NodoB nodo, int idx) {
        NodoB actual = nodo.getHijos()[idx + 1];
        while (!actual.isHoja()) {
            actual = actual.getHijos()[0];
        }
        return actual.getClaves()[0];
    }

    private void fusionar(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx + 1];
        hijo.getClaves()[d - 1] = nodo.getClaves()[idx];
        for (int i = 0; i < hermano.getN(); i++) {
            hijo.getClaves()[i + d] = hermano.getClaves()[i];
        }
        if (!hijo.isHoja()) {
            for (int i = 0; i <= hermano.getN(); i++) {
                hijo.getHijos()[i + d] = hermano.getHijos()[i];
            }
        }
        for (int i = idx + 1; i < nodo.getN(); i++) {
            nodo.getClaves()[i - 1] = nodo.getClaves()[i];
        }
        for (int i = idx + 2; i <= nodo.getN(); i++) {
            nodo.getHijos()[i - 1] = nodo.getHijos()[i];
        }
        hijo.setN(hijo.getN() + hermano.getN() + 1);
        nodo.setN(nodo.getN() - 1);
    }

    private void rellenar(NodoB nodo, int idx) {
        if (idx != 0 && nodo.getHijos()[idx - 1].getN() >= d) {
            pedirPrestadoAnterior(nodo, idx);
        } else if (idx != nodo.getN() && nodo.getHijos()[idx + 1].getN() >= d) {
            pedirPrestadoSiguiente(nodo, idx);
        } else {
            if (idx != nodo.getN()) {
                fusionar(nodo, idx);
            } else {
                fusionar(nodo, idx - 1);
            }
        }
    }

    private void pedirPrestadoAnterior(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx - 1];
        for (int i = hijo.getN() - 1; i >= 0; i--) {
            hijo.getClaves()[i + 1] = hijo.getClaves()[i];
        }
        if (!hijo.isHoja()) {
            for (int i = hijo.getN(); i >= 0; i--) {
                hijo.getHijos()[i + 1] = hijo.getHijos()[i];
            }
        }
        hijo.getClaves()[0] = nodo.getClaves()[idx - 1];
        if (!hijo.isHoja()) {
            hijo.getHijos()[0] = hermano.getHijos()[hermano.getN()];
        }
        nodo.getClaves()[idx - 1] = hermano.getClaves()[hermano.getN() - 1];
        hijo.setN(hijo.getN() + 1);
        hermano.setN(hermano.getN() - 1);
    }

    private void pedirPrestadoSiguiente(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx + 1];
        hijo.getClaves()[hijo.getN()] = nodo.getClaves()[idx];
        if (!hijo.isHoja()) {
            hijo.getHijos()[hijo.getN() + 1] = hermano.getHijos()[0];
        }
        nodo.getClaves()[idx] = hermano.getClaves()[0];
        for (int i = 1; i < hermano.getN(); i++) {
            hermano.getClaves()[i - 1] = hermano.getClaves()[i];
        }
        if (!hermano.isHoja()) {
            for (int i = 1; i <= hermano.getN(); i++) {
                hermano.getHijos()[i - 1] = hermano.getHijos()[i];
            }
        }
        hijo.setN(hijo.getN() + 1);
        hermano.setN(hermano.getN() - 1);
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public Producto[] obtenerTodosInOrden() {
        ListaEnlazada lista = new ListaEnlazada();
        inOrdenRec(raiz, lista);
        Producto[] arr = new Producto[lista.getTamanio()];
        // Copiar manualmente desde la lista enlazada
        NodoB actual = null; // Necesitamos recorrer la lista
        // Como no tenemos iterador, usamos el método listar() de ListaEnlazada
        // Pero listar() devuelve Producto[], así que podemos llamarlo directamente
        return lista.listar();
    }

    private void inOrdenRec(NodoB nodo, ListaEnlazada lista) {
        if (nodo != null) {
            int i;
            for (i = 0; i < nodo.getN(); i++) {
                if (!nodo.isHoja()) {
                    inOrdenRec(nodo.getHijos()[i], lista);
                }
                lista.insertar(nodo.getClaves()[i]);
            }
            if (!nodo.isHoja()) {
                inOrdenRec(nodo.getHijos()[i], lista);
            }
        }
    }
}