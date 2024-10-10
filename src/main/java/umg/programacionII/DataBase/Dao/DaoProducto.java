package umg.programacionII.DataBase.Dao;

import umg.programacionII.DataBase.Conexion.Conexion;
import umg.programacionII.DataBase.Model.ModelProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoProducto {

    public void agregarProducto(String descripcion, String origen, double precio, int existencia) throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "INSERT INTO tb_producto (descripcion, origen, precio, existencia) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);
        ps.setDouble(3, precio); // Cambiado a setDouble para el precio
        ps.setInt(4, existencia);

        ps.executeUpdate();
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
    }

    public ModelProducto obtenerProductoPorId(int idProducto) throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "SELECT * FROM tb_producto WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ResultSet rs = ps.executeQuery();
        ModelProducto producto = null;
        if (rs.next()) {
            producto = new ModelProducto();
            producto.setId_producto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio")); // Cambiado a getDouble para el precio
            producto.setExistencia(rs.getInt("existencia"));
        }
        rs.close(); // Cerrar ResultSet
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
        return producto;
    }

    public void actualizarProducto(int idProducto, String descripcion, String origen, double precio, int existencia) throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "UPDATE tb_producto SET descripcion = ?, origen = ?, precio = ?, existencia = ? WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);
        ps.setDouble(3, precio); // Cambiado a setDouble para el precio
        ps.setInt(4, existencia);
        ps.setInt(5, idProducto);

        ps.executeUpdate();
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
    }

    public void eliminarProducto(int idProducto) throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "DELETE FROM tb_producto WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ps.executeUpdate();
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
    }

    public List<ModelProducto> obtenerTodosLosProductos() throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "SELECT * FROM tb_producto ORDER BY origen";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ModelProducto> productos = new ArrayList<>();

        while (rs.next()) {
            ModelProducto producto = new ModelProducto();
            producto.setId_producto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio")); // Cambiado a getDouble para el precio
            producto.setExistencia(rs.getInt("existencia"));
            productos.add(producto);
        }

        rs.close(); // Cerrar ResultSet
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
        return productos;
    }

    public List<ModelProducto> obtenerGenericos(String condicion) throws Exception {
        Connection connection = Conexion.getConnection();
        String query = "SELECT * FROM tb_producto WHERE " + condicion;

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ModelProducto> productos = new ArrayList<>();

        while (rs.next()) {
            ModelProducto producto = new ModelProducto();
            producto.setId_producto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio")); // Cambiado a getDouble para el precio
            producto.setExistencia(rs.getInt("existencia"));
            productos.add(producto);
        }

        rs.close(); // Cerrar ResultSet
        ps.close(); // Cerrar el PreparedStatement
        connection.close();
        return productos;
    }
}
