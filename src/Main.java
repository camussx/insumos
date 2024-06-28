import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cafeteria cafeteria = new Cafeteria();

        String[][] insumos = {
                {"Leche fresa 500 ml", "K01"},
                {"Esencia de Algarrobina", "K02"},
                {"Pan de Pollo", "K03"},
                {"Pan con Jamón y Pavo", "K04"},
                {"Jugo de Naranja", "K05"},
                {"Limonada", "K06"}
        };

        boolean continuar = true;

        while (continuar) {
            System.out.println("Seleccione un insumo:");
            for (int i = 0; i < insumos.length; i++) {
                System.out.println((i + 1) + ". " + insumos[i][0]);
            }

            int insumoSeleccionado = -1;
            boolean insumoValido = false;
            while (!insumoValido) {
                System.out.println("Ingrese el número del insumo (1-" + insumos.length + "):");
                if (scanner.hasNextInt()) {
                    insumoSeleccionado = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea

                    if (insumoSeleccionado >= 1 && insumoSeleccionado <= insumos.length) {
                        insumoValido = true;
                    } else {
                        System.out.println("Número de insumo inválido. Por favor, intente de nuevo.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    scanner.next();  // Consumir la entrada inválida
                }
            }

            String nombreInsumo = insumos[insumoSeleccionado - 1][0];
            String codigoInsumo = insumos[insumoSeleccionado - 1][1];

            System.out.println("Ingrese la fecha de vencimiento (DD/MM/YYYY):");
            String fechaVencimientoStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr, formatter);

            System.out.println("Seleccione la conservación:");
            System.out.println("1. Ambiente");
            System.out.println("2. Refrigerado");
            int conservacionSeleccionada = -1;
            boolean conservacionValida = false;
            while (!conservacionValida) {
                System.out.println("Ingrese el número de la conservación (1-2):");
                if (scanner.hasNextInt()) {
                    conservacionSeleccionada = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea

                    if (conservacionSeleccionada == 1 || conservacionSeleccionada == 2) {
                        conservacionValida = true;
                    } else {
                        System.out.println("Número de conservación inválido. Por favor, intente de nuevo.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    scanner.next();  // Consumir la entrada inválida
                }
            }

            String conservacion = conservacionSeleccionada == 1 ? "Ambiente" : "Refrigerado";

            Producto producto = new Producto(nombreInsumo, codigoInsumo, fechaVencimiento, conservacion);
            cafeteria.agregarProducto(producto);

            System.out.println("¿Desea seguir registrando insumos?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            int continuarSeleccion = -1;
            boolean continuarValido = false;
            while (!continuarValido) {
                System.out.println("Ingrese el número de la opción (1-2):");
                if (scanner.hasNextInt()) {
                    continuarSeleccion = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea

                    if (continuarSeleccion == 1 || continuarSeleccion == 2) {
                        continuarValido = true;
                    } else {
                        System.out.println("Número de opción inválido. Por favor, intente de nuevo.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    scanner.next();  // Consumir la entrada inválida
                }
            }

            continuar = continuarSeleccion == 1;
        }

        cafeteria.ordenarProductosPorVencimiento();
        System.out.println("Nro | Insumo | Conservación | Tiempo de Vida");
        int contador = 1;
        for (Producto producto : cafeteria.getProductos()) {
            System.out.println(contador + " | " + producto.getNombre() + " | " + producto.getConservacion() + " | " + producto.getDiasParaVencer() + " días");
            contador++;
        }

        // Exportar el reporte a un archivo de texto
        System.out.println("Ingrese el nombre del archivo para exportar el reporte:");
        String nombreArchivo = scanner.nextLine();
        cafeteria.exportarReporte(nombreArchivo + ".txt");
        System.out.println("Reporte exportado exitosamente.");
    }
}
