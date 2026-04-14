package fase2edd.model;

public class ResultadoOperacion {
    private boolean exitoso;
    private String mensaje;
    private Producto productoAfectado;

    public ResultadoOperacion() {
        this.exitoso = false;
        this.mensaje = "";
    }

    public ResultadoOperacion(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public ResultadoOperacion(boolean exitoso, String mensaje, Producto producto) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.productoAfectado = producto;
    }

    public boolean isExitoso() { return exitoso; }
    public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Producto getProductoAfectado() { return productoAfectado; }
    public void setProductoAfectado(Producto producto) { this.productoAfectado = producto; }

    @Override
    public String toString() {
        return (exitoso ? "OK: " : "ERROR: ") + mensaje;
    }
}