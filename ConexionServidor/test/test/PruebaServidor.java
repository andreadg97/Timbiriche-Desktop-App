package test;
import modelo.Servidor;

public class PruebaServidor {
    public static void main(String[] args) {
        
        Servidor servidor = new Servidor();
        servidor.obtenerServidor();
        servidor.recibirClientes();
        
    }
}