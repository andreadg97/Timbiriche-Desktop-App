package controladores;
import modelos.ModeloObservable;
import modelos.ModeloTablero;

public class ControladorTablero extends Controlador{

    public ControladorTablero(ModeloObservable modelo) {
        super.modelo = modelo;
    }
    
    public LineButtonListener crearButtonListener(){
        ModeloTablero modeloTablero = (ModeloTablero) modelo;
        LineButtonListener listener = new LineButtonListener(modeloTablero);
        return listener;
    }
    
    public ComboBoxListener crearComboBoxListener(){
        ModeloTablero modeloTablero = (ModeloTablero) modelo;
        ComboBoxListener listener = new ComboBoxListener(modeloTablero);
        return listener;
    }
    
    public ColorButtonListener crearColorButtonListener(){
        ModeloTablero modeloTablero = (ModeloTablero) modelo;
        ColorButtonListener listener = new ColorButtonListener(modeloTablero);
        return listener;
    }
    
    public AbandonarButtonListener crearAbandonarButtonListener(){
        ModeloTablero modeloTablero = (ModeloTablero) modelo;
        AbandonarButtonListener botonAbandonar = new AbandonarButtonListener(modeloTablero);
        return botonAbandonar;
    }
    
    public IniciarButtonListener crearIniciarButtonListener(){
        ModeloTablero modeloTablero = (ModeloTablero) modelo;
        IniciarButtonListener botonIniciar = new IniciarButtonListener(modeloTablero);
        return botonIniciar;
    }
    
}
