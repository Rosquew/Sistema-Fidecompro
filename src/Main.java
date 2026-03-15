import Interfaz.VentanaLogin;
import logica.SistemaFacturacion;

public class Main {

    public static void main(String[] args) {

        SistemaFacturacion sistema = new SistemaFacturacion();

        VentanaLogin login = new VentanaLogin(sistema);
        login.setVisible(true);

    }
}