package programa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria {
    private List<Producto> productos;

    public Cafeteria() {
        productos = new ArrayList<>();
    }

    // Método para agregar un producto a la lista
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    // Método para ordenar los productos por fecha de vencimiento
    public void ordenarProductosPorVencimiento() {
        productos.sort(Comparator.comparingLong(Producto::getDiasParaVencer));
    }

    // Método para obtener la lista de productos
    public List<Producto> getProductos() {
        return productos;
    }

    // Método para exportar el reporte a un archivo de texto
    public void exportarReporte(String nombreArchivo) {
        ordenarProductosPorVencimiento();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Nro | Insumo | Conservación | Fecha de Vencimiento | Tiempo de Vida | Estado\n");
            int contador = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Producto producto : productos) {
                writer.write(contador + " | " + producto.getNombre() + " | " + producto.getConservacion() + " | " +
                        producto.getFechaVencimiento().format(formatter) + " | " +
                        producto.getDiasParaVencer() + " días | " +
                        producto.getEstado() + "\n");
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}