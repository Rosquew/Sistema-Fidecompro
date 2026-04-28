package logica;

import java.util.ArrayList;

public class SistemaFacturacion {

    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Producto> listaProductos;

    public SistemaFacturacion() {
        listaUsuarios = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();

        listaUsuarios.add(new Usuario("admin", "1234"));
    }

    public boolean autenticarUsuario(String usuario, String contrasena) {
        for (Usuario u : listaUsuarios) {
            if (u.validarCredenciales(usuario, contrasena)) {
                return true;
            }
        }
        return false;
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public Cliente buscarClientePorId(int idCliente) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente() == idCliente) {
                return c;
            }
        }
        return null;
    }

    public Producto buscarProductoPorId(int idProducto) {
        for (Producto p : listaProductos) {
            if (p.getIdProducto() == idProducto) {
                return p;
            }
        }
        return null;
    }

    public boolean actualizarCliente(int idCliente, String nombre, String identificacion, String telefono, String direccion) {
        Cliente cliente = buscarClientePorId(idCliente);

        if (cliente != null) {
            cliente.actualizarCliente(nombre, identificacion, telefono, direccion);
            return true;
        }

        return false;
    }

    public void eliminarProducto(int idProducto) {
        Producto producto = buscarProductoPorId(idProducto);

        if (producto != null) {
            listaProductos.remove(producto);
        }
    }
}