package fase2edd.estructuras.bplustree;

import fase2edd.model.Producto;



public class NodoBPlus {
    private int d;                 // grado mínimo
    private int n;                 // número actual de claves
    private String[] claves;       // categorías (tamaño máximo 2*d)
    private Producto[] productos;  // solo en hojas: productos correspondientes
    private NodoBPlus[] hijos;     // solo en nodos internos (tamaño 2*d+1)
    private boolean hoja;
    private NodoBPlus siguienteHoja;  

    public NodoBPlus(int d, boolean hoja) {
        this.d = d;
        this.hoja = hoja;
        this.claves = new String[2 * d];
        this.productos = new Producto[2 * d];
        this.hijos = new NodoBPlus[2 * d + 1];
        this.n = 0;
        this.siguienteHoja = null;
    }

    // Getters y Setters
    public int getD() { return d; }
    public void setD(int d) { this.d = d; }
    public int getN() { return n; }
    public void setN(int n) { this.n = n; }
    public String[] getClaves() { return claves; }
    public void setClaves(String[] claves) { this.claves = claves; }
    public Producto[] getProductos() { return productos; }
    public void setProductos(Producto[] productos) { this.productos = productos; }
    public NodoBPlus[] getHijos() { return hijos; }
    public void setHijos(NodoBPlus[] hijos) { this.hijos = hijos; }
    public boolean isHoja() { return hoja; }
    public void setHoja(boolean hoja) { this.hoja = hoja; }
    public NodoBPlus getSiguienteHoja() { return siguienteHoja; }
    public void setSiguienteHoja(NodoBPlus siguienteHoja) { this.siguienteHoja = siguienteHoja; }
}