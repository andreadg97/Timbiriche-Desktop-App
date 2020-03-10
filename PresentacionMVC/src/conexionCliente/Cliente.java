package conexionCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import paquetes.Paquete;

public class Cliente {

    private Socket socketCliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private HiloCliente hilo;
    private AdministradorEntradas adminEntradas;
    
    public Cliente(AdministradorEntradas adminEntradas){
        this.adminEntradas = adminEntradas;
    }
    
    public void detenerHilo(){
        hilo.stop();
    }
    
    public void crearSocket() {
        try {
            socketCliente = new Socket("localhost", 1931);  
            hilo = new HiloCliente(this,socketCliente,adminEntradas);               
            hilo.start();
        } catch (IOException ex) {
            System.out.print("Cliente, crearSocket:     "+ex.getMessage());
        }
    }

    public void enviarMensaje(Paquete paquete) {
        try {
            salida = new ObjectOutputStream(socketCliente.getOutputStream());            
            salida.writeObject(paquete);
        } catch (IOException ex) {
            System.out.print("Cliente, enviarMensaje:       "+ex.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            salida.close();
        } catch (IOException ex) {
            System.out.print("Cliente, cerrarMensaje:       ");
            System.out.println(ex.getMessage());
        }
    }

}
