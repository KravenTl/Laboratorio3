package umg.programacionII.DataBase.Model;

public class ModelProducto {
    private int id_producto;
    private String descripcion;
    private String origen;
    private double precio;  // Cambiado a double
    private int existencia;

    public ModelProducto(int id_producto, String descripcion, String origen, double precio, int existencia) { // Cambiado a double en el constructor
        this.id_producto = id_producto;
        this.descripcion = descripcion;
        this.origen = origen;
        this.precio = precio;
        this.existencia = existencia;
    }

    public ModelProducto() {}

    // Getters y Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public double getPrecio() { // Cambiado a double
        return precio;
    }

    public void setPrecio(double precio) { // Cambiado a double
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
}
