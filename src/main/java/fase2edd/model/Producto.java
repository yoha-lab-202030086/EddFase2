package fase2edd.model;

import fase2edd.simulacion.EstadoProducto;

public class Producto {
    private String nombre;
    private String codigoBarra;
    private String categoria;
    private String fechaCaducidad;
    private String marca;
    private double precio;
    private int stock;
    private EstadoProducto estado;

    public Producto() {
        this.estado = EstadoProducto.DISPONIBLE;
    }

    public Producto(String nombre, String codigoBarra, String categoria,
                    String fechaCaducidad, String marca, double precio, int stock) {
        this.nombre = nombre;
        this.codigoBarra = codigoBarra;
        this.categoria = categoria;
        this.fechaCaducidad = fechaCaducidad;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.estado = EstadoProducto.DISPONIBLE;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoBarra() { return codigoBarra; }
    public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public EstadoProducto getEstado() { return estado; }
    public void setEstado(EstadoProducto estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombre + " (" + codigoBarra + ")";
    }
}