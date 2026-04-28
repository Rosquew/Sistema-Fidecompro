package logica;

import java.util.ArrayList;

public class Factura {

    private int idFactura;
    private Cliente cliente;
    private String fecha;
    private ArrayList<DetalleFactura> detalles;
    private double total;

    public Factura(int idFactura, Cliente cliente, String fecha) {
        this.idFactura = idFactura;
        this.cliente = cliente;
        this.fecha = fecha;
        this.detalles = new ArrayList<>();
        this.total = 0;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        total += detalle.getSubtotal();
    }

    public int getIdFactura() {
        return idFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<DetalleFactura> getDetalles() {
        return detalles;
    }

    public double getTotal() {
        return total;
    }
}