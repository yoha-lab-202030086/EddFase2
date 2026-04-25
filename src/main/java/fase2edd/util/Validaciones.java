package fase2edd.util;

public class Validaciones {

    // Verifica que una cadena no sea nula ni vacía
    public static boolean esStringValido(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Verifica que un código de barras tenga formato numérico (solo dígitos)
    public static boolean esCodigoBarraValido(String codigo) {
        if (!esStringValido(codigo)) return false;
        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) < '0' || codigo.charAt(i) > '9') return false;
        }
        return true;
    }

    // Verifica que una fecha tenga formato AAAA-MM-DD (sin usar librerías de fecha)
    public static boolean esFechaValida(String fecha) {
        if (!esStringValido(fecha)) return false;
        if (fecha.length() != 10) return false;
        if (fecha.charAt(4) != '-' || fecha.charAt(7) != '-') return false;
        String añoStr = fecha.substring(0, 4);
        String mesStr = fecha.substring(5, 7);
        String diaStr = fecha.substring(8, 10);
        try {
            int año = Integer.parseInt(añoStr);
            int mes = Integer.parseInt(mesStr);
            int dia = Integer.parseInt(diaStr);
            if (año < 2000 || año > 2100) return false;
            if (mes < 1 || mes > 12) return false;
            if (dia < 1 || dia > 31) return false;
            // No validamos meses con 30/31 ni años bisiestos para no complicar
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Verifica que el precio sea un número positivo
    public static boolean esPrecioValido(double precio) {
        return precio >= 0.0;
    }

    // Verifica que el stock sea un entero no negativo
    public static boolean esStockValido(int stock) {
        return stock >= 0;
    }

    // Verifica que el ID de sucursal sea positivo
    public static boolean esIdSucursalValido(int id) {
        return id > 0;
    }

    // Verifica que el tiempo o costo de conexión sea no negativo
    public static boolean esPesoValido(double peso) {
        return peso >= 0.0;
    }
}