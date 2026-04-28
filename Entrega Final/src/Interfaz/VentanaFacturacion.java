package Interfaz;

import datos.ClienteDB;
import datos.ProductoDB;
import datos.FacturaDB;
import logica.Cliente;
import logica.Producto;
import logica.Factura;
import logica.DetalleFactura;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VentanaFacturacion extends JFrame {

    private ClienteDB clienteDB;
    private ProductoDB productoDB;
    private FacturaDB facturaDB;

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Producto> listaProductos;
    private Factura facturaActual;

    private JLabel lblTitulo;
    private JLabel lblIdFactura;
    private JLabel lblCliente;
    private JLabel lblProducto;
    private JLabel lblCantidad;
    private JLabel lblTotal;

    private JTextField txtIdFactura;
    private JTextField txtCantidad;

    private JComboBox<String> cmbClientes;
    private JComboBox<String> cmbProductos;

    private JButton btnAgregarProducto;
    private JButton btnGuardarFactura;
    private JButton btnLimpiar;

    private JTable tablaDetalle;
    private DefaultTableModel modeloTabla;

    private double totalFactura;

    public VentanaFacturacion() {
        clienteDB = new ClienteDB();
        productoDB = new ProductoDB();
        facturaDB = new FacturaDB();

        listaClientes = clienteDB.listarClientes();
        listaProductos = productoDB.listarProductos();

        totalFactura = 0;

        setTitle("Facturación - Fidecompro");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarClientes();
        cargarProductos();
        agregarEventos();
    }

    private void inicializarComponentes() {

        lblTitulo = new JLabel("Facturación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblIdFactura = new JLabel("ID Factura:");
        txtIdFactura = new JTextField();

        lblCliente = new JLabel("Cliente:");
        cmbClientes = new JComboBox<>();

        lblProducto = new JLabel("Producto:");
        cmbProductos = new JComboBox<>();

        lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField();

        panelSuperior.add(lblIdFactura);
        panelSuperior.add(txtIdFactura);
        panelSuperior.add(lblCliente);
        panelSuperior.add(cmbClientes);
        panelSuperior.add(lblProducto);
        panelSuperior.add(cmbProductos);

        JPanel panelCantidad = new JPanel(new GridLayout(1, 3, 10, 10));
        panelCantidad.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        btnAgregarProducto = new JButton("Agregar Producto");

        panelCantidad.add(lblCantidad);
        panelCantidad.add(txtCantidad);
        panelCantidad.add(btnAgregarProducto);

        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.add(panelSuperior, BorderLayout.CENTER);
        panelArriba.add(panelCantidad, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Producto");
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio Unitario");
        modeloTabla.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaDetalle);

        JPanel panelInferior = new JPanel(new GridLayout(1, 3, 20, 20));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        lblTotal = new JLabel("TOTAL: ₡0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        btnGuardarFactura = new JButton("Guardar Factura");
        btnLimpiar = new JButton("Limpiar");

        panelInferior.add(lblTotal);
        panelInferior.add(btnGuardarFactura);
        panelInferior.add(btnLimpiar);

        add(panelArriba, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarClientes() {
        cmbClientes.removeAllItems();

        for (Cliente c : listaClientes) {
            cmbClientes.addItem(c.getIdCliente() + " - " + c.getNombre());
        }
    }

    private void cargarProductos() {
        cmbProductos.removeAllItems();

        for (Producto p : listaProductos) {
            cmbProductos.addItem(p.getIdProducto() + " - " + p.getNombre() + " - Stock: " + p.getStock());
        }
    }

    private void agregarEventos() {

        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoFactura();
            }
        });

        btnGuardarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFactura();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFactura();
            }
        });
    }

    private Cliente obtenerClienteSeleccionado() {
        int indice = cmbClientes.getSelectedIndex();

        if (indice >= 0) {
            return listaClientes.get(indice);
        }

        return null;
    }

    private Producto obtenerProductoSeleccionado() {
        int indice = cmbProductos.getSelectedIndex();

        if (indice >= 0) {
            return listaProductos.get(indice);
        }

        return null;
    }

    private void crearFacturaSiNoExiste() throws Exception {
        if (facturaActual == null) {
            if (txtIdFactura.getText().trim().isEmpty()) {
                throw new Exception("Debe ingresar el ID de la factura.");
            }

            int idFactura = Integer.parseInt(txtIdFactura.getText().trim());
            Cliente cliente = obtenerClienteSeleccionado();

            if (cliente == null) {
                throw new Exception("Debe seleccionar un cliente.");
            }

            String fecha = LocalDate.now().toString();
            facturaActual = new Factura(idFactura, cliente, fecha);
        }
    }

    private void agregarProductoFactura() {
        try {
            crearFacturaSiNoExiste();

            if (txtCantidad.getText().trim().isEmpty()) {
                throw new Exception("Debe ingresar la cantidad.");
            }

            Producto producto = obtenerProductoSeleccionado();

            if (producto == null) {
                throw new Exception("Debe seleccionar un producto.");
            }

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor a cero.");
            }

            if (cantidad > producto.getStock()) {
                throw new Exception("No hay suficiente stock disponible.");
            }

            DetalleFactura detalle = new DetalleFactura(
                    producto.getIdProducto(),
                    producto.getNombre(),
                    cantidad,
                    producto.getPrecio()
            );

            facturaActual.agregarDetalle(detalle);

            Object[] fila = {
                detalle.getIdProducto(),
                detalle.getNombreProducto(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
            };

            modeloTabla.addRow(fila);

            totalFactura = facturaActual.getTotal();
            lblTotal.setText("TOTAL: ₡" + totalFactura);

            txtCantidad.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de factura y la cantidad deben ser numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void guardarFactura() {
        try {
            if (facturaActual == null || facturaActual.getDetalles().isEmpty()) {
                throw new Exception("Debe agregar productos a la factura.");
            }

            boolean guardado = facturaDB.guardarFactura(facturaActual);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Factura guardada correctamente.");

                listaProductos = productoDB.listarProductos();
                cargarProductos();
                limpiarFactura();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar la factura. Verifique si el ID ya existe.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarFactura() {
        facturaActual = null;
        totalFactura = 0;

        txtIdFactura.setText("");
        txtCantidad.setText("");
        modeloTabla.setRowCount(0);
        lblTotal.setText("TOTAL: ₡0.00");

        if (cmbClientes.getItemCount() > 0) {
            cmbClientes.setSelectedIndex(0);
        }

        if (cmbProductos.getItemCount() > 0) {
            cmbProductos.setSelectedIndex(0);
        }
    }
}