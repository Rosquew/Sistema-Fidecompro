package Interfaz;

import datos.ProductoDB;
import logica.Producto;
import logica.ProductoOficina;
import logica.ProductoTecnologia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaProductos extends JFrame {

    private ProductoDB productoDB;

    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblTipo;
    private JLabel lblPrecio;
    private JLabel lblStock;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;

    private JComboBox<String> cmbTipo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public VentanaProductos() {
        productoDB = new ProductoDB();

        setTitle("Gestión de Productos - Fidecompro");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        agregarEventos();
        cargarTablaProductos();
    }

    private void inicializarComponentes() {

        lblTitulo = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblId = new JLabel("ID Producto:");
        txtId = new JTextField();

        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        lblTipo = new JLabel("Tipo:");
        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("Tecnología");
        cmbTipo.addItem("Oficina");

        lblPrecio = new JLabel("Precio:");
        txtPrecio = new JTextField();

        lblStock = new JLabel("Stock:");
        txtStock = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblTipo);
        panelFormulario.add(cmbTipo);
        panelFormulario.add(lblPrecio);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(lblStock);
        panelFormulario.add(txtStock);
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelFormulario, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, scrollTabla);
        splitPane.setDividerLocation(350);

        add(splitPane, BorderLayout.CENTER);
    }

    private void agregarEventos() {

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarProducto();
            }
        });
    }

    private void guardarProducto() {
        try {
            if (txtId.getText().trim().isEmpty()
                    || txtNombre.getText().trim().isEmpty()
                    || txtPrecio.getText().trim().isEmpty()
                    || txtStock.getText().trim().isEmpty()) {

                throw new Exception("Debe completar todos los campos.");
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String tipo = cmbTipo.getSelectedItem().toString();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (precio < 0 || stock < 0) {
                throw new Exception("El precio y el stock no pueden ser negativos.");
            }

            Producto producto;

            if (tipo.equals("Tecnología")) {
                producto = new ProductoTecnologia(id, nombre, precio, stock);
            } else {
                producto = new ProductoOficina(id, nombre, precio, stock);
            }

            boolean guardado = productoDB.insertarProducto(producto);

            if (guardado) {
                cargarTablaProductos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el producto. Verifique si el ID ya existe.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID, precio y stock deben ser valores numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarProducto() {
        try {
            if (txtId.getText().trim().isEmpty()
                    || txtNombre.getText().trim().isEmpty()
                    || txtPrecio.getText().trim().isEmpty()
                    || txtStock.getText().trim().isEmpty()) {

                throw new Exception("Debe completar todos los campos.");
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String tipo = cmbTipo.getSelectedItem().toString();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (precio < 0 || stock < 0) {
                throw new Exception("El precio y el stock no pueden ser negativos.");
            }

            Producto producto;

            if (tipo.equals("Tecnología")) {
                producto = new ProductoTecnologia(id, nombre, precio, stock);
            } else {
                producto = new ProductoOficina(id, nombre, precio, stock);
            }

            boolean actualizado = productoDB.actualizarProducto(producto);

            if (actualizado) {
                cargarTablaProductos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el producto.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID, precio y stock deben ser valores numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                throw new Exception("Debe seleccionar un producto para eliminar.");
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = productoDB.eliminarProducto(id);

                if (eliminado) {
                    cargarTablaProductos();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el producto.");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cargarTablaProductos() {
        modeloTabla.setRowCount(0);

        ArrayList<Producto> lista = productoDB.listarProductos();

        for (Producto p : lista) {
            Object[] fila = {
                p.getIdProducto(),
                p.getNombre(),
                p.getTipoProducto(),
                p.getPrecio(),
                p.getStock()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarProducto() {
        int fila = tablaProductos.getSelectedRow();

        if (fila != -1) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        cmbTipo.setSelectedIndex(0);
        txtPrecio.setText("");
        txtStock.setText("");
    }
}