package modelos;

import paquetes.Paquete;
import vistas.Vista;

public interface ModeloObservable {
    
    public void attach(Vista vista);
    
    public void detach();
    
    public void notificar(Paquete paquete);
    
}