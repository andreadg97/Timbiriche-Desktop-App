package controladores;
import modelos.ModeloRegistro;

public class ControladorRegistro {
    
    private ModeloRegistro modelo;
    
    public ControladorRegistro(ModeloRegistro modelo){
        this.modelo = modelo;
    }
    
    public RegistrarseListener crearRegistrarseListener(){
        RegistrarseListener listener = new RegistrarseListener(modelo);
        return listener;
    }
    
    public AvatarListener crearAvatarListener(){
        AvatarListener listener = new AvatarListener(modelo);
        return listener;
    }
    
    public NicknameFieldListener crearNicknameFieldListener(){
        NicknameFieldListener listener = new NicknameFieldListener(modelo);
        return listener;
    }
    
}