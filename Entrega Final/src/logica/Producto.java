package logica;

public class Producto {

    private int idProducto;
    private String nombre;
    private String tipo;
    private double precio;
    private int stock;

    public Producto(int idProducto, String nombre, String tipo, double precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void actualizarStock(int cantidad) {
        this.stock = cantidad;
    }

    public String getTipoProducto() {
        return tipo;
    }
}