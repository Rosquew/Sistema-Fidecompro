package logica;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String direccion;

    public Cliente(int idCliente, String nombre, String identificacion, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void actualizarCliente(String nombre, String identificacion, String telefono, String direccion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}