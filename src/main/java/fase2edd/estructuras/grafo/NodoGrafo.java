package fase2edd.estructuras.grafo;

public class NodoGrafo {
    private int idSucursal;
    private Arista[] aristas;
    private int numAristas;
    private int capacidadAristas;

    public NodoGrafo(int idSucursal) {
        this.idSucursal = idSucursal;
        this.capacidadAristas = 4;
        this.aristas = new Arista[capacidadAristas];
        this.numAristas = 0;
    }

    public int getIdSucursal() { return idSucursal; }

    public void agregarArista(Arista a) {
        if (numAristas == capacidadAristas) {
            capacidadAristas *= 2;
            Arista[] nuevo = new Arista[capacidadAristas];
            for (int i = 0; i < numAristas; i++) {
                nuevo[i] = aristas[i];
            }
            aristas = nuevo;
        }
        aristas[numAristas++] = a;
    }

    public Arista[] getAristas() {
        // Retorna solo las aristas ocupadas
        Arista[] resultado = new Arista[numAristas];
        for (int i = 0; i < numAristas; i++) {
            resultado[i] = aristas[i];
        }
        return resultado;
    }

    public int getNumAristas() { return numAristas; }
}