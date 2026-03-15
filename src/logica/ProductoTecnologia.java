package logica;

public class ProductoTecnologia extends Producto {

    public ProductoTecnologia(int idProducto, String nombre, double precio, int stock) {
        super(idProducto, nombre, precio, stock);
    }

    @Override
    public String getTipoProducto() {
        return "Tecnología";
    }
}