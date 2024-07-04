package programa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static final String FILE_NAME = "productos.txt";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cafeteria cafeteria = new Cafeteria();
        loadProducts(cafeteria);  // Cargar productos desde archivo

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
            System.out.println("\nElige una opción:");
            System.out.println("1. Registrar ingreso");
            System.out.println("2. Generar reporte");
            System.out.println("3. Eliminar producto"); // Nueva opción para eliminar productos
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarIngreso(cafeteria, insumos, scanner);
                    break;
                case 2:
                    generarReporte(cafeteria);
                    break;
                case 3:
                    eliminarProducto(cafeteria, scanner); // Llamada al nuevo método para eliminar productos
                    break;
                case 4:
                    continuar = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        saveProducts(cafeteria);  // Guardar productos en archivo antes de salir
        scanner.close();
    }

    // Método para registrar el ingreso de nuevos productos
    public static void registrarIngreso(Cafeteria cafeteria, String[][] insumos, Scanner scanner) {
        boolean continuar = true;
        while (continuar) {
            // Mostrar lista de insumos disponibles
            System.out.println("Seleccione un insumo:");
            for (int i = 0; i < insumos.length; i++) {
                System.out.println((i + 1) + ". " + insumos[i][0]);
            }
            System.out.println((insumos.length + 1) + ". Ingresar un producto nuevo");

            // Validar selección de insumo
            int insumoSeleccionado = seleccionarOpcion(scanner, 1, insumos.length + 1);

            String nombreInsumo;
            String codigoInsumo;

            if (insumoSeleccionado == insumos.length + 1) {
                // Ingresar nuevo producto
                System.out.println("Ingrese el nombre del nuevo producto:");
                nombreInsumo = scanner.nextLine();
                System.out.println("Ingrese el código del nuevo producto:");
                codigoInsumo = scanner.nextLine();
            } else {
                nombreInsumo = insumos[insumoSeleccionado - 1][0];
                codigoInsumo = insumos[insumoSeleccionado - 1][1];
            }

            // Ingresar y validar la fecha de vencimiento
            LocalDate fechaVencimiento = ingresarFecha(scanner, "Ingrese la fecha de vencimiento (DD/MM/YYYY):");

            // Seleccionar el tipo de conservación
            System.out.println("Seleccione la conservación:");
            System.out.println("1. Ambiente");
            System.out.println("2. Refrigerado");
            int conservacionSeleccionada = seleccionarOpcion(scanner, 1, 2);

            String conservacion = conservacionSeleccionada == 1 ? "Ambiente" : "Refrigerado";

            // Crear y agregar el producto
            Producto producto = new Producto(nombreInsumo, codigoInsumo, fechaVencimiento, conservacion);
            cafeteria.agregarProducto(producto);

            System.out.println("Producto agregado exitosamente.");

            // Preguntar si desea ingresar otro producto
            System.out.println("¿Deseas ingresar otro producto? (si/no)");
            continuar = scanner.nextLine().equalsIgnoreCase("si");
        }
    }

    // Método para generar el reporte de productos
    public static void generarReporte(Cafeteria cafeteria) {
        System.out.println("\nReporte de productos:");
        System.out.println("Nro | Código | Insumo | Conservación | Fecha de Vencimiento | Tiempo de Vida | Estado");
        int contador = 1;
        for (Producto producto : cafeteria.getProductosOrdenadosPorVencimiento()) {
            System.out.println(contador + " | " + producto.getCodigo() + " | " + producto.getNombre() + " | " + producto.getConservacion() + " | " + producto.getFechaVencimiento().format(formatter) + " | " + producto.getDiasParaVencer() + " días | " + producto.getEstado());
            contador++;
        }

        // Exportar el reporte a un archivo de texto
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo para exportar el reporte:");
        String nombreArchivo = scanner.nextLine();
        cafeteria.exportarReporte(nombreArchivo + ".txt");
        System.out.println("Reporte exportado exitosamente.");
    }


    // Método para eliminar un producto
    public static void eliminarProducto(Cafeteria cafeteria, Scanner scanner) {
        System.out.println("Ingrese el número de registro del producto a eliminar:");
        int numeroRegistro = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        cafeteria.eliminarProducto(numeroRegistro);
    }


    // Método para guardar los productos en un archivo
    public static void saveProducts(Cafeteria cafeteria) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Producto producto : cafeteria.getProductos()) {
                writer.write(producto.getNombre() + ";" + producto.getCodigo() + ";" +
                        producto.getFechaVencimiento().format(formatter) + ";" +
                        producto.getConservacion() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los productos: " + e.getMessage());
        }
    }

    // Método para cargar los productos desde un archivo
    public static void loadProducts(Cafeteria cafeteria) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String nombre = parts[0];
                String codigo = parts[1];
                LocalDate fechaVencimiento = LocalDate.parse(parts[2], formatter);
                String conservacion = parts[3];
                cafeteria.agregarProducto(new Producto(nombre, codigo, fechaVencimiento, conservacion));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }
    }

    // Método auxiliar para seleccionar una opción válida
    private static int seleccionarOpcion(Scanner scanner, int min, int max) {
        int seleccion = -1;
        boolean valido = false;
        while (!valido) {
            System.out.println("Ingrese una opción (" + min + "-" + max + "):");
            if (scanner.hasNextInt()) {
                seleccion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea
                if (seleccion >= min && seleccion <= max) {
                    valido = true;
                } else {
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();  // Consumir la entrada inválida
            }
        }
        return seleccion;
    }

    // Método auxiliar para ingresar y validar una fecha
    private static LocalDate ingresarFecha(Scanner scanner, String mensaje) {
        LocalDate fecha = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println(mensaje);
            String fechaStr = scanner.nextLine();
            try {
                fecha = LocalDate.parse(fechaStr, formatter);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Por favor, intente de nuevo.");
            }
        }
        return fecha;
    }
}