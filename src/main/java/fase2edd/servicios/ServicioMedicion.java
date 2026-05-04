package fase2edd.servicios;

import fase2edd.inventario.Inventario;
import fase2edd.model.Producto;

public class ServicioMedicion {

    private double tiempoListaSecuencial;
    private double tiempoAVL;
    private double tiempoHash;

    
    private int repeticiones;

    public ServicioMedicion() {
        this.tiempoListaSecuencial = 0;
        this.tiempoAVL = 0;
        this.tiempoHash = 0;
        this.repeticiones = 100;
    }

 
    public void compararBusquedaPorNombre(Inventario inventario, String nombre) {
        long acumLista = 0;
        long acumAVL = 0;

        for (int i = 0; i < repeticiones; i++) {
            // Búsqueda secuencial en lista enlazada
            long iniLista = System.nanoTime();
            Producto[] todos = inventario.getLista().listar();
            for (int j = 0; j < todos.length; j++) {
                if (todos[j].getNombre().equalsIgnoreCase(nombre)) {
                    break;
                }
            }
            long finLista = System.nanoTime();
            acumLista += (finLista - iniLista);

            // Búsqueda en AVL (por nombre)
            long iniAVL = System.nanoTime();
            inventario.getAvl().buscarPorNombre(nombre);
            long finAVL = System.nanoTime();
            acumAVL += (finAVL - iniAVL);
        }

        this.tiempoListaSecuencial = (double) acumLista / repeticiones; // nanosegundos promedio
        this.tiempoAVL = (double) acumAVL / repeticiones;               // nanosegundos promedio
    }

    
    public void compararBusquedaPorCodigo(Inventario inventario, String codigo) {
        long acumLista = 0;
        long acumHash = 0;

        for (int i = 0; i < repeticiones; i++) {
            // Secuencial en lista
            long iniLista = System.nanoTime();
            Producto[] todos = inventario.getLista().listar();
            for (int j = 0; j < todos.length; j++) {
                if (todos[j].getCodigoBarra().equals(codigo)) {
                    break;
                }
            }
            long finLista = System.nanoTime();
            acumLista += (finLista - iniLista);

            // Hash
            long iniHash = System.nanoTime();
            inventario.getHash().buscar(codigo);
            long finHash = System.nanoTime();
            acumHash += (finHash - iniHash);
        }

        this.tiempoListaSecuencial = (double) acumLista / repeticiones;
        this.tiempoHash = (double) acumHash / repeticiones;
    }

    public void compararBusquedaPorCategoria(Inventario inventario, String categoria) {
        long acumLista = 0;
        long acumBPlus = 0;

        for (int i = 0; i < repeticiones; i++) {
            
            long iniLista = System.nanoTime();
            Producto[] todos = inventario.getLista().listar();
            for (int j = 0; j < todos.length; j++) {
                if (todos[j].getCategoria().equalsIgnoreCase(categoria)) {
                    // encontrado, seguimos contando
                }
            }
            long finLista = System.nanoTime();
            acumLista += (finLista - iniLista);

            // B+
            long iniBPlus = System.nanoTime();
            inventario.getArbolBPlus().buscarPorCategoria(categoria);
            long finBPlus = System.nanoTime();
            acumBPlus += (finBPlus - iniBPlus);
        }

        this.tiempoListaSecuencial = (double) acumLista / repeticiones;
        this.tiempoAVL = (double) acumBPlus / repeticiones; // reutilizamos campo AVL para B+
    }

    public void compararBusquedaPorRangoFechas(Inventario inventario, String inicio, String fin) {
        long acumLista = 0;
        long acumB = 0;

        for (int i = 0; i < repeticiones; i++) {
            // Secuencial
            long iniLista = System.nanoTime();
            Producto[] todos = inventario.getLista().listar();
            for (int j = 0; j < todos.length; j++) {
                String fecha = todos[j].getFechaCaducidad();
                if (fecha.compareTo(inicio) >= 0 && fecha.compareTo(fin) <= 0) {
                    // dentro del rango
                }
            }
            long finLista = System.nanoTime();
            acumLista += (finLista - iniLista);

            // Árbol B
            long iniB = System.nanoTime();
            inventario.getArbolB().buscarPorRango(inicio, fin);
            long finB = System.nanoTime();
            acumB += (finB - iniB);
        }

        this.tiempoListaSecuencial = (double) acumLista / repeticiones;
        this.tiempoHash = (double) acumB / repeticiones; // reutilizamos campo Hash para B
    }

    // Getters
    public double getTiempoListaSecuencial() { return tiempoListaSecuencial; }
    public double getTiempoAVL() { return tiempoAVL; }
    public double getTiempoHash() { return tiempoHash; }
    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int rep) { this.repeticiones = rep; }

    public String obtenerResumen(String tipo) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== MEDICIÓN DE RENDIMIENTO ===\n");
        sb.append("Tipo de búsqueda: ").append(tipo).append("\n");
        sb.append("Repeticiones: ").append(repeticiones).append("\n");
        sb.append("--------------------------------\n");
        sb.append(String.format("Lista enlazada (secuencial): %.2f ns\n", tiempoListaSecuencial));
        if (tiempoAVL > 0) sb.append(String.format("AVL / B+:              %.2f ns\n", tiempoAVL));
        if (tiempoHash > 0) sb.append(String.format("Hash / B:              %.2f ns\n", tiempoHash));
        sb.append("================================\n");
        return sb.toString();
    }
}