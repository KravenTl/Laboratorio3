package umg.programacionII.Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import umg.programacionII.DataBase.Model.ModelProducto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public void generateProductReport(List<ModelProducto> productos, String outputPath,boolean agrupar) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50); // Tamaño del papel
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open(); // Listo para empezar a trabajar

        addTitle(document); // Función para agregar título
        if(agrupar){
            addProductTableGrouped(document,productos);
        }else {
            addProductTable(document, productos);
        }
        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Yourgen Kraven Thommel Arévalo 0905-23-14003", TITLE_FONT); // Actualizado a Productos
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    //Tabla sin agrupacion
    private void addProductTable(Document document, List<ModelProducto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6); // 6 columnas
        table.setWidthPercentage(100); // Ancho del 100% del documento
        addTableHeader(table);
        addRows(table, productos);
        document.add(table);
    }

    // Tabla agrupada por origen
    private void addProductTableGrouped(Document document, List<ModelProducto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6); // 6 columnas
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRowsGroup(table, productos); // Se usa el metodo addRowsGroup para agrupar
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Descripción", "Origen", "Precio", "Existencia", "Precio Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header);
                });
    }


    private void addRows(PdfPTable table, List<ModelProducto> productos) {
        for (ModelProducto producto : productos) {
            table.addCell(new Phrase(String.valueOf(producto.getId_producto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase(String.format("%.2f", producto.getPrecio()), NORMAL_FONT)); // Formato a dos decimales
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            table.addCell(new Phrase(String.format("%.2f", producto.getExistencia() * producto.getPrecio()), NORMAL_FONT)); // Precio Total
        }
    }

    //Agrupado por origen
    private void addRowsGroup(PdfPTable table, List<ModelProducto> productos) {
        productos.sort((p1, p2) -> p1.getOrigen().compareTo(p2.getOrigen())); // Ordenar por país
        String currentOrigen = null;
        double groupTotalPrecio = 0.0;
        int groupTotalExistencia = 0;
        DecimalFormat deciform = new DecimalFormat("#.00"); // Para formatear los precios con dos decimales

        BaseColor greenColor = BaseColor.CYAN;
        Font boldFont = new Font(NORMAL_FONT.getFamily(), NORMAL_FONT.getSize(), Font.BOLD);

        for (ModelProducto producto : productos) {
            if (!producto.getOrigen().equals(currentOrigen)) {
                // Si no es el primer grupo, imprimir el total del grupo anterior
                if (currentOrigen != null) {
                    PdfPCell totalCellLabel = new PdfPCell(new Phrase("Total Grupo " + currentOrigen, boldFont));
                    totalCellLabel.setColspan(4);
                    totalCellLabel.setBackgroundColor(greenColor);
                    table.addCell(totalCellLabel);

                    PdfPCell totalExistenciaCell = new PdfPCell(new Phrase(String.valueOf(groupTotalExistencia), boldFont));
                    totalExistenciaCell.setBackgroundColor(greenColor);
                    table.addCell(totalExistenciaCell);

                    PdfPCell totalPrecioCell = new PdfPCell(new Phrase(deciform.format(groupTotalPrecio), boldFont));
                    totalPrecioCell.setBackgroundColor(greenColor);
                    table.addCell(totalPrecioCell);
                }

                // Reiniciar totales para el nuevo grupo
                groupTotalPrecio = 0.0;
                groupTotalExistencia = 0;

                // Actualizar el origen actual al nuevo grupo
                currentOrigen = producto.getOrigen();

                // Agregar fila de nuevo grupo
                PdfPCell groupCell = new PdfPCell(new Phrase("Grupo: " + currentOrigen, NORMAL_FONT));
                groupCell.setColspan(6);
                table.addCell(groupCell);
            }

            // Agregar fila del producto
            table.addCell(new Phrase(String.valueOf(producto.getId_producto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase(deciform.format(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            table.addCell(new Phrase(deciform.format(producto.getExistencia() * producto.getPrecio()), NORMAL_FONT));

            // Acumular totales del grupo
            groupTotalPrecio += producto.getExistencia() * producto.getPrecio();
            groupTotalExistencia += producto.getExistencia();
        }

        // Imprimir totales para el último grupo
        if (currentOrigen != null) {
            PdfPCell totalCellLabel = new PdfPCell(new Phrase("Total Grupo " + currentOrigen, boldFont));
            totalCellLabel.setColspan(4);
            totalCellLabel.setBackgroundColor(greenColor);
            table.addCell(totalCellLabel);

            PdfPCell totalExistenciaCell = new PdfPCell(new Phrase(String.valueOf(groupTotalExistencia), boldFont));
            totalExistenciaCell.setBackgroundColor(greenColor);
            table.addCell(totalExistenciaCell);

            PdfPCell totalPrecioCell = new PdfPCell(new Phrase(deciform.format(groupTotalPrecio), boldFont));
            totalPrecioCell.setBackgroundColor(greenColor);
            table.addCell(totalPrecioCell);
        }
    }
}
