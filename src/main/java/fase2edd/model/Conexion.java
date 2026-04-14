package fase2edd.model;

public class Conexion {
    private int idOrigen;
    private int idDestino;
    private double tiempo;   // peso en tiempo
    private double costo;    // peso en costo
    private boolean bidireccional;

    public Conexion() {}

    public Conexion(int idOrigen, int idDestino, double tiempo, double costo, boolean bidireccional) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.tiempo = tiempo;
        this.costo = costo;
        this.bidireccional = bidireccional;
    }

    public int getIdOrigen() { return idOrigen; }
    public void setIdOrigen(int idOrigen) { this.idOrigen = idOrigen; }

    public int getIdDestino() { return idDestino; }
    public void setIdDestino(int idDestino) { this.idDestino = idDestino; }

    public double getTiempo() { return tiempo; }
    public void setTiempo(double tiempo) { this.tiempo = tiempo; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public boolean isBidireccional() { return bidireccional; }
    public void setBidireccional(boolean bidireccional) { this.bidireccional = bidireccional; }

    @Override
    public String toString() {
        return idOrigen + " -> " + idDestino + " (t:" + tiempo + ", c:" + costo + ")";
    }
}