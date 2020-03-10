package modelos;

import java.awt.Color;

public class ConstantesColor {
    
    public static Color ROJO = Color.RED;
    public static Color AZUL = Color.BLUE;
    public static Color AMARILLO = Color.YELLOW;
    public static Color VERDE = Color.GREEN;
    
    public static Color obtenerColor(String color){
        switch(color){
            case "rojo":
                return ROJO;
            case "azul":
                return AZUL;
            case "amarillo":
                return AMARILLO;
            case "verde":
                return VERDE;
            default:
                return null;
        }
    }
    
    public static String[] obtenerColores(){
        String colores[] = {"rojo","amarillo","azul","verde"};
        return colores;
    }
    
}