package fase2edd.model;

import fase2edd.estructuras.cola.Cola;
import fase2edd.inventario.Inventario;



public class Sucursal {
    private int id;
    private String nombre;
    private String ubicacion;
    private double tiempoIngreso;      // segundos para procesar llegada
    private double tiempoTraspaso;    // segundos para preparar envío
    private double intervaloDespacho; // segundos entre cada envío

    
    private Cola colaIngreso;
    private Cola colaTraspaso;
    private Cola colaDespacho;

   
    private Inventario inventario;

    public Sucursal() {
        this.colaIngreso = new Cola();
        this.colaTraspaso = new Cola();
        this.colaDespacho = new Cola();
        this.inventario = new Inventario();
    }

    public Sucursal(int id, String nombre, String ubicacion,
                    double tiempoIngreso, double tiempoTraspaso, double intervaloDespacho) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoTraspaso = tiempoTraspaso;
        this.intervaloDespacho = intervaloDespacho;
        this.colaIngreso = new Cola();
        this.colaTraspaso = new Cola();
        this.colaDespacho = new Cola();
        this.inventario = new Inventario();
    }

  
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public double getTiempoIngreso() { return tiempoIngreso; }
    public void setTiempoIngreso(double tiempoIngreso) { this.tiempoIngreso = tiempoIngreso; }

    public double getTiempoTraspaso() { return tiempoTraspaso; }
    public void setTiempoTraspaso(double tiempoTraspaso) { this.tiempoTraspaso = tiempoTraspaso; }

    public double getIntervaloDespacho() { return intervaloDespacho; }
    public void setIntervaloDespacho(double intervaloDespacho) { this.intervaloDespacho = intervaloDespacho; }

    public Cola getColaIngreso() { return colaIngreso; }
    public Cola getColaTraspaso() { return colaTraspaso; }
    public Cola getColaDespacho() { return colaDespacho; }

    public Inventario getInventario() { return inventario; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}