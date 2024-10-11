package umg.programacionII.Reportes;

import umg.programacionII.DataBase.Model.ModelProducto;
import umg.programacionII.DataBase.Service.ServiceProducto;

import javax.swing.*;
import java.util.List;

public class pruebas {
    public static void main(String[] args) {
        try {
            // Obtener productos cuyo precio esté entre 200 y 400
            List<ModelProducto> productos = new ServiceProducto().obtenerTodosLosProductos();

            // Generar el reporte PDF
            new PdfReport().generateProductReport(productos, "C:\\Reportes en PDF\\reporte.pdf",false);

            // Mostrar un mensaje de que se generó el reporte
            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF\\reporte.pdf");
        } catch (Exception e) {
            // Mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }
}
