package test;

import conexionCliente.AdministradorEntradas;
import conexionCliente.Cliente;
import controladores.ControladorRegistro;
import controladores.ControladorTablero;
import dominio.Partida;
import dominio.Tablero;
import dominio.Turno;
import modelos.ModeloRegistro;
import modelos.ModeloResultados;
import modelos.ModeloTablero;
import vistas.Vista;
import vistas.VistaRegistro;
import vistas.VistaResultados;
import vistas.VistaTablero;

public class PruebaGeneral {
    public static void main(String[] args) {
        
        AdministradorEntradas adminEntradas = new AdministradorEntradas();
        Cliente cliente = new Cliente(adminEntradas);
        
        Tablero tablero = new Tablero();
        Turno turno = new Turno();        
        Partida partida = new Partida(turno, tablero);

        // TABLERO
        ModeloTablero modelo = new ModeloTablero(partida,cliente); 
        adminEntradas.agregarModelo(modelo);
        ControladorTablero controlador = new ControladorTablero(modelo);
        Vista vista = new VistaTablero(controlador);
        modelo.attach(vista);             
      
        // REGISTRO
        ModeloRegistro modeloRegistro = new ModeloRegistro(partida,cliente,adminEntradas);
        adminEntradas.agregarModelo(modeloRegistro);
        ControladorRegistro controladorRegistro = new ControladorRegistro(modeloRegistro);
        VistaRegistro vistaRegistro = new VistaRegistro(null, true,controladorRegistro);
        modeloRegistro.attach(vistaRegistro);
        cliente.crearSocket();
        
        // RESULTADOS
        ModeloResultados modeloResultados = new ModeloResultados();
        adminEntradas.agregarModelo(modeloResultados);
        VistaResultados vistaResultados = new VistaResultados();
        modeloResultados.attach(vistaResultados);
        
    }    
}