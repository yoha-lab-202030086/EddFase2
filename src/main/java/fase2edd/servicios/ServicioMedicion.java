package fase2edd.servicios;

import fase2edd.inventario.Inventario;
import fase2edd.model.Producto;



public class ServicioMedicion {
    private double[] tiemposBusquedaNombre;
    private double[] tiemposBusquedaCodigo;
    private double[] tiemposBusquedaCategoria;
    private double[] tiemposBusquedaRango;
    private int cantidadMediciones;

    public ServicioMedicion() {
        tiemposBusquedaNombre = new double[1000];
        tiemposBusquedaCodigo = new double[1000];
        tiemposBusquedaCategoria = new double[1000];
        tiemposBusquedaRango = new double[1000];
        cantidadMediciones = 0;
    }

    // Mide búsqueda por nombre en AVL y en Lista enlazada (secuencial)
    public void medirBusquedaPorNombre(Inventario inventario, String nombre) {
        long inicio = System.nanoTime();
        Producto pAvl = inventario.getAvl().buscarPorNombre(nombre);
        long finAvl = System.nanoTime();

        long inicioLista = System.nanoTime();
        Producto pLista = null;
        Producto[] todos = inventario.getLista().listar();
        for (int i = 0; i < todos.length; i++) {
            if (todos[i].getNombre().equalsIgnoreCase(nombre)) {
                pLista = todos[i];
                break;
            }
        }
        long finLista = System.nanoTime();

        if (cantidadMediciones < tiemposBusquedaNombre.length) {
            tiemposBusquedaNombre[cantidadMediciones] = (finAvl - inicio) / 1e6; // milisegundos
            // también podemos guardar el de lista en otro arreglo
            // Por simplicidad aquí solo registramos AVL pero se puede extender
        }
        cantidadMediciones++;
    }

    // Similar para las otras búsquedas...
    public double medirTiempoInsercion(Inventario inventario, Producto p) {
        long inicio = System.nanoTime();
        inventario.insertarProducto(p);
        long fin = System.nanoTime();
        return (fin - inicio) / 1e6;
    }

    // Método genérico para reportar últimas mediciones
    public String obtenerResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mediciones realizadas: ").append(cantidadMediciones).append("\n");
        if (cantidadMediciones > 0) {
            double suma = 0;
            for (int i = 0; i < cantidadMediciones; i++) suma += tiemposBusquedaNombre[i];
            sb.append("Tiempo promedio búsqueda por nombre (AVL): ").append(suma / cantidadMediciones).append(" ms");
        }
        return sb.toString();
    }
}