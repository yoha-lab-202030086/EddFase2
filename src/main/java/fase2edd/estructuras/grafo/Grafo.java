package fase2edd.estructuras.grafo;


public class Grafo {
    private NodoGrafo[] nodos;
    private int numNodos;
    private int capacidadNodos;

    public Grafo() {
        capacidadNodos = 8;
        nodos = new NodoGrafo[capacidadNodos];
        numNodos = 0;
    }

   
    public void agregarNodo(int idSucursal) {
        if (buscarNodo(idSucursal) != null) return; // ya existe
        if (numNodos == capacidadNodos) {
            capacidadNodos *= 2;
            NodoGrafo[] nuevo = new NodoGrafo[capacidadNodos];
            for (int i = 0; i < numNodos; i++) {
                nuevo[i] = nodos[i];
            }
            nodos = nuevo;
        }
        nodos[numNodos++] = new NodoGrafo(idSucursal);
    }
    
   
    public double obtenerPesoArista(int origen, int destino, int criterio) {
        NodoGrafo nodoOrigen = buscarNodo(origen);
        if (nodoOrigen == null) {
            return Double.MAX_VALUE;
        }
        Arista[] aristas = nodoOrigen.getAristas();
        for (int i = 0; i < nodoOrigen.getNumAristas(); i++) {
            if (aristas[i].getDestino() == destino) {
                return (criterio == 0) ? aristas[i].getTiempo() : aristas[i].getCosto();
            }
        }
        return Double.MAX_VALUE;
    }

    
    public void agregarArista(int origen, int destino, double tiempo, double costo) {
        NodoGrafo nodoOrigen = buscarNodo(origen);
        if (nodoOrigen == null) {
            agregarNodo(origen);
            nodoOrigen = buscarNodo(origen);
        }
        //si ya existe arista entre los mismos, se actualiza
        Arista[] existentes = nodoOrigen.getAristas();
        for (int i = 0; i < nodoOrigen.getNumAristas(); i++) {
            if (existentes[i].getDestino() == destino) {
                // Actualizar pesos
                existentes[i] = new Arista(destino, tiempo, costo);
                return;
            }
        }
        nodoOrigen.agregarArista(new Arista(destino, tiempo, costo));
    }

    private NodoGrafo buscarNodo(int id) {
        for (int i = 0; i < numNodos; i++) {
            if (nodos[i].getIdSucursal() == id) return nodos[i];
        }
        return null;
    }

    
    public int[] rutaMasCorta(int idOrigen, int idDestino, int criterio) {
        // Número de nodos en el grafo
        int n = numNodos;
        if (n == 0) return null;

        // Mapear ids a índices
        int origenIdx = -1, destinoIdx = -1;
        for (int i = 0; i < n; i++) {
            if (nodos[i].getIdSucursal() == idOrigen) origenIdx = i;
            if (nodos[i].getIdSucursal() == idDestino) destinoIdx = i;
        }
        if (origenIdx == -1 || destinoIdx == -1) return null;

        // Arreglos para Dijkstra
        double[] dist = new double[n];
        boolean[] visitado = new boolean[n];
        int[] anterior = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Double.MAX_VALUE;
            anterior[i] = -1;
        }
        dist[origenIdx] = 0;

        
        for (int count = 0; count < n; count++) {
            int u = -1;
            double minDist = Double.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }
            if (u == -1) break; // nodos inalcanzables
            visitado[u] = true;

            // Relajar aristas de u
            Arista[] aristasU = nodos[u].getAristas();
            for (int j = 0; j < nodos[u].getNumAristas(); j++) {
                int vIdx = -1;
                for (int k = 0; k < n; k++) {
                    if (nodos[k].getIdSucursal() == aristasU[j].getDestino()) {
                        vIdx = k;
                        break;
                    }
                }
                if (vIdx == -1) continue;
                double peso = (criterio == 0) ? aristasU[j].getTiempo() : aristasU[j].getCosto();
                if (!visitado[vIdx] && dist[u] + peso < dist[vIdx]) {
                    dist[vIdx] = dist[u] + peso;
                    anterior[vIdx] = u;
                }
            }
        }

        
        if (dist[destinoIdx] == Double.MAX_VALUE) return null;

        
        int[] temp = new int[n];
        int count = 0;
        int actual = destinoIdx;
        while (actual != -1) {
            temp[count++] = nodos[actual].getIdSucursal();
            actual = anterior[actual];
        }

       
        int[] ruta = new int[count];
        for (int i = 0; i < count; i++) {
            ruta[i] = temp[count - 1 - i];
        }
        return ruta;
    }

    
    public NodoGrafo[] getNodos() {
        NodoGrafo[] resultado = new NodoGrafo[numNodos];
        for (int i = 0; i < numNodos; i++) {
            resultado[i] = nodos[i];
        }
        return resultado;
    }

    public int getNumNodos() {
        return numNodos;
    }
}