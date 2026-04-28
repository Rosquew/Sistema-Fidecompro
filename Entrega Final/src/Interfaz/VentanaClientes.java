package Interfaz;

import datos.ClienteDB;
import logica.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaClientes extends JFrame {

    private ClienteDB clienteDB;

    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblIdentificacion;
    private JLabel lblTelefono;
    private JLabel lblDireccion;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtTelefono;
    private JTextField txtDireccion;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public VentanaClientes() {
        clienteDB = new ClienteDB();

        setTitle("Gestión de Clientes - Fidecompro");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        agregarEventos();
        cargarTablaClientes();
    }

    private void inicializarComponentes() {

        lblTitulo = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblId = new JLabel("ID Cliente:");
        txtId = new JTextField();

        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        lblIdentificacion = new JLabel("Identificación:");
        txtIdentificacion = new JTextField();

        lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField();

        lblDireccion = new JLabel("Dirección:");
        txtDireccion = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblIdentificacion);
        panelFormulario.add(txtIdentificacion);
        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);
        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelFormulario, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Identificación");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Dirección");

        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, scrollTabla);
        splitPane.setDividerLocation(330);

        add(splitPane, BorderLayout.CENTER);
    }

    private void agregarEventos() {

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarCliente();
            }
        });
    }

    private void guardarCliente() {
        try {
            if (txtId.getText().trim().isEmpty()
                    || txtNombre.getText().trim().isEmpty()
                    || txtIdentificacion.getText().trim().isEmpty()
                    || txtTelefono.getText().trim().isEmpty()
                    || txtDireccion.getText().trim().isEmpty()) {

                throw new Exception("Debe completar todos los campos.");
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String identificacion = txtIdentificacion.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();

            Cliente cliente = new Cliente(id, nombre, identificacion, telefono, direccion);

            boolean guardado = clienteDB.insertarCliente(cliente);

            if (guardado) {
                cargarTablaClientes();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el cliente.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarCliente() {
        try {
            if (txtId.getText().trim().isEmpty()
                    || txtNombre.getText().trim().isEmpty()
                    || txtIdentificacion.getText().trim().isEmpty()
                    || txtTelefono.getText().trim().isEmpty()
                    || txtDireccion.getText().trim().isEmpty()) {

                throw new Exception("Debe completar todos los campos.");
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String identificacion = txtIdentificacion.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();

            Cliente cliente = new Cliente(id, nombre, identificacion, telefono, direccion);

            boolean actualizado = clienteDB.actualizarCliente(cliente);

            if (actualizado) {
                cargarTablaClientes();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarCliente() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                throw new Exception("Debe seleccionar un cliente para eliminar.");
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar este cliente?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = clienteDB.eliminarCliente(id);

                if (eliminado) {
                    cargarTablaClientes();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el cliente.");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cargarTablaClientes() {
        modeloTabla.setRowCount(0);

        ArrayList<Cliente> lista = clienteDB.listarClientes();

        for (Cliente c : lista) {
            Object[] fila = {
                c.getIdCliente(),
                c.getNombre(),
                c.getIdentificacion(),
                c.getTelefono(),
                c.getDireccion()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarCliente() {
        int fila = tablaClientes.getSelectedRow();

        if (fila != -1) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtIdentificacion.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtDireccion.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }
}