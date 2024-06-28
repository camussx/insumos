import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria {
    private List<Producto> productos;

    public Cafeteria() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void ordenarProductosPorVencimiento() {
        productos.sort(Comparator.comparingLong(Producto::getDiasParaVencer));
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void exportarReporte(String nombreArchivo) {
        ordenarProductosPorVencimiento();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Nro | Insumo | Conservación | Tiempo de Vida\n");
            int contador = 1;
            for (Producto producto : productos) {
                writer.write(contador + " | " + producto.getNombre() + " | " + producto.getConservacion() + " | " + producto.getDiasParaVencer() + " días\n");
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
