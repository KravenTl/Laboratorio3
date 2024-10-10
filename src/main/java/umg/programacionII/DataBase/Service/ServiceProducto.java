package umg.programacionII.DataBase.Service;

import umg.programacionII.DataBase.Dao.DaoProducto;
import umg.programacionII.DataBase.Model.ModelProducto;

import java.util.List;

public class ServiceProducto {

    private DaoProducto daoProducto;

    public ServiceProducto() {
        this.daoProducto = new DaoProducto();
    }

    public void agregarProducto(String descripcion, String origen, double precio, int existencia) throws Exception {
        daoProducto.agregarProducto(descripcion, origen, precio, existencia); // Pasar precio y existencia
    }

    public ModelProducto obtenerProductoPorId(int idProducto) throws Exception {
        return daoProducto.obtenerProductoPorId(idProducto);
    }

    public void actualizarProducto(int idProducto, String descripcion, String origen, double precio, int existencia) throws Exception {
        daoProducto.actualizarProducto(idProducto, descripcion, origen, precio, existencia); // Pasar precio y existencia
    }

    public void eliminarProducto(int idProducto) throws Exception {
        daoProducto.eliminarProducto(idProducto);
    }

    public List<ModelProducto> obtenerTodosLosProductos() throws Exception {
        return daoProducto.obtenerTodosLosProductos();
    }

    public List<ModelProducto> obtenerGenericos(String condicion) throws Exception {
        return daoProducto.obtenerGenericos(condicion);
    }
}