package fase2edd.estructuras.btree;

import fase2edd.model.Producto;


public class NodoB {
    private int d;                  // grado mínimo
    private int n;                  // número actual de claves
    private Producto[] claves;      // tamaño máximo 2*d
    private NodoB[] hijos;          // tamaño máximo 2*d + 1
    private boolean hoja;

    public NodoB(int d, boolean hoja) {
        this.d = d;
        this.hoja = hoja;
        this.claves = new Producto[2 * d];
        this.hijos = new NodoB[2 * d + 1];
        this.n = 0;
    }

    public int getD() { return d; }
    public void setD(int d) { this.d = d; }

    public int getN() { return n; }
    public void setN(int n) { this.n = n; }

    public Producto[] getClaves() { return claves; }
    public void setClaves(Producto[] claves) { this.claves = claves; }

    public NodoB[] getHijos() { return hijos; }
    public void setHijos(NodoB[] hijos) { this.hijos = hijos; }

    public boolean isHoja() { return hoja; }
    public void setHoja(boolean hoja) { this.hoja = hoja; }
}