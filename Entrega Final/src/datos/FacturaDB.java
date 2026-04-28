package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import logica.DetalleFactura;
import logica.Factura;

public class FacturaDB {

    public boolean guardarFactura(Factura factura) {

        String sqlFactura = "INSERT INTO facturas (id_factura, id_cliente, fecha, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_factura (id_factura, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        try {
            Connection conexion = ConexionBD.conectar();

            PreparedStatement psFactura = conexion.prepareStatement(sqlFactura);
            psFactura.setInt(1, factura.getIdFactura());
            psFactura.setInt(2, factura.getCliente().getIdCliente());
            psFactura.setString(3, factura.getFecha());
            psFactura.setDouble(4, factura.getTotal());
            psFactura.executeUpdate();
            psFactura.close();

            for (DetalleFactura detalle : factura.getDetalles()) {

                PreparedStatement psDetalle = conexion.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, factura.getIdFactura());
                psDetalle.setInt(2, detalle.getIdProducto());
                psDetalle.setInt(3, detalle.getCantidad());
                psDetalle.setDouble(4, detalle.getPrecioUnitario());
                psDetalle.setDouble(5, detalle.getSubtotal());
                psDetalle.executeUpdate();
                psDetalle.close();

                PreparedStatement psStock = conexion.prepareStatement(sqlStock);
                psStock.setInt(1, detalle.getCantidad());
                psStock.setInt(2, detalle.getIdProducto());
                psStock.executeUpdate();
                psStock.close();
            }

            conexion.close();
            return true;

        } catch (Exception e) {
            System.out.println("Error real al guardar factura:");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Object[]> listarFacturas() {
        ArrayList<Object[]> lista = new ArrayList<>();

        String sql = "SELECT f.id_factura, c.nombre, f.fecha, f.total "
                + "FROM facturas f "
                + "INNER JOIN clientes c ON f.id_cliente = c.id_cliente";

        try {
            Connection conexion = ConexionBD.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_factura"),
                    rs.getString("nombre"),
                    rs.getString("fecha"),
                    rs.getDouble("total")
                };

                lista.add(fila);
            }

            rs.close();
            ps.close();
            conexion.close();

        } catch (Exception e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Object[]> listarDetalleFactura(int idFactura) {
        ArrayList<Object[]> lista = new ArrayList<>();

        String sql = "SELECT p.nombre, d.cantidad, d.precio_unitario, d.subtotal "
                + "FROM detalle_factura d "
                + "INNER JOIN productos p ON d.id_producto = p.id_producto "
                + "WHERE d.id_factura = ?";

        try {
            Connection conexion = ConexionBD.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setInt(1, idFactura);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal")
                };

                lista.add(fila);
            }

            rs.close();
            ps.close();
            conexion.close();

        } catch (Exception e) {
            System.out.println("Error al listar detalle de factura: " + e.getMessage());
        }

        return lista;
    }
}