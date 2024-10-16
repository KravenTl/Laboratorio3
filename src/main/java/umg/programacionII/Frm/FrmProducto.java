package umg.programacionII.Frm;

import umg.programacionII.DataBase.Dao.DaoProducto;
import umg.programacionII.DataBase.Model.ModelProducto;
import umg.programacionII.DataBase.Service.ServiceProducto;
import umg.programacionII.Reportes.PdfReport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmProducto {
    private JPanel JPanelProducto;
    private JLabel lblTitulo;
    private JLabel lblId;
    private JTextField textFieldID;
    private JLabel lblNombre;
    private JTextField textFieldNombre;
    private JTextField textFieldPrecio;
    private JLabel lblPrecio;
    private JTextField textFieldExistencia;
    private JLabel lblExistencia;
    private JLabel lblOrigen;
    private JComboBox comboBoxOrigen;
    private JComboBox comboBoxReportes;
    private JButton buttonCrearReporte;
    private JButton buttonBuscar;
    private JButton buttonInsertar;
    private JButton buttonEliminar;
    private JButton buttonActualizar;
    private JLabel lblTipoReport;
    private ServiceProducto serviceproducto;
    private DaoProducto daoProducto;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrmProducto");
        frame.setContentPane(new FrmProducto().JPanelProducto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
    public FrmProducto() {
        //Instanciar la clase Service del producto
        serviceproducto = new ServiceProducto();

        //Contenido del combo box origen
        String[] orígenes = {
                "","Estados Unidos", "Corea del Sur", "Vietnam", "Colombia",
                "Italia", "Japón", "Suiza", "México",
                "España", "Argentina", "Noruega", "Suecia",
                "Francia", "Irlanda", "China", "Alemania", "Escocia"
        };

        for (String org: orígenes ){
            comboBoxOrigen.addItem(org);
        }

        //Contenido del combobox Registro
        // Definir las opciones en un array
        String[] opcionesReportes = {
                "Reporte General",
                "Existencia menores a 20",
                "Filtrado por pais",
                "Precios mayores a 2,000",
                "Agrupado por pais y ordenado por precio, de mayor a menor",
                "Precio menores a 100",
                "Existencia menor a 30 unidades",
                "Precio entre 200 y 400",
                "Ordenar de mayor a menor",
                "Ordenar de menor a mayor"
        };
        // Agregar las opciones al comboBox en un bucle
        for (String opcion : opcionesReportes) {
            comboBoxReportes.addItem(opcion);
        }

        //Boton para buscar
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProducto = Integer.parseInt(textFieldID.getText());
                    ModelProducto producto = serviceproducto.obtenerProductoPorId(idProducto);

                    if (producto != null) {
                        textFieldNombre.setText(producto.getDescripcion());
                        comboBoxOrigen.setSelectedItem(producto.getOrigen());
                        textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
                        textFieldExistencia.setText(String.valueOf(producto.getExistencia()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + ex.getMessage());
                }
            }
        });

        //Buton para insertar productos
        buttonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validar que todos los campos estén llenos
                if (textFieldNombre.getText().isEmpty() || textFieldPrecio.getText().isEmpty() ||
                        textFieldExistencia.getText().isEmpty() || comboBoxOrigen.getSelectedItem().toString().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return; // Termina la ejecución si algún campo está vacío
                }

                // Guardar producto
                String descripcion = textFieldNombre.getText();
                String origen = comboBoxOrigen.getSelectedItem().toString();
                double precio = Double.parseDouble(textFieldPrecio.getText());
                int existencia = Integer.parseInt(textFieldExistencia.getText());
                ModelProducto producto = new ModelProducto();
                producto.setDescripcion(descripcion);
                producto.setOrigen(origen);
                producto.setPrecio(precio);
                producto.setExistencia(existencia);

                try {
                    serviceproducto.agregarProducto(producto.getDescripcion(), producto.getOrigen(), producto.getPrecio(), producto.getExistencia());
                    JOptionPane.showMessageDialog(null, "Producto guardado exitosamente");
                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage());
                }
            }
        });

        //boton para eliminar
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int idProducto = Integer.parseInt(textFieldID.getText());
                    ModelProducto producto = serviceproducto.obtenerProductoPorId(idProducto);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (producto.getPrecio() == 0){
                            serviceproducto.eliminarProducto(idProducto);
                            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
                            limpiarCampos();
                        }else{
                            JOptionPane.showMessageDialog(null, "No se puede eliminar productos cuya existencia sea mayor a cero");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
                }

            }
        });

        //boton para actualizar
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProducto = Integer.parseInt(textFieldID.getText());
                    String descripcion = textFieldNombre.getText();
                    String origen = comboBoxOrigen.getSelectedItem().toString();
                    double precio = Double.parseDouble(textFieldPrecio.getText());
                    int existencia = Integer.parseInt(textFieldExistencia.getText());

                    ModelProducto producto = new ModelProducto();
                    producto.setId_producto(idProducto);
                    producto.setDescripcion(descripcion);
                    producto.setOrigen(origen);
                    producto.setPrecio(precio);
                    producto.setExistencia(existencia);

                    serviceproducto.actualizarProducto(producto.getId_producto(),producto.getDescripcion(), producto.getOrigen(),producto.getPrecio(),producto.getExistencia());
                    JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente");
                    limpiarCampos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + ex.getMessage());
                }
            }
        });


        //Boton para generar los distintos tipos de reportes
        buttonCrearReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int reportip= comboBoxReportes.getSelectedIndex();
                    List<ModelProducto> condi;
                    boolean agrupar=false;
                    switch (reportip) {
                        case 0: {
                            //Reporte General
                            condi = new ServiceProducto().obtenerTodosLosProductos();
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 1: {
                            //Reporte de productos con existencias menores a 20 unidades
                            condi = new ServiceProducto().obtenerGenericos("existencia<20");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 2: {
                            //Reporte de los productos de un pais en especifico
                            String paiselegido = comboBoxOrigen.getSelectedItem().toString();
                            if (paiselegido.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Porfavor selccionar un pais");
                                return;
                            }
                            condi = new ServiceProducto().obtenerGenericos("origen='" + paiselegido + "'");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 3: {
                            //Reporte de productos con un precio mayor a 2,000
                            condi = new ServiceProducto().obtenerGenericos("precio>2000");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 4: {
                            //Reporte agrupado por pais y ordenado por precio de mayor a menor
                            agrupar = true;
                            condi = new ServiceProducto().productosagrupados();
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar); // Agrupado
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 5: {
                            //Reporte de productos con precios menores a 100
                            condi = new ServiceProducto().obtenerGenericos("precio<100");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 6: {
                            //Reporte de productos con existencias menores a 30
                            condi = new ServiceProducto().obtenerGenericos("existencia<30");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 7: {
                            //Reporte de productos con un precio entre 200 y 400
                            condi = new ServiceProducto().obtenerGenericos("precio>=200 AND precio<=400");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 8: {
                            //Reporte de productos ordenados por precio de mayor a menor
                            condi = new ServiceProducto().obtenerGenericos("1 ORDER BY precio DESC");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                        case 9: {
                            //Reporte de productos ordenados por precio de menor a mayor
                            condi = new ServiceProducto().obtenerGenericos("1 ORDER BY precio ASC");
                            new PdfReport().generateProductReport(condi, "C:\\Reportes en PDF\\reporte.pdf", agrupar);
                            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Reportes en PDF");
                            break;
                        }
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al crear el Reporte: " + ex.getMessage());
                }
            }
        });
    }

    //metodo par limpiar los campos
    private void limpiarCampos() {
        textFieldID.setText("");
        textFieldNombre.setText("");
        comboBoxOrigen.setSelectedIndex(0);
        textFieldPrecio.setText("");
        textFieldExistencia.setText("");
    }

}
