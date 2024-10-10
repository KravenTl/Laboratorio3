package umg.programacionII.Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import umg.programacionII.DataBase.Model.ModelProducto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public void generateProductReport(List<ModelProducto> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50); // Tamaño del papel
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open(); // Listo para empezar a trabajar

        addTitle(document); // Función para agregar título
        addProductTable(document, productos);

        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Reporte de Productos", TITLE_FONT); // Actualizado a Productos
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addProductTable(Document document, List<ModelProducto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6); // 6 columnas
        table.setWidthPercentage(100); // Ancho del 100% del documento
        addTableHeader(table);
        addRows(table, productos);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Descripción", "Origen", "Precio", "Existencia", "Precio Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GRAY);
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
}