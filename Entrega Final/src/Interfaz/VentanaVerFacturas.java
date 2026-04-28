package Interfaz;

import datos.FacturaDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaVerFacturas extends JFrame {

    private FacturaDB facturaDB;

    private JLabel lblTitulo;
    private JLabel lblDetalle;

    private JTable tablaFacturas;
    private JTable tablaDetalle;

    private DefaultTableModel modeloFacturas;
    private DefaultTableModel modeloDetalle;

    private JButton btnVerDetalle;
    private JButton btnCerrar;

    public VentanaVerFacturas() {
        facturaDB = new FacturaDB();

        setTitle("Consulta de Facturas - Fidecompro");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        agregarEventos();
        cargarFacturas();
    }

    private void inicializarComponentes() {

        lblTitulo = new JLabel("Facturas Registradas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        modeloFacturas = new DefaultTableModel();
        modeloFacturas.addColumn("ID Factura");
        modeloFacturas.addColumn("Cliente");
        modeloFacturas.addColumn("Fecha");
        modeloFacturas.addColumn("Total");

        tablaFacturas = new JTable(modeloFacturas);
        JScrollPane scrollFacturas = new JScrollPane(tablaFacturas);

        modeloDetalle = new DefaultTableModel();
        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Precio Unitario");
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloDetalle);
        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);

        lblDetalle = new JLabel("Detalle de Factura", SwingConstants.CENTER);
        lblDetalle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelDetalle = new JPanel(new BorderLayout());
        panelDetalle.add(lblDetalle, BorderLayout.NORTH);
        panelDetalle.add(scrollDetalle, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollFacturas, panelDetalle);
        splitPane.setDividerLocation(230);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        btnVerDetalle = new JButton("Ver Detalle");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCerrar);

        add(splitPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarEventos() {

        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDetalleFactura();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        tablaFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarDetalleFactura();
            }
        });
    }

    private void cargarFacturas() {
        modeloFacturas.setRowCount(0);

        ArrayList<Object[]> lista = facturaDB.listarFacturas();

        for (Object[] fila : lista) {
            modeloFacturas.addRow(fila);
        }
    }

    private void cargarDetalleFactura() {
        try {
            int filaSeleccionada = tablaFacturas.getSelectedRow();

            if (filaSeleccionada == -1) {
                throw new Exception("Debe seleccionar una factura.");
            }

            int idFactura = Integer.parseInt(modeloFacturas.getValueAt(filaSeleccionada, 0).toString());

            modeloDetalle.setRowCount(0);

            ArrayList<Object[]> listaDetalle = facturaDB.listarDetalleFactura(idFactura);

            for (Object[] fila : listaDetalle) {
                modeloDetalle.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}