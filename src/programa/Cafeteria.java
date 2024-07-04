package programa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria {
    private List<Producto> productos; // Lista para mantener todos los productos
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean listaOrdenada = false; // Indica si la lista ya está ordenada

    public Cafeteria() {
        productos = new ArrayList<>();
    }

    // Método para agregar un producto a la lista
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        listaOrdenada = false; // La lista necesita ser reordenada
    }

    // Método para obtener la lista de productos ordenados por fecha de vencimiento
    public List<Producto> getProductosOrdenadosPorVencimiento() {
        //productos.sort(Comparator.comparingLong(Producto::getDiasParaVencer));
        if (!listaOrdenada) {
            productos.sort(Comparator.comparing(Producto::getFechaVencimiento));
            listaOrdenada = true; // Marcar la lista como ordenada
        }
        return productos;
    }

    // Método para obtener la lista de productos
    public List<Producto> getProductos() {
        return productos;
    }

    // Método para eliminar un producto por número de registro
    public boolean eliminarProducto(int numeroRegistro) {
        if (numeroRegistro > 0 && numeroRegistro <= productos.size()) {
            Producto productoEliminado = productos.remove(numeroRegistro - 1);
            System.out.println("Producto eliminado: Nro " + numeroRegistro + " | " + productoEliminado.getNombre() + " | " + productoEliminado.getFechaVencimiento().format(formatter));
            listaOrdenada = false; // La lista necesita ser reordenada
            return true;
        } else {
            System.out.println("Número de registro inválido.");
            return false;
        }
    }


    // Método para exportar el reporte a un archivo de texto
    public void exportarReporte(String nombreArchivo) {
        //ordenarProductosPorVencimiento();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Reporte de productos por fecha de vencimiento:\n");
            writer.write("Nro | Código | Insumo | Conservación | Fecha de Vencimiento | Tiempo de Vida | Estado\n");
            int contador = 1;
            for (Producto producto : getProductosOrdenadosPorVencimiento()) {
                writer.write(contador + " | " + producto.getCodigo() + " | " + producto.getNombre() + " | " +
                        producto.getConservacion() + " | " + producto.getFechaVencimiento().format(formatter) + " | " +
                        producto.getDiasParaVencer() + " días | " + producto.getEstado() + "\n");
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al exportar el reporte: " + e.getMessage());
        }
    }
}