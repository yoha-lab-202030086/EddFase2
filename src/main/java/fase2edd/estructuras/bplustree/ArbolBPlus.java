package fase2edd.estructuras.bplustree;

import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.model.Producto;



public class ArbolBPlus {
    private NodoBPlus raiz;
    private int d;   // grado mínimo

    public ArbolBPlus(int d) {
        this.d = d;
        this.raiz = null;
    }
    
    public NodoBPlus getRaiz() {
    return raiz;
}
    public void insertar(Producto p) {
        String clave = p.getCategoria();
        if (raiz == null) {
            raiz = new NodoBPlus(d, true);
            raiz.getClaves()[0] = clave;
            raiz.getProductos()[0] = p;
            raiz.setN(1);
            return;
        }
        // Si raíz llena, crear nueva raíz y dividir
        if (raiz.getN() == 2 * d) {
            NodoBPlus nuevaRaiz = new NodoBPlus(d, false);
            nuevaRaiz.getHijos()[0] = raiz;
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
        insertarNoLleno(raiz, clave, p);
    }

    private void insertarNoLleno(NodoBPlus nodo, String clave, Producto p) {
        if (nodo.isHoja()) {
            // Insertar en hoja manteniendo orden por categoría (y luego por código de barras si igual)
            int i = nodo.getN() - 1;
            while (i >= 0 && clave.compareTo(nodo.getClaves()[i]) < 0) {
                nodo.getClaves()[i + 1] = nodo.getClaves()[i];
                nodo.getProductos()[i + 1] = nodo.getProductos()[i];
                i--;
            }
            // Si misma categoría, orden secundario por código de barra (opcional)
            while (i >= 0 && clave.equals(nodo.getClaves()[i]) &&
                   p.getCodigoBarra().compareTo(nodo.getProductos()[i].getCodigoBarra()) < 0) {
                nodo.getClaves()[i + 1] = nodo.getClaves()[i];
                nodo.getProductos()[i + 1] = nodo.getProductos()[i];
                i--;
            }
            nodo.getClaves()[i + 1] = clave;
            nodo.getProductos()[i + 1] = p;
            nodo.setN(nodo.getN() + 1);
        } else {
            // Encontrar hijo adecuado
            int i = nodo.getN() - 1;
            while (i >= 0 && clave.compareTo(nodo.getClaves()[i]) < 0) {
                i--;
            }
            i++; // índice del hijo donde insertar
            if (nodo.getHijos()[i].getN() == 2 * d) {
                dividirHijo(nodo, i);
                if (clave.compareTo(nodo.getClaves()[i]) >= 0) {
                    i++;
                }
            }
            insertarNoLleno(nodo.getHijos()[i], clave, p);
        }
    }

    private void dividirHijo(NodoBPlus padre, int idx) {
        NodoBPlus hijo = padre.getHijos()[idx];
        NodoBPlus nuevo = new NodoBPlus(d, hijo.isHoja());

        // Copiar la mitad superior de claves y productos/hijos al nuevo nodo
        nuevo.setN(d);
        for (int j = 0; j < d; j++) {
            nuevo.getClaves()[j] = hijo.getClaves()[j + d];
            if (hijo.isHoja()) {
                nuevo.getProductos()[j] = hijo.getProductos()[j + d];
            }
        }
        if (!hijo.isHoja()) {
            for (int j = 0; j <= d; j++) {
                nuevo.getHijos()[j] = hijo.getHijos()[j + d];
            }
        }
        hijo.setN(d);

        // Enlazar hojas
        if (hijo.isHoja()) {
            nuevo.setSiguienteHoja(hijo.getSiguienteHoja());
            hijo.setSiguienteHoja(nuevo);
        }

        
        for (int j = padre.getN(); j >= idx + 1; j--) {
            padre.getHijos()[j + 1] = padre.getHijos()[j];
        }
        padre.getHijos()[idx + 1] = nuevo;

        
        for (int j = padre.getN() - 1; j >= idx; j--) {
            padre.getClaves()[j + 1] = padre.getClaves()[j];
        }
       
        padre.getClaves()[idx] = nuevo.getClaves()[0];
        padre.setN(padre.getN() + 1);
    }

    //Busqueda por categoria
    public ListaEnlazada buscarPorCategoria(String categoria) {
        ListaEnlazada resultado = new ListaEnlazada();
        if (raiz == null) return resultado;

        // Descender hasta la hoja que podría contener la categoría
        NodoBPlus nodo = raiz;
        while (!nodo.isHoja()) {
            int i = 0;
            while (i < nodo.getN() && categoria.compareTo(nodo.getClaves()[i]) >= 0) {
                i++;
            }
            nodo = nodo.getHijos()[i];
        }

        // Recorrer las hojas hacia adelante recolectando productos
        while (nodo != null) {
            for (int i = 0; i < nodo.getN(); i++) {
                if (nodo.getClaves()[i].equals(categoria)) {
                    resultado.insertar(nodo.getProductos()[i]);
                } else if (nodo.getClaves()[i].compareTo(categoria) > 0) {
                    // Como están ordenadas, si ya pasamos la categoría, terminamos
                    return resultado;
                }
            }
            nodo = nodo.getSiguienteHoja();
        }
        return resultado;
    }

    public void eliminar(String categoria, String codigoBarra) {
        if (raiz == null) return;
        eliminarRec(raiz, categoria, codigoBarra);
        if (raiz.getN() == 0) {
            if (raiz.isHoja()) {
                raiz = null;
            } else {
                raiz = raiz.getHijos()[0];
            }
        }
    }

    private void eliminarRec(NodoBPlus nodo, String categoria, String codigo) {
        if (nodo.isHoja()) {
            eliminarDeHoja(nodo, categoria, codigo);
            return;
        }
        // Encontrar el hijo que contiene la clave
        int idx = 0;
        while (idx < nodo.getN() && categoria.compareTo(nodo.getClaves()[idx]) >= 0) {
            idx++;
        }
        NodoBPlus hijo = nodo.getHijos()[idx];
        if (hijo.getN() == d - 1) {
            rellenar(nodo, idx);
        }
        // Después de rellenar, el nodo podría haber cambiado, reubicar
        if (idx > nodo.getN()) {
            eliminarRec(nodo.getHijos()[idx - 1], categoria, codigo);
        } else {
            eliminarRec(nodo.getHijos()[idx], categoria, codigo);
        }
    }

    private void eliminarDeHoja(NodoBPlus hoja, String categoria, String codigo) {
        // Buscar la clave exacta
        int pos = -1;
        for (int i = 0; i < hoja.getN(); i++) {
            if (hoja.getClaves()[i].equals(categoria) && hoja.getProductos()[i].getCodigoBarra().equals(codigo)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) return; // no encontrado

        // Desplazar elementos a la izquierda
        for (int i = pos + 1; i < hoja.getN(); i++) {
            hoja.getClaves()[i - 1] = hoja.getClaves()[i];
            hoja.getProductos()[i - 1] = hoja.getProductos()[i];
        }
        hoja.setN(hoja.getN() - 1);
    }

    private void rellenar(NodoBPlus padre, int idxHijo) {
        NodoBPlus hijo = padre.getHijos()[idxHijo];

        // Intentar préstamo del hermano izquierdo
        if (idxHijo > 0 && padre.getHijos()[idxHijo - 1].getN() >= d) {
            NodoBPlus hermano = padre.getHijos()[idxHijo - 1];
            // Mover claves en hijo para hacer espacio
            for (int i = hijo.getN() - 1; i >= 0; i--) {
                hijo.getClaves()[i + 1] = hijo.getClaves()[i];
                if (hijo.isHoja()) hijo.getProductos()[i + 1] = hijo.getProductos()[i];
            }
            if (!hijo.isHoja()) {
                for (int i = hijo.getN(); i >= 0; i--) {
                    hijo.getHijos()[i + 1] = hijo.getHijos()[i];
                }
            }

            
            if (hijo.isHoja()) {
                hijo.getClaves()[0] = hermano.getClaves()[hermano.getN() - 1];
                hijo.getProductos()[0] = hermano.getProductos()[hermano.getN() - 1];
            } else {
                hijo.getClaves()[0] = padre.getClaves()[idxHijo - 1];
                hijo.getHijos()[0] = hermano.getHijos()[hermano.getN()];
            }
            hijo.setN(hijo.getN() + 1);
            hermano.setN(hermano.getN() - 1);

            
            padre.getClaves()[idxHijo - 1] = (hijo.isHoja() ? hijo.getClaves()[0] : hermano.getClaves()[hermano.getN() - 1]);
        }
        
        else if (idxHijo < padre.getN() && padre.getHijos()[idxHijo + 1].getN() >= d) {
            NodoBPlus hermano = padre.getHijos()[idxHijo + 1];

            if (hijo.isHoja()) {
                hijo.getClaves()[hijo.getN()] = hermano.getClaves()[0];
                hijo.getProductos()[hijo.getN()] = hermano.getProductos()[0];
            } else {
                hijo.getClaves()[hijo.getN()] = padre.getClaves()[idxHijo];
                hijo.getHijos()[hijo.getN() + 1] = hermano.getHijos()[0];
            }
            hijo.setN(hijo.getN() + 1);

            
            for (int i = 1; i < hermano.getN(); i++) {
                hermano.getClaves()[i - 1] = hermano.getClaves()[i];
                if (hermano.isHoja()) hermano.getProductos()[i - 1] = hermano.getProductos()[i];
            }
            if (!hermano.isHoja()) {
                for (int i = 1; i <= hermano.getN(); i++) {
                    hermano.getHijos()[i - 1] = hermano.getHijos()[i];
                }
            }
            hermano.setN(hermano.getN() - 1);

            padre.getClaves()[idxHijo] = (hermano.isHoja() ? hermano.getClaves()[0] : padre.getHijos()[idxHijo + 1].getClaves()[0]);
        }
        // Fusión con hermano izquierdo o derecho
        else {
            if (idxHijo > 0) {
                fusionar(padre, idxHijo - 1);
            } else {
                fusionar(padre, idxHijo);
            }
        }
    }

    private void fusionar(NodoBPlus padre, int idxIzq) {
        NodoBPlus izq = padre.getHijos()[idxIzq];
        NodoBPlus der = padre.getHijos()[idxIzq + 1];

        // Copiar claves y productos de der a izq
        if (izq.isHoja()) {
            for (int i = 0; i < der.getN(); i++) {
                izq.getClaves()[izq.getN() + i] = der.getClaves()[i];
                izq.getProductos()[izq.getN() + i] = der.getProductos()[i];
            }
            izq.setN(izq.getN() + der.getN());
            izq.setSiguienteHoja(der.getSiguienteHoja());
        } else {
            // En nodos internos, la clave separadora del padre baja a izq
            izq.getClaves()[izq.getN()] = padre.getClaves()[idxIzq];
            izq.setN(izq.getN() + 1);
            for (int i = 0; i < der.getN(); i++) {
                izq.getClaves()[izq.getN() + i] = der.getClaves()[i];
            }
            for (int i = 0; i <= der.getN(); i++) {
                izq.getHijos()[izq.getN() + i] = der.getHijos()[i];
            }
            izq.setN(izq.getN() + der.getN());
        }

        
        for (int i = idxIzq + 1; i < padre.getN() - 1; i++) {
            padre.getClaves()[i] = padre.getClaves()[i + 1];
        }
        for (int i = idxIzq + 2; i <= padre.getN(); i++) {
            padre.getHijos()[i - 1] = padre.getHijos()[i];
        }
        padre.setN(padre.getN() - 1);
    }

    public boolean estaVacio() {
        return raiz == null;
    }

   
    public Producto[] obtenerTodos() {
        ListaEnlazada lista = new ListaEnlazada();
        if (raiz != null) {
            // Ir a la primera hoja
            NodoBPlus nodo = raiz;
            while (!nodo.isHoja()) nodo = nodo.getHijos()[0];
            while (nodo != null) {
                for (int i = 0; i < nodo.getN(); i++) {
                    lista.insertar(nodo.getProductos()[i]);
                }
                nodo = nodo.getSiguienteHoja();
            }
        }
        return lista.listar();
    }
}