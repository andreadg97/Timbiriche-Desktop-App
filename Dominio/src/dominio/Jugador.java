package dominio;

import java.io.Serializable;

public class Jugador implements Serializable{
    
    private String nickname;
    private String color;
    private int puntaje;
    private int avatar;

    public Jugador(){}
    
    public Jugador(String nickname, int avatar) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.puntaje = 0;
    }

    public String obtenertNickname() {
        return nickname;
    }

    public String obtenerColor() {
        return color;
    }

    public int obtenerPuntaje() {
        return puntaje;
    }

    public int obtenerAvatar() {
        return avatar;
    }

    public void establecerColor(String color) {
        this.color = color;
    }

    public void sumarPunto() {
        puntaje ++;
    }
    
}