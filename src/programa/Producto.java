package programa;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Producto {
    private String nombre;
    private String codigo;
    private LocalDate fechaVencimiento;
    private String conservacion;

    public Producto(String nombre, String codigo, LocalDate fechaVencimiento, String conservacion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.fechaVencimiento = fechaVencimiento;
        this.conservacion = conservacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getConservacion() {
        return conservacion;
    }

    // Método para calcular los días restantes para vencer
    public long getDiasParaVencer() {
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }

    // Método para determinar el estado del producto
    public String getEstado() {
        long diasParaVencer = getDiasParaVencer();
        if (diasParaVencer < 0) {
            return "Vencido";
        } else if (diasParaVencer <= 3) {
            return "Próximo a vencer";
        } else {
            return "En buen estado";
        }
    }
}