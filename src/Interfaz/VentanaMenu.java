package Interfaz;

import logica.SistemaFacturacion;
import Interfaz.VentanaLogin;
import Interfaz.VentanaClientes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenu extends JFrame {

    private SistemaFacturacion sistema;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    private JButton btnClientes;
    private JButton btnProductos;
    private JButton btnCerrarSesion;

    public VentanaMenu(SistemaFacturacion sistema) {
        this.sistema = sistema;

        setTitle("Menú Principal - Fidecompro");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        agregarEventos();
    }

    private void inicializarComponentes() {

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));

        lblTitulo = new JLabel("FIDECOMPRO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        lblSubtitulo = new JLabel("Panel Principal", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(lblSubtitulo);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        btnClientes = new JButton("Gestión de Clientes");
        btnProductos = new JButton("Gestión de Productos");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnClientes);
        panelBotones.add(btnProductos);
        panelBotones.add(btnCerrarSesion);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void agregarEventos() {

        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaClientes(sistema).setVisible(true);
            }
        });

        btnProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaProductos(sistema).setVisible(true);
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VentanaLogin(sistema).setVisible(true);
            }
        });
    }
}