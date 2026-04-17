package fase2edd.estructuras.grafo;

public class Arista {
    private int destino;      // id de la sucursal destino
    private double tiempo;
    private double costo;

    public Arista(int destino, double tiempo, double costo) {
        this.destino = destino;
        this.tiempo = tiempo;
        this.costo = costo;
    }

    public int getDestino() { return destino; }
    public double getTiempo() { return tiempo; }
    public double getCosto() { return costo; }
}