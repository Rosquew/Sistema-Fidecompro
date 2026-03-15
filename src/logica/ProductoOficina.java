package logica;

public class ProductoOficina extends Producto {

    public ProductoOficina(int idProducto, String nombre, double precio, int stock) {
        super(idProducto, nombre, precio, stock);
    }

    @Override
    public String getTipoProducto() {
        return "Oficina";
    }
}