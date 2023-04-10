package com.example.fartos;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String nom;
    private List<Carta> ma;
    private boolean patada = false;
    private boolean zancadilla = false;


    public String getNom() {
        return nom;
    }
    public List<Carta> getMa() {
        return ma;
    }
    public boolean isPatada() {
        return patada;
    }
    public boolean isZancadilla() {
        return zancadilla;
    }

    public void setMa(List<Carta> ma) {
        this.ma = new ArrayList<>();
        this.ma.addAll(ma);
    }
    public void setPatada(boolean patada) {
        this.patada = patada;
    }
    public void setZancadilla(boolean zancadilla) {
        this.zancadilla = zancadilla;
    }

    public Jugador (String nom) {
        this.nom = nom;
        this.ma = new ArrayList<>();
    }




    @Override
    public String toString() {
        return nom;
    }
}
