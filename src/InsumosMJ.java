import java.util.Scanner;
import java.util.HashMap;

public class InsumosMJ {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear una base de datos simple de productos y sus vidas útiles
        HashMap<String, Integer> productShelfLife = new HashMap<>();
        productShelfLife.put("Base Coffee", 30); // 30 días de vida útil
        // Agregar más productos según sea necesario

        // Ingreso de datos
        System.out.print("Ingrese el nombre del producto: ");
        String productName = scanner.nextLine();

        System.out.print("Ingrese la fecha de producción (DD-MM-YYYY): ");
        String productionDate = scanner.nextLine();

        // Calcular fecha de vencimiento
        int shelfLife = productShelfLife.getOrDefault(productName, 0);
        String expirationDate = calculateExpirationDate(productionDate, shelfLife);

        // Generar y mostrar etiqueta
        String label = generateLabel(productName, productionDate, expirationDate);
        System.out.println(label);

    }

    // Calcular la fecha de vencimiento
    public static String calculateExpirationDate(String productionDate, int shelfLifeDays) {
        String[] parts = productionDate.split("/");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // Agregar la vida útil en días
        day += shelfLifeDays;

        // Ajustar días, meses y años (simplificado)
        while (day > 30) { // Meses de 30 días
            day -= 30;
            month += 1;
        }
        while (month > 12) {
            month -= 12;
            year += 1;
        }

        return String.format("%02d-%02d-%04d", day, month, year);
    }

    // Generar la etiqueta
    public static String generateLabel(String productName, String productionDate, String expirationDate) {
        return "Producto: " + productName + "\n" +
                "Fecha de Producción: " + productionDate + "\n" +
                "Fecha de Vencimiento: " + expirationDate;
    }

}
