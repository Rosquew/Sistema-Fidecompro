package Interfaz;

import logica.SistemaFacturacion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {

    private SistemaFacturacion sistema;

    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasena;

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    private JButton btnIngresar;
    private JButton btnLimpiar;

    public VentanaLogin(SistemaFacturacion sistema) {
        this.sistema = sistema;

        setTitle("Inicio de Sesión - Fidecompro");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        agregarEventos();
    }

    private void inicializarComponentes() {

        JPanel panelTitulo = new JPanel();
        lblTitulo = new JLabel("FIDECOMPRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelTitulo.add(lblTitulo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(3, 2, 10, 10));

        lblUsuario = new JLabel("Nombre de usuario:");
        txtUsuario = new JTextField();

        lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        btnIngresar = new JButton("Ingresar");
        btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(lblUsuario);
        panelFormulario.add(txtUsuario);
        panelFormulario.add(lblContrasena);
        panelFormulario.add(txtContrasena);
        panelFormulario.add(btnIngresar);
        panelFormulario.add(btnLimpiar);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelCentral.add(panelFormulario, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void agregarEventos() {

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUsuario.setText("");
                txtContrasena.setText("");
            }
        });
    }

    private void iniciarSesion() {

    String usuario = txtUsuario.getText();
    String contrasena = txtContrasena.getText();

    if (sistema.autenticarUsuario(usuario, contrasena)) {

        VentanaMenu menu = new VentanaMenu(sistema);
        menu.setVisible(true);
        this.dispose();

    } else {

        JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos",
                "Error de acceso",
                JOptionPane.ERROR_MESSAGE);
    }
}
}