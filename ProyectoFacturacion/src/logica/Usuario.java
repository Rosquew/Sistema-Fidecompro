package logica;

public class Usuario {

    private String nombreUsuario;
    private String contrasena;

    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean validarCredenciales(String usuarioIngresado, String contrasenaIngresada) {

        if (this.nombreUsuario.equals(usuarioIngresado) && this.contrasena.equals(contrasenaIngresada)) {
            return true;
        } else {
            return false;
        }

    }
}