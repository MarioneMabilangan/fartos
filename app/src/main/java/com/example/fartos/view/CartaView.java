package com.example.fartos.view;

import java.io.Serializable;

public class CartaView implements Serializable {
    private String nom;
    private int skin;

    private int tipus;

    public CartaView(String nom, int skin, int tipus) {
        this.nom = nom;
        this.skin = skin;
        this.tipus = tipus;
    }

    public int getTipus() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus = tipus;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }


    @Override
    public String toString() {
        return "CartaV{" + "nom='" + nom + '\'' + ", skin=" + skin + ", tipus=" + tipus + '}';
    }
}

