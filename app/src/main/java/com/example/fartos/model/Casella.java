package com.example.fartos.model;

import java.io.Serializable;

public class Casella implements Serializable {
    private String nom;
    private int nCasella;
    private int p1;
    private int p2;
    private int ronda = 1;


    public Casella(String nom, int nCasella, int p1, int p2) {
        this.nCasella = nCasella;
        this.p1 = p1;
        this.p2 = p2;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getnCasella() {
        return nCasella;
    }

    public void setnCasella(int nCasella) {
        this.nCasella = nCasella;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }
}