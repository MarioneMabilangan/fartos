package com.example.fartos.model;

import com.example.fartos.view.CartaView;

import java.io.Serializable;
import java.util.*;

public class Jugador implements Serializable {

    private List<CartaView> cartasMano = new ArrayList<>();
    private String name;
    private boolean patada = false;
    private boolean zancadilla = false;
    private int sprite;
    private int icon;
    private int numCasilla = -1;
    private boolean isPlayer;

    public Jugador(String name, int sprite, int icon) {
        this.name = name;
        this.sprite = sprite;
        this.icon = icon;
    }

    public List<CartaView> getCartasMano() {
        return cartasMano;
    }

    public void setCartasMano(List<CartaView> cartasMano) {
        this.cartasMano = cartasMano;
    }

    public int getNumCasilla() {
        return numCasilla;
    }

    public void setNumCasilla(int numCasilla) {
        this.numCasilla = numCasilla;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        this.isPlayer = player;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CartaView> getMa() {
        return cartasMano;
    }

    public void setMa(List<CartaView> ma) {
        this.cartasMano = ma;
    }

    public boolean isPatada() {
        return patada;
    }

    public void setPatada(boolean patada) {
        this.patada = patada;
    }

    public boolean isZancadilla() {
        return zancadilla;
    }

    public void setZancadilla(boolean zancadilla) {
        this.zancadilla = zancadilla;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

}